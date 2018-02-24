package com.renren.wawa.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;


import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.renren.wawa.R;
import com.renren.wawa.adapter.GameRoomAdapter;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.config.Constants;
import com.renren.wawa.db.UploadInfoDBHelper;
import com.renren.wawa.fragment.GameDetailFragment;
import com.renren.wawa.fragment.GameFragment;
import com.renren.wawa.manager.GameManager;
import com.renren.wawa.manager.QiNiuUploadManager;
import com.renren.wawa.manager.UserManager;
import com.renren.wawa.model.AppUploadBean;
import com.renren.wawa.model.RoomInfoBean;
import com.renren.wawa.model.UploadInfo;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.service.ScreenRecorderService;
import com.renren.wawa.sp.CommSetting;
import com.renren.wawa.utils.AppUtil;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.CommDialog;
import com.renren.wawa.view.HorizontalListView;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.qqtheme.framework.util.ConvertUtils;
import rx.Subscriber;

import static com.renren.wawa.config.Constants.END_GAME;
import static com.renren.wawa.config.Constants.START_GAME;
import static com.renren.wawa.config.Constants.isScreenRecord;


public class GameRoomActivity extends BaseTitleBarActivity {

    private static String TAG = "GameRoomActivity";
    @BindView(R.id.game_controller)
    FrameLayout gameController;
    @BindView(R.id.game_detail)
    FrameLayout gameDetail;
    @BindView(R.id.game_room_root)
    FrameLayout mLayoutMain;
    @BindView(R.id.input_container)
    View mInputContainer;
    @BindView(R.id.input_shortcut)
    HorizontalListView mShortcutList;
    @BindView(R.id.input_field)
    EditText mEditText;
    @BindView(R.id.game_scrollview)
    ScrollView scrollView;
    @BindView(R.id.next_room_btn)
    ImageView nextRoomBtn;
    @BindView(R.id.pre_room_btn)
    ImageView preRoomBtn;

    private GameFragment mFragment;
    private GameDetailFragment gameDetailFragment;

    private String mRoomId;
    private String[] mShortcut;
    private GameRoomAdapter mAdapter;

    private boolean showKeyboard;
    private boolean needPlayMusic;
    private MediaPlayer mMediaPlayer;

    private static final int SCREEN_RECORD_PERMISSION_CODE = 1;
    private static final int REQUEST_PERMISSION_CODE = 2;
    private MediaProjectionManager mProjectionManager;
    private MediaProjection mMediaProjection;
    private ScreenRecorderService mScreenRecorderService;
    private int gameId;
    private String qiNiuToken;
    private String qiNiuKey;
    private int currentRoomIdIndex = -1;

