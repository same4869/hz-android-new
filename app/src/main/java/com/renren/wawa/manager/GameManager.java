package com.renren.wawa.manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wawaji.vip.R;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.interfaces.RoomObserver;
import com.renren.wawa.model.EmailLoginBean;
import com.renren.wawa.model.RoomStatusUpdate;
import com.renren.wawa.model.RoomUserMessage;
import com.renren.wawa.push.MessageEvent;
import com.renren.wawa.push.PushUtil;
import com.renren.wawa.socket.RoomSocketClient;
import com.renren.wawa.socket.RoomStatus;
import com.renren.wawa.sp.CommSetting;
import com.renren.wawa.utils.AppUtil;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.NetWorkUtil;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import room.protobuf.Wawa;

//这个类是后台全局tcp连接的管理类
public class GameManager implements RoomSocketClient.ReceiveGameMessage {
    public static final int SOUND_READY_GO = 0;
    public static final int SOUND_MOVE = 1;
    public static final int SOUND_WHISTLE = 2;
    public static final int SOUND_TAKE = 3;
    public static final int SOUND_SUCCEED = 4;
    public static final int SOUND_FAILED = 5;


    private static GameManager mInstance;

    private Context mContext;
    private CopyOnWriteArrayList<RoomObserver> roomObservers;


    private SoundPool mSoundPool;
    private int[] mSoundIds = new int[6];

    private Timer mTimer;

    public String mTcp;
    public String mToken;

    public String mLiveId;
    public String mLiveSig;

    private boolean isConnected;
    private boolean isLiveSDKLogin;//是否登录
    private boolean isLiveSDKLogining; // 是否正在登录
    private long uid;
    private EmailLoginBean.DataBean.UserBean userBean;

    private RoomSocketClient roomSocketClient;

