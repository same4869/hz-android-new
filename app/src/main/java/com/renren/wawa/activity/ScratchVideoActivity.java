package com.renren.wawa.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.wawaji.vip.R;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.utils.AppUtil;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.CommBeatLoadingView;
import com.youth.banner.WeakHandler;

/**
 * 抓取视频页面
 */

public class ScratchVideoActivity extends BaseTitleBarActivity implements View.OnTouchListener, View.OnClickListener {
    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_SIZE = "video_size";
    public static final String IS_WIFI = "is_wifi";
    /**
     * 更新当前播放的时间
     */
    private static final int UPDATE_CURRENT_POSITION = 1;
    /**
     * 隐藏控制面板
     */
    private static final int HIDE_CTRL_LAYOUT = 2;
    private VideoView playVideoView;
    /**
     * 视频播放时间与总时间
     */
    private TextView tvPlayTime;
    /**
     * 控制视频播放进度的SeekBar
     */
    private SeekBar sbVideo;
    /**
     * 播放按钮
     */
    private ImageButton btnPlay;
    /**
     * 底部控制栏
     */
    private LinearLayout bottomControlLayout;
    /**
     * 当前播放时间
     */
    private int currentPosition = 0;
    /**
     * 播放暂停标志位
     */
    private boolean isPlaying = true;
    private CommBeatLoadingView beatLoadingView;//加载dialog
    private HeadsetPlugReceiver headsetPlugReceiver;//耳机监听
    private boolean isWifiPlay = true;
    private TelephonyManager mTelephonyManager;
    private boolean suspendFlag = false;//播放视频是否挂起
    private int secondaryProgress = 0;// 缓冲进度

    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case UPDATE_CURRENT_POSITION://更新进度条 时间
                    updateCurrentPosition();
                    break;
                case HIDE_CTRL_LAYOUT://显示隐藏控制栏
                    toggleCtrlLayout();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    public int getLayoutId() {
        return R.layout.activity_scratch_video;
    }

    @Override
    public void initData() {
        registerHeadsetPlugReceiver();
        mTelephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        beatLoadingView = (CommBeatLoadingView) findViewById(R.id.scratch_video_loading_view);
        bottomControlLayout = (LinearLayout) findViewById(R.id.scratch_video_ll_bottom_ctrl);
        tvPlayTime = (TextView) findViewById(R.id.scratch_video_tv_play_time);
        sbVideo = (SeekBar) findViewById(R.id.scratch_video_sb_video);
        sbVideo.setEnabled(false);
        btnPlay = (ImageButton) findViewById(R.id.scratch_video_btn_play);
        playVideoView = (VideoView) findViewById(R.id.scratch_video_video_play);
        String videoUrl = getIntent().getStringExtra(VIDEO_URL);
        isWifiPlay = getIntent().getBooleanExtra(IS_WIFI, true);
        BBLog.e("videoUrl " + videoUrl);
        if (StringUtil.isBlank(videoUrl)) {
            ToastUtil.showToast(getString(R.string.game_video_url_error));
            finish();
            return;
        }
        BBLog.e("videoUrl ", "videoUrl " + videoUrl + " isWifiPlay " + isWifiPlay);
        Uri uri = Uri.parse(videoUrl);
        if (uri == null || StringUtil.isBlank(uri.toString())) {
            ToastUtil.showToast(getString(R.string.game_video_url_error));
            finish();
        }
        playVideoView.setVideoPath(uri.toString());
        sbVideo.setOnSeekBarChangeListener(mVideoOnSeekBarChangeListener);
        btnPlay.setOnClickListener(this);
        playVideoView.setOnPreparedListener(onPreparedListener);
        playVideoView.setOnTouchListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            playVideoView.setOnInfoListener(onInfoListener);
        }
        playVideoView.setOnErrorListener(onErrorListener);
        playVideoView.setOnCompletionListener(onCompletionListener);