    public void showInputView() {
        mInputContainer.setVisibility(View.VISIBLE);
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_game_room;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        mRoomId = getIntent().getStringExtra("room_id");
        BBLog.d("kkkkkkkk", "currentRoomIdIndex --> " + currentRoomIdIndex + " mRoomId --> " + mRoomId);

        //      GameFragment初始化
        mFragment = new GameFragment();
        gameDetailFragment = new GameDetailFragment();
        mFragment.setRoom(mRoomId);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.game_controller, mFragment);
        fragmentTransaction.replace(R.id.game_detail,  gameDetailFragment);
        fragmentTransaction.commit();




//        游戏界面 和 列表 发送弹幕  初始化
        int height = AppUtil.getScreenHeight(this) - AppUtil.dp2px(getStatusBarHeight(this));
        int width = AppUtil.getScreenWidth(this);
        gameController.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        gameDetail.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));

        mAdapter = new GameRoomAdapter(this);

        mShortcut = new String[]{"666", "棒棒哒", "让一让", "鶸", "233333", "👏", "求开光"};
        mShortcutList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, android.R.id.text1, mShortcut));

    }

    @Override
    public void initData() {
        needPlayMusic = CommSetting.getSettingBackgroundMusic();
        mMediaPlayer = MediaPlayer.create(this, new Date().getTime() % 2 == 0 ? R.raw.bgm01 : R.raw.bgm02);
        mMediaPlayer.setLooping(true);

        GameManager.getInstance().joinRoom(Long.valueOf(mRoomId));//此处是向后台全局tcp连接发送进入房间的消息，和互动直播的进入房间不是一种
        initScreenRecord();
        checkPermission();

        getRoomInfo();

        initListener();

    }

    private void initListener() {
        EventBus.getDefault().register(this);

        //此处通过监控ViewTree来计算当前输入法是否显示，如果消失则隐藏弹幕输入的控件
        mLayoutMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mLayoutMain.getWindowVisibleDisplayFrame(r);
                int minKeyboardHeight = 200;
                int statusBarHeight = getStatusBarHeight(GameRoomActivity.this);
                int screenHeight = mLayoutMain.getRootView().getHeight();
                int height = screenHeight - (r.bottom - r.top);


                if (showKeyboard) {
                    if (height - statusBarHeight < minKeyboardHeight) {
                        showKeyboard = false;
                        mInputContainer.setVisibility(View.GONE);
                    }
                } else {
                    if (height - statusBarHeight > minKeyboardHeight) {
                        showKeyboard = true;
                    }
                }
            }
        });
    }


    /**
     * 申请权限
     */
    void checkPermission() {
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.CAMERA);
            if ((checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.RECORD_AUDIO);
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if ((checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.READ_PHONE_STATE);
            if ((checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
            if (permissionsList.size() != 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_PERMISSION_CODE);
            }
        }
    }


    /**
     * 申请授权处理结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                try {
                    if (grantResults.length > 0) {
                        //存放没授权的权限
                        List<String> deniedPermissions = new ArrayList<>();
                        for (int i = 0; i < grantResults.length; i++) {
                            int grantResult = grantResults[i];
                            String permission = permissions[i];
                            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                                deniedPermissions.add(permission);
                            }
                        }
                        BBLog.e(deniedPermissions.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化录屏
     */
    private void initScreenRecord() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            //录屏服务
            Intent intentScreenRecord = new Intent(this, ScreenRecorderService.class);
            bindService(intentScreenRecord, connection, BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (needPlayMusic) {
            mMediaPlayer.start(); //这个mediaplayer用于在后台持续播放背景音乐
        }
    }

    @Override
    public void onPause() {
        if (needPlayMusic) {
            mMediaPlayer.pause();
        }
        BBLog.e(Constants.TAG, "game room activity onPause ");
        mFragment.forceLeaveRoom();
        finish();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        stoptScreenRecorder();
        if (needPlayMusic) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        GameManager.getInstance().leaveRoom(Long.valueOf(mRoomId));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            unbindService(connection);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mMediaProjection != null) {
                mMediaProjection.stop();
            }
        }

        if(mFragment != null){
            mFragment = null;
        }

        if(gameDetailFragment!=null){
            gameDetailFragment = null;
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        exitGame();
    }


    @OnItemClick(R.id.input_shortcut)
    public void clickShortcut(int i) {
        String message = mEditText.getText().toString() + mShortcut[i];
        mEditText.setText(message);
        mEditText.setSelection(message.length());
    }

    @OnClick(R.id.send_input)
    public void sendInput() {
        sendMessage(mEditText.getText().toString());
        mEditText.setText(null);
        closeInputMethod();
    }

    private void sendMessage(String message) {
        if (!message.isEmpty()) {
            mFragment.addMsgToList(UserManager.getCurUserNickName() + ":", message);
//            mFragment.addDanmaku(UserManager.getCurUserNickName() + ": " + message, 2.0f);
            BBLog.e(Constants.TAG, "mRoomId " + mRoomId);
            ILVText text = new ILVText(ILVText.ILVTextType.eGroupMsg, mRoomId, message);
            ILVLiveManager.getInstance().sendText(text, new ILiveCallBack() {
                @Override
                public void onSuccess(Object data) {
                    BBLog.e(Constants.TAG, data.toString());
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    BBLog.e(Constants.TAG, "onError: " + errMsg + " errCode " + errCode + " errMsg " + errMsg);
                }
            });
        }
    }

    private void exitGame() {
        if (!mFragment.canLeaveRoom()) {

            commDialog = new CommDialog(this, "", "游戏进行中，确定要退出吗", false);
            commDialog.setCanceledOnTouchOutside(false);
            commDialog.show();
            commDialog.hideTitle();
            commDialog.setLeftButtonText("继续游戏");
            commDialog.setRightButtonText("确定退出");
            commDialog.setLeftButtonPositive(false);
            commDialog.setRightButtonPositive(true);
            commDialog.setRightListener(new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancelDialog();
                    mFragment.forceLeaveRoom();
                    finish();
                }
            });
            commDialog.setLeftListener(new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancelDialog();
                }
            });
        } else {
            mFragment.forceLeaveRoom();
            finish();
        }
    }

    /**
     * 获取房间信息
     */
    private void getRoomInfo() {
        HttpMethods.getInstance().getRoomInfo(mRoomId, new Subscriber<RoomInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                BBLog.e(Constants.TAG, "e " + e.toString());
            }

            @Override
            public void onNext(RoomInfoBean roomInfoBean) {
                mAdapter.setContent(roomInfoBean.getData().getGameList(), roomInfoBean.getData().getWawa());
                mFragment.setLiveStream(roomInfoBean.getData().getRoomInfo().getLive(), roomInfoBean.getData().getRoomInfo().getCoin());
                gameDetailFragment.setData(roomInfoBean);
            }
        });
    }

    public int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void closeInputMethod() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm.isActive()) {
                View view = getCurrentFocus();
                if (view != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 准备录屏和投屏
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void prepareRecording() {
        if (mMediaProjection == null) {
            startActivityForResult(mProjectionManager.createScreenCaptureIntent(), SCREEN_RECORD_PERMISSION_CODE);
            return;
        }
        startScreenRecorder(String.valueOf(gameId));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (SCREEN_RECORD_PERMISSION_CODE == requestCode && resultCode == Activity.RESULT_OK) {
            mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
            if (mMediaProjection == null) {
                BBLog.e("@@", "media projection is null");
                return;
            }
            startScreenRecorder(String.valueOf(gameId));
        }

    }

    /**
     * 结束录屏
     */
    private void stoptScreenRecorder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mScreenRecorderService != null && mScreenRecorderService.isRunning()) {
                mScreenRecorderService.stopRecord();
                if (Constants.isDebug)
                    ToastUtil.showToast("结束录屏");
                UploadInfoDBHelper.getInstance(this).save(new UploadInfo(String.valueOf(gameId), 0, UserManager.getCurUserId()));
                BBLog.e("qiniu", "UploadInfoDBHelper " + UploadInfoDBHelper.getInstance(WawaApplication.getInstance()).
                        getAllData(UserManager.getCurUserId()).toString());
                QiNiuUploadManager qiNiuUploadManager = new QiNiuUploadManager();
                qiNiuUploadManager.upLoadVideo(gameId, qiNiuKey, qiNiuToken);

            }

        }
    }

    /**
     * 开始录屏
     */
    private void startScreenRecorder(String mScreenRecordName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            mScreenRecorderService.setConfig(720, 1280, 1, mScreenRecordName);
            mScreenRecorderService.setMediaProject(mMediaProjection);
            mScreenRecorderService.startRecord();
            if (Constants.isDebug)
                ToastUtil.showToast("开始录屏");
        }
    }


    /**
     * 获取七牛  token
     */
    private void getVideoConfig(final int sessionId) {
        //类型 1 日志 2 游戏视频
        HttpMethods.getInstance().getAppUpload(2, sessionId, new Subscriber<AppUploadBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AppUploadBean appUploadBean) {
                if (appUploadBean != null && appUploadBean.isSucceed()) {
                    qiNiuToken = appUploadBean.getData().getToken();
                    qiNiuKey = appUploadBean.getData().getFile_name();
                }
            }
        });
    }

    /**
     * 录屏 服务连接
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BBLog.e(TAG, "onServiceConnected");
            ScreenRecorderService.RecordBinder binder = (ScreenRecorderService.RecordBinder) service;
            mScreenRecorderService = binder.getRecordService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            BBLog.e(TAG, "onServiceDisconnected");
        }
    };

    @Subscribe
    public void onEventMainThread(Message message) {
        switch (message.what) {
            case START_GAME://开始游戏
                if (!isScreenRecord) {
                    return;
                }
                BBLog.e("开始游戏 " + ContextCompat.checkSelfPermission(GameRoomActivity.this, Manifest.permission.RECORD_AUDIO));
                if (ContextCompat.checkSelfPermission(GameRoomActivity.this, Manifest.permission.RECORD_AUDIO)
                        == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(GameRoomActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    gameId = message.getData().getInt("gameId");
                    getVideoConfig(gameId);
                    BBLog.e(TAG, "开始游戏  gameId " + gameId);
                    prepareRecording();
                } else {
                    BBLog.e("没有权限");
                }
                break;
            case END_GAME://结束游戏
                if (!isScreenRecord) {
                    return;
                }
                BBLog.e("结束游戏");
                if (ContextCompat.checkSelfPermission(GameRoomActivity.this, Manifest.permission.RECORD_AUDIO) ==
                        PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(GameRoomActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    stoptScreenRecorder();
                } else {
                    BBLog.e("没有权限");
                }
                break;

        }
    }

//    @OnClick(R.id.next_room_btn)
//    public void onClickNextRoom() {
//        currentRoomIdIndex = currentRoomIdIndex + 1;
//        int roomId = getRoomIdInList(currentRoomIdIndex);
//        Intent intent = new Intent(this, GameRoomActivity.class);
//        intent.putExtra("room_id", String.valueOf(roomId));
//        intent.putIntegerArrayListExtra("room_id_list", roomIdsList);
//        startActivity(intent);
//        exitGame();
//        //设置切换动画，从右边进入，左边退出
//        overridePendingTransition(R.anim.right_in, R.anim.left_out);
//        finish();
//    }
//
//    @OnClick(R.id.pre_room_btn)
//    public void onClickPreRoom() {
//        currentRoomIdIndex = currentRoomIdIndex - 1;
//        int roomId = getRoomIdInList(currentRoomIdIndex);
//        Intent intent = new Intent(this, GameRoomActivity.class);
//        intent.putExtra("room_id", String.valueOf(roomId));
//        intent.putIntegerArrayListExtra("room_id_list", roomIdsList);
//        startActivity(intent);
//        exitGame();
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
//        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