    public static synchronized GameManager getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("Game manager is not initialized");
        }
        return mInstance;
    }

    public static synchronized void initialize(Context context) {
        if (mInstance == null) {
            mInstance = new GameManager(context);
        }
    }

    private GameManager(Context context) {
        mContext = context;
        roomObservers = new CopyOnWriteArrayList<>();
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        int[] resIds = new int[]{R.raw.readygo, R.raw.move, R.raw.whistle, R.raw.take, R.raw.result_succeed, R.raw.result_failed};
        for (int i = 0; i < resIds.length; i++) {
            mSoundIds[i] = mSoundPool.load(mContext, resIds[i], 1);
        }
    }

    public void playSound(int index) {
        mSoundPool.play(mSoundIds[index], 1, 1, 0, 0, 1);
    }

    /**
     * 开始连接
     */
    public void start() {
        EmailLoginBean emailLoginBean = UserManager.getEmailLoginBean();
        if (emailLoginBean == null) {
            ToastUtil.showToast("登录存储信息为空");
        } else {
            String mTcp = emailLoginBean.getData().getSocketUrl();
            mToken = emailLoginBean.getData().getSessionId();
            mLiveId = emailLoginBean.getData().getLive().getIdentifier();
            mLiveSig = emailLoginBean.getData().getLive().getSign();
            BBLog.e(Constants.TAG,"mLiveId "+mLiveId);
            uid = emailLoginBean.getData().getUserInfo().getId();
            userBean = emailLoginBean.getData().getUserInfo();
//            PreferenceManager.getInstance().setMainTcp(mTcp);
//            PreferenceManager.getInstance().setSameCountTcp("");
            connect(mTcp);
        }

    }

    public void registerObserver(RoomObserver observer) {
        roomObservers.add(observer);
        if (StringUtil.isNotBlank(CommSetting.getToken())) {
            checkConnection();
        }
    }

    public void unregisterObserver(RoomObserver observer) {
        roomObservers.remove(observer);
    }

    public void joinRoom(long roomId) {
        if (roomSocketClient != null) {
            roomSocketClient.sendJoinRoomMsg(roomId);
        }
    }

    public void leaveRoom(long roomId) {
        if (roomSocketClient != null) {
            roomSocketClient.sendLeftRoomMsg(roomId);
        }
    }


    /**
     * 直播sdk 登录
     */
    private void loginLiveSDK() {
        if(isLiveSDKLogining){
            BBLog.e(Constants.TAG, "isLiveSDKLogin 正在登录");
            return;
        }
        isLiveSDKLogining = true;
        ILiveLoginManager.getInstance().iLiveLogin(mLiveId, mLiveSig, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                BBLog.e(Constants.TAG, data.toString());
                isLiveSDKLogin = true;
                isLiveSDKLogining = false;
                BBLog.e(Constants.TAG, "isLiveSDKLogin 登录成功");
                //初始化程序后台后消息推送
                PushUtil.getInstance();
                //初始化消息监听
                MessageEvent.getInstance();
                // 小米  华为 推送注册
                PushUtil.registerPush();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                BBLog.e(Constants.TAG, "errCode " + errCode + "errMsg " + errMsg);
                isLiveSDKLogin = false;
                isLiveSDKLogining = false;
                BBLog.e(Constants.TAG, "isLiveSDKLogin 登录失败");
            }
        });
    }

    /**
     * 登出腾讯SDK
     */
    public void logoutLiveSdk() {
        isLiveSDKLogin = false;
        BBLog.e(Constants.TAG, "logoutLiveSdk ");

        if (Constants.isDebug)
            ILiveLoginManager.getInstance().iLiveLogout(new ILiveCallBack() {
                @Override
                public void onSuccess(Object o) {
                    BBLog.e(Constants.TAG, "onSuccess " + o.toString());
                    isLiveSDKLogin = false;
                }

                @Override
                public void onError(String s, int i, String s1) {
                    BBLog.e(Constants.TAG, "onError " + s.toString() + " i " + i + " s1 " + s1);
                    isLiveSDKLogin = false;
                }
            });

    }

    /**
     * 连接roomserver
     */
    public void connect(String mTcp) {
        if (mTcp == null || mToken == null) {
            return;
        }
        this.mTcp = mTcp;
        onDisConnect();
        BBLog.e(Constants.TAG,"mTcp "+mTcp+" mToken "+mToken +" uid "+uid);

        String[] ipPort = mTcp.split(":");
        if (ipPort.length == 2 && roomSocketClient == null) {
            roomSocketClient = new RoomSocketClient(ipPort[0], ipPort[1], uid, userBean.getNickname(), userBean.getAvatar(), mToken, this);
            roomSocketClient.connectServer();
            isConnected = true;
        }

    }


    /**
     * 断开长连接
     */
    private void onDisConnect() {
        BBLog.e(Constants.TAG, "GameManager onDisConnect");
        if (roomSocketClient != null) {
            roomSocketClient.onDisconnect();
            roomSocketClient = null;
            isConnected = false;
        }
    }

    /**
     * 定时5S发送心跳
     */
    private void scheduleHeartbeat() {
        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (roomSocketClient != null) {
                    roomSocketClient.sendPingMsg();
                }
            }
        }, 0, 5000);
    }

    private void checkConnection() {
        BBLog.e(Constants.TAG, "checkConnection " + isConnected);
        if (!isConnected) {
            connect(mTcp);
        }

        if (!isLiveSDKLogin)
            loginLiveSDK();
    }

    private synchronized void roomStatusUpdated(RoomStatusUpdate roomStatusUpdate) {
        for (RoomObserver observer : roomObservers) {
            observer.onRoomStateUpdated(roomStatusUpdate);
        }
    }


    private synchronized void broadcastReceived(RoomUserMessage roomUserMessage) {
        for (RoomObserver observer : roomObservers) {
            observer.onBroadcastReceived(roomUserMessage);
        }
    }

    private synchronized void roomMemberUpdated(boolean isJoin, RoomUserMessage roomUserMessage) {
        for (RoomObserver observer : roomObservers) {
            observer.onRoomMemberUpdated(isJoin, roomUserMessage);
        }
    }

    /**
     * 接收服务端  长连接  消息
     * @param data
     */
    @Override
    public void receiveGameMessage(byte[] data) {
        try {
            Wawa.Protocol protocol = Wawa.Protocol.parseFrom(data);

            if (protocol.hasPing()) {

            } else if (protocol.hasNotice()) {
                Wawa.Notice notice = protocol.getNotice();

            } else if (protocol.hasAck()) {
                BBLog.e(Constants.TAG,"receiveGameMessage hasAck");
                Wawa.Ack responseProtocol = protocol.getAck();
                if (responseProtocol != null && responseProtocol.getOriginalType().getNumber() == RoomStatus.CONNECT && responseProtocol.getResponseCode() == 0) {
                    scheduleHeartbeat();// 发送心跳
                }

            } else if (protocol.hasGoodJob()) {//抓到娃娃通知

                Wawa.GoodJob cheers = protocol.getGoodJob();
                //BBLog.e(Constants.TAG, "抓到娃娃通知 url " + cheers.getAvatar() + "  nickName:" + cheers.getNickname());

                RoomUserMessage roomUserMessage = new RoomUserMessage(cheers.getRoomId(), cheers.getUid(), cheers.getUname(), cheers.getUimg());
                broadcastReceived(roomUserMessage);

            } else if (protocol.hasJoin()) {
                BBLog.e(Constants.TAG, "有人进来了");
                Wawa.Join userJoin = protocol.getJoin();
                roomMemberUpdated(true, new RoomUserMessage(userJoin.getRoomId(), userJoin.getUid(), userJoin.getUname(), userJoin.getUimg()));

            } else if (protocol.hasLeft()) {
                 BBLog.e(Constants.TAG, "有人离开了");
                Wawa.Left userLeft = protocol.getLeft();
                roomMemberUpdated(false, new RoomUserMessage(userLeft.getRoomId(), userLeft.getUid(), userLeft.getUname(), userLeft.getUimg()));
            } else if (protocol.hasRoomStatus()) {//更新房间状态
                Wawa.RoomStatus roomStatus = protocol.getRoomStatus();
                RoomStatusUpdate roomStatusUpdate = new RoomStatusUpdate(roomStatus.getRoomId(), roomStatus.getStatus());
                roomStatusUpdated(roomStatusUpdate);
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            BBLog.e(Constants.TAG, e.toString());
        }

    }

    /**
     * 异常
     */
    @Override
    public void roomSocketError() {
        BBLog.e(Constants.TAG, "GameManager roomSocketError");
        if (!NetWorkUtil.checkNetWork(WawaApplication.getInstance())) {
            onDisConnect();
        }
    }
}