        beatLoadingView.startLoading();

    }

    @Override
    public void onPause() {
        super.onPause();
        playVideoView.pause();
        handler.removeMessages(UPDATE_CURRENT_POSITION);
    }


    /**
     * 显示或隐藏控制面板
     */
    protected void toggleCtrlLayout() {
        float translationY = ViewHelper.getTranslationY(bottomControlLayout);
        if (translationY == 0) {
            // 如果原来是显示的，则隐藏
            // 顶部控制栏的隐藏：Y方向移动控件的高度的负数
            ViewPropertyAnimator.animate(getTitleBar()).translationY(-getTitleBar().getHeight());
            // 底部控制栏的隐藏：Y方向移动控件的高度
            ViewPropertyAnimator.animate(bottomControlLayout).translationY(bottomControlLayout.getHeight());
        } else {
            // 如果原来是隐藏的，则显示
            ViewPropertyAnimator.animate(getTitleBar()).translationY(0f);
            ViewPropertyAnimator.animate(bottomControlLayout).translationY(0f);
            sendHideCtrlLayoutMessage(6000);
        }
    }

    /**
     * 移除隐藏控制面板的消息
     */
    private void removeHideCtrlLayoutMessage() {
        handler.removeMessages(HIDE_CTRL_LAYOUT);
    }

    /**
     * 发送隐藏控制面板的消息
     *
     * @param time
     */
    private void sendHideCtrlLayoutMessage(int time) {
        removeHideCtrlLayoutMessage();
        handler.sendEmptyMessageDelayed(HIDE_CTRL_LAYOUT, time);
    }

    /**
     * 播放或者暂停
     */
    private void playPause() {
        int resid;
        if (playVideoView.isPlaying()) {
            // 暂停
            playVideoView.pause();
            // 如果正在播放，则显示一个暂停按钮
            resid = R.mipmap.guwen_audio_video_pause;
            isPlaying = false;
            handler.removeMessages(UPDATE_CURRENT_POSITION);
        } else {
            if (suspendFlag) {
                playVideoView.resume();
                suspendFlag = false;
            }
            // 播放
            playVideoView.start();
            // 如果是暂停的，则显示一个播放按钮
            resid = R.mipmap.guwen_audio_video_play;
            isPlaying = true;
            handler.removeMessages(UPDATE_CURRENT_POSITION);
            handler.sendEmptyMessageDelayed(UPDATE_CURRENT_POSITION, 300);
        }
        btnPlay.setImageResource(resid);
    }

    /**
     * 准备播放视频监听
     */
    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {

        public void onPrepared(MediaPlayer mp) {
            if (currentPosition != 0) {
                playVideoView.seekTo(currentPosition);
            }
            if (isPlaying) {
                playVideoView.start(); // 开始播放视频
            } else {
                playVideoView.pause();
            }
            int duration = playVideoView.getDuration();
            sbVideo.setMax(duration);
            updateCurrentPosition();
            sendHideCtrlLayoutMessage(5000);
            BBLog.e("onPreparedListener", "onPreparedListener " + currentPosition);
            beatLoadingView.endLoading(true);
            sbVideo.setEnabled(true);
        }
    };

    /**
     * 视频播放完成的回调监听器
     */
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            btnPlay.setImageResource(R.mipmap.guwen_audio_video_pause);
            handler.removeMessages(UPDATE_CURRENT_POSITION);
            currentPosition = playVideoView.getDuration();
            isPlaying = false;
            if (Build.MODEL.equals("M463C")) {
                String videoUrl = getIntent().getStringExtra(VIDEO_URL);
                Uri uri = Uri.parse(videoUrl);
                playVideoView.setVideoPath(uri.toString());
                currentPosition = 0;
            }
            BBLog.e("onCompletionListener", "-----------onCompletionListener------------------" + currentPosition);
        }
    };

    /**
     * 视频缓冲卡顿监听器
     */
    private MediaPlayer.OnInfoListener onInfoListener = new MediaPlayer.OnInfoListener() {

        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START: // 视频缓冲卡顿的时候会执行
                    BBLog.e("onInfoListener", "视频缓冲卡顿的时候会执行");
                    beatLoadingView.startLoading();
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END: // 视频缓冲到可以播放的时候会执行
                    BBLog.e("onInfoListener", "视频缓冲到可以播放的时候会执行");
                    beatLoadingView.endLoading(true);
                    break;
            }
            return true;
        }
    };

    /**
     * 视频播放失败回调
     */
    private MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            BBLog.e("onErrorListener", "onErrorListener what " + what + " extra " + extra + " currentPosition " + currentPosition);
            return true;
        }

    };


    /**
     * 视频在SeekBar滑动改变监听
     */
    private SeekBar.OnSeekBarChangeListener mVideoOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        public void onStopTrackingTouch(SeekBar seekBar) {
            sendHideCtrlLayoutMessage(5000);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            removeHideCtrlLayoutMessage();
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                BBLog.e("Build.MODEL", "Build.MODEL " + Build.MODEL + " progress " + progress + " secondaryProgress " + secondaryProgress);
                if (Build.MODEL.equals("M463C") && secondaryProgress != 0 && secondaryProgress < progress) {
                    playVideoView.seekTo(secondaryProgress);
                } else {
                    playVideoView.seekTo(progress); // 视频跳转
                }

            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scratch_video_btn_play) {
            playPause();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                BBLog.e("onTouch", "onTouchEvent---ACTION_DOWN");
                removeHideCtrlLayoutMessage();
                return true;

            case MotionEvent.ACTION_UP:
                sendHideCtrlLayoutMessage(0);
                BBLog.e("onTouch", "onTouchEvent---ACTION_UP  ");
                return true;

            default:
                break;
        }
        return true;
    }

    /**
     * 更新当前播放时间和进度条
     */
    private void updateCurrentPosition() {
        int duration = playVideoView.getDuration();
        int currentDuration = playVideoView.getCurrentPosition();
        if (currentDuration != 0) {
            currentPosition = currentDuration;
        }
        String playTime = AppUtil.formatMillis(duration).toString();// 视频总长度
        String currentPlayTime = AppUtil.formatMillis(currentDuration).toString();// 视频当前长度
        tvPlayTime.setText(currentPlayTime + "/" + playTime);
        sbVideo.setProgress(currentDuration);

        //视频缓冲进度条
        float percentFloat = playVideoView.getBufferPercentage() / 100f;
        secondaryProgress = (int) (duration * percentFloat);
        if (secondaryProgress != 0) {
            sbVideo.setSecondaryProgress(secondaryProgress);
        }

        Log.e("updateCurrentPosition", "updateCurrentPosition: " + secondaryProgress);
        handler.sendEmptyMessageDelayed(UPDATE_CURRENT_POSITION, 300);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        BBLog.e("onConfigurationChanged", "onConfigurationChanged " + this.getResources().getConfiguration().orientation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        unregisterHeadsetPlugReceiver();
        mTelephonyManager.listen(mPhoneStateListener, 0);
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 监听是否来电
     */
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String ignored) {
            if (state != TelephonyManager.CALL_STATE_IDLE) {
                if (playVideoView.isPlaying()) {
                    playPause();
                }
            }
        }
    };

    /**
     * 耳机广播监听注册
     */
    private void registerHeadsetPlugReceiver() {
        headsetPlugReceiver = new HeadsetPlugReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(headsetPlugReceiver, filter);
    }

    /**
     * 耳机广播监听取消注册
     */
    private void unregisterHeadsetPlugReceiver() {
        unregisterReceiver(headsetPlugReceiver);
    }


    /**
     * 耳机监听
     */
    private class HeadsetPlugReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.hasExtra("state")) {
                if (intent.getIntExtra("state", 0) == 0) {
                    BBLog.e("HeadsetPlugReceiver ", "headset not connected");
                    if (playVideoView != null && playVideoView.isPlaying()) {
                        playPause();
                    }
                } else if (intent.getIntExtra("state", 0) == 1) {
                    BBLog.e("HeadsetPlugReceiver ", "headset  connected");
                }
            }
        }

    }
}
