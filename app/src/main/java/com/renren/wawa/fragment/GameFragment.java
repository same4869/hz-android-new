package com.renren.wawa.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.renren.wawa.R;
import com.renren.wawa.activity.GameDetailActivity;
import com.renren.wawa.activity.GameRoomActivity;
import com.renren.wawa.activity.MyDollActivity;
import com.renren.wawa.activity.WawaMainActivity;
import com.renren.wawa.adapter.ChatMsgListAdapter;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.config.GameControlStatus;
import com.renren.wawa.config.GameState;
import com.renren.wawa.interfaces.RoomObserver;
import com.renren.wawa.manager.GameManager;
import com.renren.wawa.manager.UserManager;
import com.renren.wawa.model.ChatEntity;
import com.renren.wawa.model.GameStartBean;
import com.renren.wawa.model.GameStatusBean;
import com.renren.wawa.model.MemberUserBean;
import com.renren.wawa.model.PaySettingBean;
import com.renren.wawa.model.RoomInfoBean;
import com.renren.wawa.model.RoomStatusUpdate;
import com.renren.wawa.model.RoomUserMessage;
import com.renren.wawa.model.UserInfo;
import com.renren.wawa.model.UserWalletBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.socket.GameSocketClient;
import com.renren.wawa.sp.CommSetting;
import com.renren.wawa.utils.AppUtil;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.CircleTransform;
import com.renren.wawa.utils.MessageObservable;
import com.renren.wawa.utils.NetWorkUtil;
import com.renren.wawa.utils.PbUtil;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.CommGameDialog;
import com.renren.wawa.view.WeChatRechargeDialog;
import com.squareup.picasso.Picasso;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.av.sdk.AVRoomMulti;
import com.tencent.av.sdk.AVView;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.ILiveMemStatusLisenter;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.core.ILiveRoomOption;
import com.tencent.ilivesdk.view.ILiveRootView;
import com.tencent.ilivesdk.view.VideoListener;
import com.tencent.livesdk.ILVChangeRoleRes;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import game.protobuf.Game;
import rx.Subscriber;

import static com.renren.wawa.config.Constants.END_GAME;
import static com.renren.wawa.config.Constants.START_GAME;

public class GameFragment extends Fragment implements RoomObserver, ILVLiveConfig.ILVLiveMsgListener, GameSocketClient.ReceiveGameMessage {
    private static final int CMD_UP_LIVE = 1;
    private static final int CMD_DOWN_LIVE = 2;
    private static final int CMD_MESSAGE_UP = 3;
    private static final int CMD_MESSAGE_DOWN = 4;
    private static final int CMD_TEXT_MESSAGE = 5;

    private static final int UPDATE_ROOM_STATUS = 10;
    private static final int UPDATE_ROOM_MEMBER = 11;

    private static final int UPDATE_WS_MESSAGE = 12;
    private static final int JOIN_ROOM_SUCCEED = 13;
    private static final int AUTH_WEBSOCKET = 14;
    private static final int UP_VIDEO_LIVE_SUCCEED = 15;
    private static final int DOWN_VIDEO_LIVE_SUCCEED = 16;
    private static final int RECEIVE_GAME_MESSAGE = 17;//获取服务端消息
    private static final int DELAY_UPDATE_VIEW = 18;//更新UI

    private static final int REFRESH_LISTVIEW = 6;
    private static final int MINFRESHINTERVAL = 500;

    @BindView(R.id.game_master_view)
    ILiveRootView mMasterView;
    @BindView(R.id.game_slave_view)
    ILiveRootView mSlaveView;

    @BindView(R.id.game_control_idle)
    View mIdleView;
    @BindView(R.id.game_control_playing)
    View mControlView;
    @BindView(R.id.game_control_start)
    TextView mStartBtn;
    @BindView(R.id.opt_catch)
    ImageView mCatchBtn;
    @BindView(R.id.tint_text)
    TextView mTintText;
    @BindView(R.id.game_danmaku)
    DanmakuView mDanmakuView;
    @BindView(R.id.game_placeholder)
    View mPlaceHolder;
    @BindView(R.id.placeholder_text)
    TextView mPlaceText;
    @BindView(R.id.game_member_count)
    TextView mMemberCount;
    @BindView(R.id.game_member_more)
    ImageView mMemberMore;
    @BindView(R.id.game_members)
    View mMembersView;
    @BindView(R.id.add_comment)
    ImageButton mCommentBtn;
    @BindViews({R.id.game_member_1, R.id.game_member_2, R.id.game_member_3})
    List<ImageView> mAvatars;
    @BindView(R.id.game_back)
    ImageButton gameBack;
    @BindView(R.id.im_msg_listview)
    ListView mListViewMsgItems;
    @BindView(R.id.danmu_control)
    ImageView danmuToggle;
    @BindView(R.id.my_balance)
    TextView mBalanceText;

    @BindView(R.id.current_up_user_iv)
    ImageView currentUpUserIv;
    @BindView(R.id.current_up_user_tv)
    TextView currentUpUserTv;
    @BindView(R.id.game_guest_layout)
    LinearLayout gameGuestLayout;

    private boolean sideCameraReady;
    private boolean isVideoLiveOnline;
    private boolean needSoundEffect;
    private boolean hasMoved;
    private boolean joinRoomSucceed;
    private String mRoomId;
    private int mBalance;
    private int mPrice = -1;
    private int mCountFix = 0;
    private Vibrator mVibrator;

