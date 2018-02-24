package com.renren.wawa.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.ToastUtil;

import java.io.File;
import java.io.IOException;

/**
 * 录制音视频
 */

public class ScreenRecorderService extends Service {
    private String TAG = ScreenRecorderService.class.getSimpleName();
    private MediaProjection mediaProjection;
    private MediaRecorder mediaRecorder;
    private VirtualDisplay virtualDisplay;

    private boolean running;
    private int width = 720;//720
    private int height = 1080;//1080
    private int dpi;
    private String recordName;


    @Override
    public IBinder onBind(Intent intent) {
        return new RecordBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        running = false;
        mediaRecorder = new MediaRecorder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setMediaProject(MediaProjection project) {
        mediaProjection = project;
    }



    public boolean isRunning() {
        return running;
    }

    public void setConfig(int width, int height, int dpi ,String recordName) {
        this.width = width;
        this.height = height;
        this.dpi = dpi;
        this.recordName = recordName;
    }

    public boolean startRecord() {
        if (mediaProjection == null || running) {
            return false;
        }

        initRecorder();
        try{
            createVirtualDisplay();
        }catch (Exception e){//解决 魅族 小米 权限的问题
            ToastUtil.showToast("请打开录音权限");
            BBLog.e(e.toString());
            return false;
        }

        mediaRecorder.start();
        running = true;
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean stopRecord() {
        if (!running) {
            return false;
        }
        running = false;
        try {
            mediaRecorder.setOnErrorListener(null);
            mediaRecorder.setOnInfoListener(null);
            mediaRecorder.stop();

        } catch (IllegalStateException e) {
            // TODO: handle exception
            BBLog.e("Exception", Log.getStackTraceString(e));
        } catch (RuntimeException e) {
            // TODO: handle exception
            BBLog.e("Exception", Log.getStackTraceString(e));
        } catch (Exception e) {
            // TODO: handle exception
            BBLog.e("Exception", Log.getStackTraceString(e));
        }
        mediaRecorder.reset();
        if (virtualDisplay != null)
            virtualDisplay.release();
        //mediaProjection.stop();

        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createVirtualDisplay() {
        virtualDisplay = mediaProjection.createVirtualDisplay("MainScreen", width, height, dpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), null, null);
    }

    private void initRecorder() {
        // mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置音频源
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);//设置视频源
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//设置输出文件格式
        mediaRecorder.setOutputFile(getsaveDirectory() + recordName + ".mp4");//设置输出文件
        mediaRecorder.setVideoSize(width, height);//设置视频的宽度和高度（分辨率）
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);//设置视频编码
        //mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//设置音频编码
        mediaRecorder.setVideoEncodingBitRate(8 * 512 * 1024);//设置编码比特率  5
        //设置帧率，通常这个值越高，视频会显得越流畅，一般默认我设置成30，你最低可以设置成24，不要低于这个值，低于24会明显卡顿
        mediaRecorder.setVideoFrameRate(30);//设置视频帧的频率
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                BBLog.e(TAG, "onInfo: what:" + what + " extra :" + extra);
            }
        });

        mediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                BBLog.e(TAG, "onError: what:" + what + " extra :" + extra);
            }
        });
    }

    public static String getsaveDirectory() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "ScreenRecord" + "/";

            File file = new File(rootDir);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }

            return rootDir;
        } else {
            return null;
        }
    }

    public class RecordBinder extends Binder {
        public ScreenRecorderService getRecordService() {
            return ScreenRecorderService.this;
        }
    }
}