    private LinkedList<MemberUserBean> mMembers;
    private HashMap<Long, MemberUserBean> mMembersMap;
    private HashMap<Class<GameState>, GameState> mStates;
    private List<PaySettingBean.DataBean> weixinBeanList = new ArrayList<>();
    private CountDownTimer countDownTimer;
    private String name;
    private RoomInfoBean.DataBean.RoomBean.LiveStreamBean liveStreamBean;
    private long uid;
    private String token;
    private GameSocketClient gameSocketClient;
    private boolean isSideCameraCurrent = false;  // 当前是哪个摄像头
    private String currentGameStatus = GameState.MACHINE_STATE_IDLE;
    private int session = 0;
    private int roomStatus = -1;
    private boolean mBoolRefreshLock = false;
    private boolean mBoolNeedRefresh = false;
    private ArrayList<ChatEntity> mTmpChatList = new ArrayList<>();//缓冲队列
    private ArrayList<ChatEntity> mArrayListChatEntity = new ArrayList<>();
    private ChatMsgListAdapter mChatMsgListAdapter;
    private final Timer mTimer = new Timer();
    private TimerTask mTimerTask = null;
    private boolean isDanmuClose = false;
    private Unbinder unbinder;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case RECEIVE_GAME_MESSAGE:
                    BBLog.e(Constants.TAG, "RECEIVE_GAME_MESSAGE");
                    break;
                case UPDATE_ROOM_STATUS://要改
                    RoomStatusUpdate roomStatusUpdate = (RoomStatusUpdate) message.obj;
                    BBLog.e(Constants.TAG, "UPDATE_ROOM_STATUS mRoomId " + mRoomId + " : " + roomStatusUpdate.getRoomId());
                    if (mRoomId.equals(roomStatusUpdate.getRoomId() + "")) {
                        setRoomStatus(roomStatusUpdate.getStatus());
                    }
                    break;
                case UPDATE_ROOM_MEMBER://更行房间人数
                    updateRoomMember(message.getData().getBoolean("isJoin"),
                            (RoomUserMessage) message.obj);
                    break;
                case JOIN_ROOM_SUCCEED://进入房间成功
                    if (mMasterView != null && mSlaveView != null) {
                        mMasterView.render(liveStreamBean.getMid(), AVView.VIDEO_SRC_TYPE_CAMERA);
                        mSlaveView.render(liveStreamBean.getSid(), AVView.VIDEO_SRC_TYPE_CAMERA);
                    }

                    break;
                case CMD_MESSAGE_UP: //  接收到消息 上机
                    BBLog.e(Constants.TAG, "received message: CMD_MESSAGE_UP");
                    addDanmaku("系统消息", message.getData().getString("text"), 1.0f);
                    for (MemberUserBean user : mMembers) {
                        if (message.getData().getString("id").contains(String.valueOf(user.getId()))) {
                            //mUserName.setText(user.getNickname());
                            break;
                        }
                    }
                    break;
                case CMD_MESSAGE_DOWN: //接收到消息 下机
                    addDanmaku("系统消息", message.getData().getString("text"), 1.0f);
                    //mUserName.setText(null);
                    break;
                case CMD_UP_LIVE:
                    String upId = (String) message.obj;
                    String userId;
                    BBLog.e(Constants.TAG, "received message: CMD_UP_LIVE " + upId);
                    if (StringUtil.isNotBlank(upId)) {
                        userId = upId.replace("u", "");
                        getUserInfo(Long.parseLong(userId));

                    }

                    break;
                case CMD_DOWN_LIVE:
                    BBLog.e(Constants.TAG, "received message: CMD_DOWN_LIVE");
                    if (gameGuestLayout != null) {
                        gameGuestLayout.setVisibility(View.INVISIBLE);
                    }

                    break;
                case CMD_TEXT_MESSAGE:  //发送消息
                    Bundle bundle = message.getData();
                    for (MemberUserBean user : mMembers) {
                        BBLog.e(Constants.TAG, "id " + user.getId() + " " + bundle.getString("id"));
                        if (bundle.getString("id") != null && bundle.getString("id").contains(String.valueOf(user.getId()))) {
                            addDanmaku(user.getNickname(), bundle.getString("text"), 2.0f);
                            break;
                        }
                    }
                    break;
                case UP_VIDEO_LIVE_SUCCEED:
                    isVideoLiveOnline = true;
                    break;
                case DOWN_VIDEO_LIVE_SUCCEED:
                    isVideoLiveOnline = false;
                    break;
                case DELAY_UPDATE_VIEW:
                    if (mPlaceHolder != null) {
                        mPlaceHolder.setVisibility(View.GONE);
                    }
                    break;
                case REFRESH_LISTVIEW:
                    doRefreshListView();
                    break;
            }
            return false;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = UserManager.getCurUserNickName();
        mStates = new HashMap<>();
        mMembers = new LinkedList<>();
        mMembersMap = new HashMap<>();

        needSoundEffect = CommSetting.getSettingSoundEffect();
        mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        uid = UserManager.getCurUserId();
        token = CommSetting.getToken();
        BBLog.e(Constants.TAG, "uid " + Long.valueOf(uid) + " token " + token);
        loadChargeList();//获取充值列表
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        unbinder = ButterKnife.bind(this, view);

        GameManager.getInstance().registerObserver(this);


        //UI初始化完成之后，将三个用于视频显示的控件传给SDK进行初始化
        ILiveRoomManager.getInstance().initRootViewArr(new ILiveRootView[]{mMasterView, mSlaveView});

        mChatMsgListAdapter = new ChatMsgListAdapter(getActivity(), mListViewMsgItems, mArrayListChatEntity);
        mListViewMsgItems.setAdapter(mChatMsgListAdapter);

        //以下几个方法需要界面被attach到window之后才能调用
        mMasterView.setBackground(R.mipmap.game_bg);
        mMasterView.setAutoOrientation(false);
        mSlaveView.setAutoOrientation(false);
//        mGuestView.setAutoOrientation(false);
//        mGuestView.setZOrderMediaOverlay(true);
        mMasterView.setVideoListener(new VideoListener() {
            @Override
            public void onFirstFrameRecved(int width, int height, int angle, String identifier) {
                BBLog.e(Constants.TAG, "mMasterView video listener: first frame reached");
                //主摄像头的第一帧到达之后，才把载入中那张placeholder图隐藏掉
                joinRoomSucceed = true;
                if (mPlaceHolder == null) {
                    return;
                }
                if (mPlaceHolder != null) {
                    mPlaceHolder.setVisibility(View.GONE);
                }
                setRoomStatus(roomStatus);
            }

            @Override
            public void onHasVideo(String s, int i) {
                BBLog.e(Constants.TAG, "mMasterView video listener: onHasVideo s " + s + " i " + i);

            }

            @Override
            public void onNoVideo(String s, int i) {
                BBLog.e(Constants.TAG, "mMasterView video listener: no videos s " + s + " i " + i);

            }

        });
        mSlaveView.setVideoListener(new VideoListener() {
            @Override
            public void onFirstFrameRecved(int width, int height, int angle, String identifier) {
                //侧面摄像头的第一帧到达之后，才认为侧面摄像头已经准备好，否则不能切换到侧面摄像头视角
                sideCameraReady = true;
                BBLog.e(Constants.TAG, "mSlaveView video listener: first frame reached");
            }

            @Override
            public void onHasVideo(String s, int i) {
                BBLog.e(Constants.TAG, "mSlaveView video listener: onHasVideo s " + s + " i " + i);

            }

            @Override
            public void onNoVideo(String s, int i) {
                BBLog.e(Constants.TAG, "mSlaveView video listener: no videos s " + s + " i " + i);

            }

        });


        return view;
    }


    public void setRoom(String roomId) {
        mRoomId = roomId;
        BBLog.e(Constants.TAG, " mPrice " + mPrice + " roomId " + Integer.valueOf(roomId));
    }


    public void setLiveStream(RoomInfoBean.DataBean.RoomBean.LiveStreamBean liveStreamBean, int price) {
        this.liveStreamBean = liveStreamBean;

        SpannableString msp = new SpannableString("开始游戏\n" + price + "/次");
        msp.setSpan(new RelativeSizeSpan(0.8f), 4, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        mStartBtn.setText(msp);
        getGameStatus();
    }

    //用户点击左上角退出按钮时，判断当前是不是在游戏中
    public boolean canLeaveRoom() {
        return currentGameStatus.equals(GameState.MACHINE_STATE_IDLE);
    }

    //退出房间时，需要调用SDK的退出房间接口
    public void forceLeaveRoom() {
        if (gameSocketClient != null) {
            gameSocketClient.sendStopGame();
        }
        if (ILiveRoomManager.getInstance().getOption() != null) {
            ILiveRoomManager.getInstance().getOption().setRoomMemberStatusLisenter(null);
        }
        ILiveRoomManager.getInstance().quitRoom(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        //注册两个监听器，一个是从互动视频的SDK里接受来自互动视频的消息，一个是从后台长连接那里获取其它人的游戏信息
        MessageObservable.getInstance().addObserver(this);
    }

    @Override
    public void onStop() {
        MessageObservable.getInstance().deleteObserver(this);

        super.onStop();
    }

    @Override
    public void onDestroy() {
        //退出时将资源释放
        mVibrator.cancel();
        clearOldData();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (gameSocketClient != null) {
            gameSocketClient.onDisconnect();
        }

        mMasterView = null;
        mSlaveView = null;

        super.onDestroy();
    }

    //收到全局tcp通知时，更新当前房间是否空闲的状态
    @Override
    public void onRoomStateUpdated(RoomStatusUpdate roomStatusUpdate) {
        BBLog.e(Constants.TAG, "roomStatusUpdate " + roomStatusUpdate.toString());
        Message msg = new Message();
        msg.what = UPDATE_ROOM_STATUS;
        msg.obj = roomStatusUpdate;
        mHandler.sendMessage(msg);
    }

    //收到全局tcp通知时，更新当前房间内玩家的数量
    @Override
    public void onRoomMemberUpdated(boolean isJoin, RoomUserMessage roomUserMessage) {
        BBLog.e(Constants.TAG, "onRoomMemberUpdated " + roomUserMessage.toString());
        Message message = new Message();
        message.what = UPDATE_ROOM_MEMBER;
        Bundle bundle = new Bundle();
        bundle.putBoolean("isJoin", isJoin);
        message.obj = roomUserMessage;
        message.setData(bundle);
        mHandler.sendMessage(message);
    }


    @Override
    public void onBroadcastReceived(RoomUserMessage roomUserMessage) {
    }


    @OnClick(R.id.game_back)
    public void gameBackOnClick() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.show_change)
    public void showCharge() {
        if (!WawaApplication.getInstance().getmWxApi().isWXAppInstalled()) {
            ToastUtil.showToast("您还未安装微信客户端");
            return;
        }
        WeChatRechargeDialog weChatRechargeDialog = new WeChatRechargeDialog(getActivity(), weixinBeanList);
        weChatRechargeDialog.show();
    }

    //点击右上角头像，展示此用户的抓取记录
//    @OnClick({R.id.game_member_1, R.id.game_member_2, R.id.game_member_3})
//    public void clickAvatar(ImageView button) {
//        int index = mAvatars.indexOf(button);
//        if (index >= 0 && index < mMembers.size()) {
//            MemberUserBean user = mMembers.get(index);
    //Get user scratch records
//            Intent intent = new Intent(getActivity(), MyDollActivity.class);
//            intent.putExtra("type", "other");
//            intent.putExtra("user_id", user.getId());
//            startActivity(intent);
//
//        }
//    }

    @OnClick(R.id.danmu_control)
    public void toggleDanmu() {
        isDanmuClose = !isDanmuClose;
        if (isDanmuClose) {
            danmuToggle.setImageResource(R.mipmap.danmu_open);
            mListViewMsgItems.setVisibility(View.GONE);
        } else {
            danmuToggle.setImageResource(R.mipmap.danmu_close);
            mListViewMsgItems.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 点击切换摄像头的按钮
     */
    @OnClick(R.id.game_camera_toggle)
    public void toggleViewport() {
        //若当前网络异常，则显示网络异常的placeholder
        if (!NetWorkUtil.checkNetWork(getActivity())) {
            mPlaceText.setText("网络异常！");
            mPlaceHolder.setVisibility(View.VISIBLE);
        } else {
            if (joinRoomSucceed) {
                mPlaceHolder.setVisibility(View.GONE);
            }
        }
        if (liveStreamBean == null) {
            return;
        }

        if (mMasterView.getVisibility() == View.VISIBLE) {
            //若侧面摄像头已经准备好，则切换到侧面摄像头
            if (sideCameraReady) {
                switchCamera();
            } else {
                ToastUtil.showToast("侧边摄像头还未准备好！");
            }
        } else {
            switchCamera();
        }
    }

    /**
     * 切换摄像头
     */
    private void switchCamera() {
        if (isSideCameraCurrent) {//当前 是 侧边摄像头  切换成  主摄像头
            mSlaveView.setVisibility(View.INVISIBLE);
            mMasterView.setVisibility(View.VISIBLE);
            isSideCameraCurrent = false;
        } else {//当前 是 主摄像头  切换成  侧边摄像头
            mMasterView.setVisibility(View.INVISIBLE);
            mSlaveView.setVisibility(View.VISIBLE);
            isSideCameraCurrent = true;
        }
    }

    //点击发弹幕
    @OnClick(R.id.add_comment)
    public void editComment() {
        ((GameRoomActivity) getActivity()).showInputView();
    }

    //点击开始游戏
    @OnClick(R.id.game_control_start)
    public void startGame() {
        if (AppUtil.isFastDoubleClick(1500)) {
            return;
        }

        //如果还没有加入房间，则不能点击开始游戏按钮
        if (!joinRoomSucceed) {
            return;
        }

        if (!sideCameraReady) { //侧边摄像头  没准备好不能上机
            ToastUtil.showToast("侧边摄像头没准备好");
            return;
        }
        //如果当前余额不足，则不能开始游戏，并且显示邀请朋友的Dialog
        if (mBalance < mPrice) {
            showInviteDialog();
            return;
        }


        HttpMethods.getInstance().getGameStart(mRoomId, new Subscriber<GameStartBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                BBLog.e(Constants.TAG, e.getMessage());
            }

            @Override
            public void onNext(GameStartBean gameStartBean) {
                mBalance -= mPrice;//开始成功之后，先本地扣费，这个值后面结束游戏之后还会调api来刷新，确保正确
                currentGameStatus = GameState.MACHINE_STATE_STANDBY;
                // 第一次上机
                if (gameStartBean != null) {
                    String tcp = gameStartBean.getData().getSocketUrl();
                    String[] ipPort = tcp.split(":");
                    if (ipPort.length == 2) {
                        gameSocketClient = new GameSocketClient(ipPort[0], ipPort[1], uid, token, mRoomId, GameFragment.this);
                        gameSocketClient.connectServer();
                    }
                }


            }
        });

    }


    /**
     * 下爪控制
     */
    @OnClick(R.id.opt_catch)
    public void take() {
        if (hasMoved) {
            mVibrator.vibrate(new long[]{100, 400}, -1);
            if (gameSocketClient != null && !AppUtil.isFastDoubleClick()) {
                gameSocketClient.sendGameControl(GameControlStatus.CRAWLING);//发送下爪的指令
            }
        }
    }

    /**
     * 游戏上下左右控制
     */
    @OnTouch({R.id.opt_up, R.id.opt_down, R.id.opt_left, R.id.opt_right})
    public boolean move(View view, MotionEvent event) {

        if (gameSocketClient == null) {
            return false;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //按下方向按钮之后，将按钮置为按下状态，并且播放对应的声音和震动
            hasMoved = true;//此值用于确保至少按一次方向键之后，才能允许按下爪按钮
            view.setPressed(true);
            mVibrator.vibrate(new long[]{100, 400}, -1);
            playSoundEffect(GameManager.SOUND_MOVE);

            switch (view.getId()) {
                case R.id.opt_up:
                    if (isSideCameraCurrent)
                        gameSocketClient.sendGameControl(GameControlStatus.LEFT_START);
                    else
                        gameSocketClient.sendGameControl(GameControlStatus.BACKWARD_START);

                    break;
                case R.id.opt_down:
                    if (isSideCameraCurrent)
                        gameSocketClient.sendGameControl(GameControlStatus.RIGHT_START);
                    else
                        gameSocketClient.sendGameControl(GameControlStatus.FORWARD_START);
                    break;
                case R.id.opt_left:
                    if (isSideCameraCurrent)
                        gameSocketClient.sendGameControl(GameControlStatus.FORWARD_START);
                    else
                        gameSocketClient.sendGameControl(GameControlStatus.LEFT_START);

                    break;
                case R.id.opt_right:
                    if (isSideCameraCurrent)
                        gameSocketClient.sendGameControl(GameControlStatus.BACKWARD_START);
                    else
                        gameSocketClient.sendGameControl(GameControlStatus.RIGHT_START);
                    break;
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            view.setPressed(false);
            switch (view.getId()) {
                case R.id.opt_up:
                    if (isSideCameraCurrent)
                        gameSocketClient.sendGameControl(GameControlStatus.LEFT_END);
                    else
                        gameSocketClient.sendGameControl(GameControlStatus.FORWARD_END);
                    break;
                case R.id.opt_down:
                    if (isSideCameraCurrent)
                        gameSocketClient.sendGameControl(GameControlStatus.RIGHT_END);
                    else
                        gameSocketClient.sendGameControl(GameControlStatus.BACKWARD_END);
                    break;
                case R.id.opt_left:
                    if (isSideCameraCurrent)
                        gameSocketClient.sendGameControl(GameControlStatus.FORWARD_END);
                    else
                        gameSocketClient.sendGameControl(GameControlStatus.LEFT_END);
                    break;
                case R.id.opt_right:
                    if (isSideCameraCurrent)
                        gameSocketClient.sendGameControl(GameControlStatus.BACKWARD_END);
                    else
                        gameSocketClient.sendGameControl(GameControlStatus.RIGHT_END);
                    break;
            }
            return true;
        }
        return false;
    }

    //你就这个以下几种消息就行 1（进入房间）、2（离开房间）、2049（上机成功）、2065（抓取成功）、2066（抓取失败）
    private void handle(String cmd, final long remains, long timeUsed, int session) {
        currentGameStatus = cmd;
        if (cmd == GameState.MACHINE_STATE_STANDBY) {//开始游戏   上麦
//            mCatchBtn.setPercentage(1.0f);
            mTintText.setText("游戏开始...");
            playSoundEffect(GameManager.SOUND_READY_GO);
            sendCustomCmd(Constants.LIVE_CMD_UP_MACHINE, name + " 成功上机了");
            upMemberVideo();
        } else if (cmd == GameState.MACHINE_STATE_CRAWLING) {//下爪
            if (isSideCameraCurrent) {
                switchCamera();
            }
            refreshBalance();
            mTintText.setText("已下爪，等待游戏结果...");
            playSoundEffect(GameManager.SOUND_TAKE);
        } else if (cmd == GameState.MACHINE_STATE_AWARDING_FAILED) {//抓取失败
            if (isSideCameraCurrent) {
                switchCamera();
            }
            playSoundEffect(GameManager.SOUND_FAILED);
            showFailedDialog(remains);
            sendCustomCmd(Constants.LIVE_CMD_FAILED, name + " 没有抓到");
            addDanmaku("系统消息", name + " 没有抓到", 1.0f);

            Message message = new Message();
            message.what = END_GAME;
            EventBus.getDefault().post(message);
        } else if (cmd == GameState.MACHINE_STATE_AWARDING_SUCCESS) {//抓取成功
            if (isSideCameraCurrent) {
                switchCamera();
            }
            playSoundEffect(GameManager.SOUND_SUCCEED);
            showSuccessDialog();
            sendCustomCmd(Constants.LIVE_CMD_SUCCEED, name + "抓到了，大吉大利，晚上吃鸡");
            addDanmaku("系统消息", name + "抓到了，大吉大利，晚上吃鸡", 1.0f);

            Message message = new Message();
            message.what = END_GAME;
            EventBus.getDefault().post(message);
        }
        //游戏开始之后切换成控制界面，结束之后再恢复为开始按钮的界面
        if (cmd == GameState.MACHINE_STATE_IDLE) {
            mIdleView.setVisibility(View.VISIBLE);
            mControlView.setVisibility(View.INVISIBLE);
        } else {
            mIdleView.setVisibility(View.INVISIBLE);
            mControlView.setVisibility(View.VISIBLE);
        }
        //倒计时到5s的时候，播放警告声
        if (remains >= 0 && cmd == GameState.MACHINE_STATE_PLAYING) {
            final long total = 30;
            mTintText.setText(remains + "\"");
//            mCatchBtn.setPercentage((float) remains / (float) total);
            if (remains == 5) {
                playSoundEffect(GameManager.SOUND_WHISTLE);
            }
        }
    }


    private void reset() {
        hasMoved = false;
        isSideCameraCurrent = false;
        refreshBalance();
        handle(GameState.MACHINE_STATE_IDLE, -1, 0, 0);
        if (gameSocketClient != null) {
            gameSocketClient.onDisconnect();
            gameSocketClient = null;
        }
    }

    /**
     * 获取房间状态
     */
    private void getGameStatus() {
        BBLog.e(Constants.TAG, "mRoomId " + mRoomId + " joinRoomSucceed " + joinRoomSucceed);
        HttpMethods.getInstance().getGameStatus(mRoomId, new Subscriber<GameStatusBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                BBLog.e(Constants.TAG, e.toString());
            }

            @Override
            public void onNext(GameStatusBean gameStatusBean) {

                setRoomStatus(gameStatusBean.getData().getStatus());
                //如果房间是维护中的状态，就显示“维护中”的placeholder
                if (gameStatusBean.getData().getStatus() == 2) {
                    mPlaceText.setText("维护中...");
                    mCommentBtn.setAlpha(0.3f);
                    mCommentBtn.setEnabled(false);
                } else {
                    joinLiveRoom();
                }


                //将初始时候房间里已有的玩家保存下来
                for (MemberUserBean user : gameStatusBean.getData().getUserList()) {
                    if (mMembersMap.get(user.getId()) == null) {
                        mMembers.add(user);
                        mMembersMap.put(user.getId(), user);
                    }
                }
                //api返回的游戏总人数和members列表数量不一致，这里做一个处理，把两者的差值保存一下，用于后面计算房间总人数
                //mCountFix = Math.max(sessionResult.session.members_count - mMembers.size(), 0);
                updateMembersView();//刷新右上角头像显示
                mMembersView.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 充值 列表
     */
    private void loadChargeList() {

        HttpMethods.getInstance().getPaySetting(new Subscriber<PaySettingBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PaySettingBean paySettingBean) {
                weixinBeanList = paySettingBean.getData();
            }
        });
    }


    /**
     * 调用直播SDK加入房间的接口
     */
    private void joinLiveRoom() {
        if (liveStreamBean == null) {
            return;
        }
        //这里的ControlRole分为两种，观看时是Guest，视频延迟较大，等开始游戏上麦之后，切换为LiveGuest，这时候视频延迟会变小
        ILiveRoomOption option = new ILiveRoomOption(liveStreamBean.getMid())
                .controlRole("Guest")
                .videoMode(ILiveConstants.VIDEOMODE_BMUTE)// 设置视频模式(支持后台，普通、后台静默)
                .autoCamera(false)
                .autoMic(false)
                .authBits(AVRoomMulti.AUTH_BITS_JOIN_ROOM | AVRoomMulti.AUTH_BITS_RECV_AUDIO | AVRoomMulti.AUTH_BITS_RECV_CAMERA_VIDEO | AVRoomMulti.AUTH_BITS_RECV_SCREEN_VIDEO)
                .setRoomMemberStatusLisenter(new ILiveMemStatusLisenter() {
                    @Override
                    public boolean onEndpointsUpdateInfo(int eventid, String[] updateList) {
                        BBLog.e(Constants.TAG, "onEndpointsUpdateInfo " + "eventid " + eventid + " updateList " + updateList.toString());
                        if (eventid == ILiveConstants.TYPE_MEMBER_CHANGE_HAS_CAMERA_VIDEO) {//有用户上麦后，SDK会回调到这里，这时候将左上角小视频窗口打开
                            BBLog.e(Constants.TAG, "update info-up: " + eventid + updateList[updateList.length - 1]);
                            for (String id : updateList) {
                                if (!id.equals(liveStreamBean.getMid()) && !id.equals(liveStreamBean.getSid())) {
                                    Message message = new Message();
                                    message.what = CMD_UP_LIVE;
                                    message.obj = id;
                                    mHandler.sendMessage(message);
                                    break;
                                } else if (id.equals(liveStreamBean.getSid())) {
                                    sideCameraReady = true;
                                    break;
                                }
                            }
                        } else if (eventid == ILiveConstants.TYPE_MEMBER_CHANGE_NO_CAMERA_VIDEO) {//用户下麦后，关闭左上角小视频
                            BBLog.e(Constants.TAG, "update info-down: " + eventid + updateList[updateList.length - 1]);
                            for (String id : updateList) {
                                if (id.equals(liveStreamBean.getSid())) {
                                    sideCameraReady = false;
                                    return false;
                                }
                            }
                            Message message = new Message();
                            message.what = CMD_DOWN_LIVE;
                            mHandler.sendMessage(message);
                        }
                        return false;
                    }
                });
        int ret = ILiveRoomManager.getInstance().joinRoom(liveStreamBean.getRoom_id(), option, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mHandler.sendEmptyMessage(JOIN_ROOM_SUCCEED);//此处设置为加入房间成功，然后才可以按开始游戏按钮
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                BBLog.d(Constants.TAG, errMsg);
            }
        });
    }

    /**
     * 获取当前喵喵币
     */
    private void refreshBalance() {
        HttpMethods.getInstance().getUserWallet(new Subscriber<UserWalletBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(UserWalletBean userWalletBean) {
                mBalance = userWalletBean.getData().getBalance();
                mBalanceText.setText(mBalance + "");
            }
        });
    }

    /**
     * 根据room status 刷新UI
     *
     * @param status
     */
    private void setRoomStatus(int status) {
        BBLog.e(Constants.TAG, "joinRoomSucceed " + joinRoomSucceed + " status " + status);
        roomStatus = status;
        if (status == 0 && joinRoomSucceed) { //空闲
            mHandler.sendEmptyMessageDelayed(DELAY_UPDATE_VIEW, 3000);
            mStartBtn.setAlpha(1.0f);
            mStartBtn.setEnabled(true);
        } else if (status == 1 && joinRoomSucceed) { // 游戏
            mHandler.sendEmptyMessageDelayed(DELAY_UPDATE_VIEW, 3000);
            mStartBtn.setAlpha(0.3f);
            mStartBtn.setEnabled(false);
        } else if (status == 2) {  // 维护
            mPlaceHolder.setVisibility(View.VISIBLE);
            mPlaceText.setText("维护中...");
            mStartBtn.setAlpha(0.3f);
            mStartBtn.setEnabled(false);
        }
    }


    /**
     * 失败后显示抓取失败的Dialog
     *
     * @param remains
     */
    private void showFailedDialog(long remains) {
        if (getActivity() == null) {
            return;
        }

        final CommGameDialog gameFailedDialog = new CommGameDialog(getActivity(), getString(R.string.game_fail_tip),
                "当前剩余: " + mBalance + "个币", false);
        gameFailedDialog.show();
        gameFailedDialog.setCancelable(false);
        gameFailedDialog.setLeftButtonText(getString(R.string.game_back));
        gameFailedDialog.setRightButtonText(getString(R.string.game_continu));
        gameFailedDialog.setLeftListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                downMemberVideo();
                gameFailedDialog.dismiss();
                countDownTimer.cancel();
                countDownTimer = null;
                reset();
            }
        });
        gameFailedDialog.setRightListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (AppUtil.isFastDoubleClick(1500)) {
                    return;
                }
                gameFailedDialog.dismiss();
                countDownTimer.cancel();
                countDownTimer = null;
                //如果当前余额不足，下麦 ，则不能开始游戏，并且显示邀请朋友的Dialog ,
                BBLog.e(Constants.TAG, " mBalance " + mBalance + " mPrice " + mPrice);
                if (mBalance < mPrice) {
                    downMemberVideo();
                    showInviteDialog();
                    reset();
                    return;
                }
                if (gameSocketClient != null) {
                    hasMoved = false;
                    mBalance -= mPrice;//开始成功之后，先本地扣费，这个值后面结束游戏之后还会调api来刷新，确保正确
                    gameSocketClient.sendNextCmd();
                }
            }
        });

        countDownTimer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                gameFailedDialog.setRightButtonText("再来一局(" + l / 1000 + ")");
            }

            @Override
            public void onFinish() {
                downMemberVideo();
                gameFailedDialog.dismiss();
                reset();
            }
        }.start();
    }


    /**
     * 成功后显示抓取成功的Dialog
     */
    private void showSuccessDialog() {
        downMemberVideo();
        if (getActivity() == null) {
            return;
        }
        final CommGameDialog gameFailedDialog = new CommGameDialog(getActivity(), getString(R.string.game_success_tip),
                "", false);
        gameFailedDialog.show();
        gameFailedDialog.setCancelable(false);
        gameFailedDialog.setCommDialogContent(R.mipmap.game_victory_dialog_bg);
        gameFailedDialog.setLeftButtonText(getString(R.string.game_back));
        gameFailedDialog.setRightButtonText(getString(R.string.game_look_wawa));
        gameFailedDialog.setLeftListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gameFailedDialog.dismiss();
                reset();
            }
        });
        gameFailedDialog.setRightListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (AppUtil.isFastDoubleClick(1500)) {
                    return;
                }
                gameFailedDialog.dismiss();
                reset();
                Intent intent = new Intent(getActivity(), GameDetailActivity.class);
                intent.putExtra(GameDetailActivity.GAME_ID, session);
                startActivity(intent);

            }
        });
    }

    // 喵喵币不够了 显示邀请
    private void showInviteDialog() {
        downMemberVideo();
        SweetAlertDialog dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("喵喵币不够了")
                .setCancelText("取消")
                .setConfirmText("分享邀请码")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(getActivity(), WawaMainActivity.class);
                        startActivity(intent);
                    }
                });
        dialog.show();
    }

    /**
     * 收到全局tcp通知有用户进来或出去时，刷新右上角的玩家头像和数量
     *
     * @param isJoin
     * @param member
     */
    private void updateRoomMember(boolean isJoin, RoomUserMessage member) {
        if (String.valueOf(member.getRoomId()).equals(mRoomId)) { //判断是不是当前房间
            if (isJoin) { //进入房间
                if (mMembersMap.get(member.getUid()) == null) {
                    MemberUserBean memberUserBean = new MemberUserBean(member.getUid(), member.getAvatar(), member.getNickname());
                    mMembers.addFirst(memberUserBean);
                    mMembersMap.put(member.getUid(), memberUserBean);
                }
                addDanmaku("系统消息", member.getNickname() + " 进入房间", 1.0f);
            } else {//  离开房间
                MemberUserBean who = null;
                for (MemberUserBean memberUserBean : mMembers) {
                    if (memberUserBean.getId() == member.getUid()) {
                        who = memberUserBean;
                        break;
                    }
                }
                if (who != null) {
                    mMembers.remove(who);
                    mMembersMap.remove(who.getId());
                    addDanmaku("系统消息", member.getNickname() + " 离开房间", 1.0f);
                }
            }
            updateMembersView();
        }
    }

    public void addDanmaku(String name, String message, float factor) {
        if (getActivity() != null) {
            addMsgToList(name, message);
//            DanmakuItem item = new DanmakuItem(getActivity(), message, mDanmakuView.getWidth());
//            item.setSpeedFactor(factor);
//            item.setTextColor(getResources().getColor(R.color.themeColor));
//            item.setTextSize(AppUtil.spToPx(getActivity(), 5));
//            mDanmakuView.addItem(item);
//            mDanmakuView.show();
        }
    }

    public void addMsgToList(String name, String context) {
        ChatEntity entity = new ChatEntity();
        entity.setSenderName(name);
        entity.setContext(context);
        notifyRefreshListView(entity);

        mListViewMsgItems.setVisibility(View.VISIBLE);

        if (mListViewMsgItems.getCount() > 1) {
            if (true)
                mListViewMsgItems.setSelection(0);
            else
                mListViewMsgItems.setSelection(mListViewMsgItems.getCount() - 1);
        }
    }

    /**
     * 通知刷新消息ListView
     */
    private void notifyRefreshListView(ChatEntity entity) {
        mBoolNeedRefresh = true;
        mTmpChatList.add(entity);
        if (mBoolRefreshLock) {
            return;
        } else {
            doRefreshListView();
        }
    }

    private void setDanmuToList() {
        if (mArrayListChatEntity.size() > 5) {
            mArrayListChatEntity.remove(0);
        }
    }

    /**
     * 刷新ListView并重置状态
     */
    private void doRefreshListView() {
        if (mBoolNeedRefresh) {
            mBoolRefreshLock = true;
            mBoolNeedRefresh = false;
            mArrayListChatEntity.addAll(mTmpChatList);
            setDanmuToList();
            mTmpChatList.clear();
            mChatMsgListAdapter.notifyDataSetChanged();

            if (null != mTimerTask) {
                mTimerTask.cancel();
            }
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(REFRESH_LISTVIEW);
                }
            };
            mTimer.schedule(mTimerTask, MINFRESHINTERVAL);
        } else {
            mBoolRefreshLock = false;
        }
    }

    // 清除老房间数据
    private void clearOldData() {
        mArrayListChatEntity.clear();
        mBoolNeedRefresh = true;
        if (mBoolRefreshLock) {
            return;
        } else {
            doRefreshListView();
        }
    }

    //刷新右上角玩家头像，多于三个时显示最后三个人头像
    private void updateMembersView() {
        int count = mMembers.size();
        int viewCount = mAvatars.size();
        mMemberCount.setText((count + mCountFix) + "");
        mMemberMore.setVisibility((count > 3 ? View.VISIBLE : View.GONE));
        int limit = Math.min(viewCount, count);
        for (int i = 0; i < limit; i++) {
            MemberUserBean member = mMembers.get(i);
            ImageView view = mAvatars.get(i);
            view.setVisibility(View.VISIBLE);
            if (StringUtil.isBlank(member.getAvatar())){
                Picasso.with(getActivity()).load(R.mipmap.ic_launcher).transform(new CircleTransform()).into(view);
            }else{
                Picasso.with(getActivity()).load(member.getAvatar()).transform(new CircleTransform()).into(view);
            }
        }
        if (count < viewCount) {
            for (int i = limit; i < viewCount; i++) {
                mAvatars.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void playSoundEffect(int index) {
        if (needSoundEffect) {
            GameManager.getInstance().playSound(index);
        }
    }

    //来自SDK的自定义消息通知，弹幕类消息会回调到这里
    @Override
    public void onNewTextMsg(ILVText text, String senderId, TIMUserProfile userProfile) {
        BBLog.e(Constants.TAG, "received message: NEWTEXT " + text.getText() + " senderId " + senderId +
                " text DestId " + text.getDestId() + " mRoomId " + mRoomId );
        if (text.getDestId().equals(mRoomId)) {
            Bundle bundle = new Bundle();
            bundle.putString("id", senderId);
            bundle.putString("text", text.getText());
            Message msg = new Message();
            msg.what = CMD_TEXT_MESSAGE;
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 接收消息：主要是上麦下麦，以及上机成功、抓取成功、抓取失败这几类消息，会回调到这里
     *
     * @param cmd
     * @param id
     * @param userProfile
     */
    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {
        BBLog.e(Constants.TAG, "received message: CUSTOM" + cmd.getText() + " " + cmd.getCmd() +
                " id " + id);
        if (cmd.getDestId().equals(mRoomId)) {
            int code = cmd.getCmd();
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("text", cmd.getText());
            message.setData(bundle);
            if (code == Constants.LIVE_CMD_UP_MACHINE) {
                message.what = CMD_MESSAGE_UP;
                mHandler.sendMessage(message);
            } else if (code == Constants.LIVE_CMD_FAILED || code == Constants.LIVE_CMD_SUCCEED) {
                message.what = CMD_MESSAGE_DOWN;
                mHandler.sendMessage(message);
            }
        }
    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {
        BBLog.e(Constants.TAG, "received message: OTHER " + message.getCustomStr());
    }

    private int sendCustomCmd(int code, String message) {
        ILVCustomCmd customCmd = new ILVCustomCmd();
        customCmd.setDestId(mRoomId);
        customCmd.setCmd(code);
        customCmd.setText(message);
        customCmd.setType(ILVText.ILVTextType.eGroupMsg);
        return ILVLiveManager.getInstance().sendCustomCmd(customCmd, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                BBLog.e(Constants.TAG, "onSuccess");
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                BBLog.e(Constants.TAG, "onError");
            }
        });
    }

    /**
     * 调用SDK的上麦借口
     */
    private void upMemberVideo() {
        if (!joinRoomSucceed) {
            return;
        }

        if (roomStatus == 2) {//房间维护中
            return;
        }

        if (isVideoLiveOnline) {
            return;
        }
        ILVLiveManager.getInstance().upToVideoMember("LiveGuest", true, true, new ILiveCallBack<ILVChangeRoleRes>() {
            @Override
            public void onSuccess(ILVChangeRoleRes data) {
                mHandler.sendEmptyMessage(UP_VIDEO_LIVE_SUCCEED);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                BBLog.e(Constants.TAG, "上麦失败");
            }
        });
    }

    /**
     * 调用SDK的下麦接口
     */
    private void downMemberVideo() {
        BBLog.e("downMemberVideo");
        if (!isVideoLiveOnline) {
            return;
        }
        try {
            ILVLiveManager.getInstance().downToNorMember("Guest", new ILiveCallBack<ILVChangeRoleRes>() {
                @Override
                public void onSuccess(ILVChangeRoleRes data) {
                    mHandler.sendEmptyMessage(DOWN_VIDEO_LIVE_SUCCEED);
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    BBLog.e(Constants.TAG, "下麦 失败");
                }
            });
        } catch (Exception e) {
            BBLog.e(Constants.TAG, "e " + e.toString());
        }

    }


    @Override
    public void receiveGameMessage(final int cmdType, final byte[] data) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    handleServerMsg(cmdType, data);
                }
            });

        }
    }

    /**
     * 发送开始录屏消息
     */
    private void sendStartRecordMessage(int gameId) {
        Bundle bundle = new Bundle();
        bundle.putInt("gameId", gameId);
        Message message = new Message();
        message.what = START_GAME;
        message.setData(bundle);
        EventBus.getDefault().post(message);
    }

    /**
     * 接收消息处理
     *
     * @param cmdType
     * @param data
     */
    private void handleServerMsg(int cmdType, byte[] data) {
        BBLog.e(Constants.TAG, "kkkkkkkk handleServerMsg:" + " cmdType --> " + cmdType);
        if (cmdType == PbUtil.RESPONSE) {//返回信息
            try {
                Game.Ret response = Game.Ret.parseFrom(data);
                BBLog.e(Constants.TAG, "response OriginMsgType " + response.getOriginMsgType() + " Code " + response.getCode() + " msg " + response.getMsg() + " Data " + response.getData());
                if (response != null) {
                    if (response.getCode() == 0) {
                        switch (response.getOriginMsgType()) {
                            case PbUtil.AUTH://anth 认证成功

                                if (StringUtil.isNotBlank(response.getData())) {
                                    session = Integer.valueOf(response.getData());
                                    sendStartRecordMessage(session);
                                }
                                BBLog.e(Constants.TAG, "session " + session);
                                handle(GameState.MACHINE_STATE_STANDBY, -1, 0, session);
                                break;
                            case PbUtil.CONTROL:

                                break;

                            case PbUtil.NEXT://anth 认证成功
                                if (StringUtil.isNotBlank(response.getData())) {
                                    session = Integer.valueOf(response.getData());
                                    sendStartRecordMessage(session);
                                }
                                BBLog.e(Constants.TAG, "session " + session);
                                handle(GameState.MACHINE_STATE_STANDBY, -1, 0, session);
                                break;
                        }

                    } else if (response.getCode() == 4 && response.getOriginMsgType() == PbUtil.NEXT) { // 余额不足 下麦
                        downMemberVideo();
                        reset();
                    } else {
                        if (getActivity() != null) {
                            ToastUtil.showToast("加入游戏失败!");
                        }
                        reset();
                    }
                }

            } catch (InvalidProtocolBufferException e) {
                BBLog.e(Constants.TAG, e.toString());
            }

        } else if (cmdType == PbUtil.STATE) {//游戏中状态

            try {
                BBLog.e(Constants.TAG, "msg " + PbUtil.bytesToHexString(data));
                Game.Status state = Game.Status.parseFrom(data);
                BBLog.e(Constants.TAG, "response State " + state.getState() + " TimeRemain " + state.getTimeRemain() + " TimeUsed " + state.getTimeUsed());
                if (state.getState() == GameState.GAME_CRAWL_FAILED) {//失败
                    handle(GameState.MACHINE_STATE_AWARDING_FAILED, -1, 0, 0);
                } else if (state.getState() == GameState.GAME_CRAWL_SUCCEED) {//成功
                    handle(GameState.MACHINE_STATE_AWARDING_SUCCESS, -1, 0, 0);
                } else if (state.getState() == GameState.GAME_CRAWLING) {// 下爪
                    handle(GameState.MACHINE_STATE_CRAWLING, -1, 0, 0);
                } else if (state.getState() == GameState.GAME_CRAWL_PLAYING) {//游戏中
                    handle(GameState.MACHINE_STATE_PLAYING, state.getTimeRemain(), state.getTimeUsed(), 0);
                } else if (state.getState() == GameState.GAME_CRAWL_ERROR) {//故障
                    ToastUtil.showToast("游戏故障");
                }

            } catch (InvalidProtocolBufferException e) {
                BBLog.e(Constants.TAG, e.toString());
            }

        }

    }



    @Override
    public void onResume() {
        super.onResume();
        BBLog.e(Constants.TAG, "onResume ");
        ILiveRoomManager.getInstance().onResume();
        refreshBalance();
    }

    @Override
    public void onPause() {
        super.onPause();
        BBLog.e(Constants.TAG, "game fragment onPause ");
        if (joinRoomSucceed) {
            ILiveRoomManager.getInstance().onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        GameManager.getInstance().unregisterObserver(this);
        mHandler.removeCallbacksAndMessages(null);
        unbinder.unbind();
    }


    /**
     * 获取当前用户的 profile
     */
    private void getUserInfo(long userId) {
        HttpMethods.getInstance().getUserInfo(userId, new Subscriber<UserInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfo userInfo) {
                if (userInfo != null && userInfo.isSucceed()) {
                    if (StringUtil.isNotBlank(userInfo.getData().getAvatar())) {
                        gameGuestLayout.setVisibility(View.VISIBLE);
                        Picasso.with(getActivity()).load(userInfo.getData().getAvatar()).into(currentUpUserIv);
                        currentUpUserTv.setText(userInfo.getData().getNickname());
                    }


                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //注释掉，不保存状态，与actvity一起被回收
//        super.onSaveInstanceState(outState);
    }
}
