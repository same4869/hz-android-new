package com.renren.wawa.manager;


import android.text.TextUtils;

import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.db.UploadInfoDBHelper;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.FileUtil;
import com.renren.wawa.utils.ToastUtil;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;

import org.json.JSONObject;

import java.io.File;


public class QiNiuUploadManager {

    private static UploadManager uploadManager;


    public QiNiuUploadManager() {
        if (uploadManager == null) {
            init();
        }
    }

    private void init() {
        String dirPath = FileUtil.getSaveQiNiuDirectory();
        if (TextUtils.isEmpty(dirPath)) {
            ToastUtil.showToast("没有存储卡");
        }
        Recorder recorder = null;
        try {
            recorder = new FileRecorder(dirPath);
        } catch (Exception e) {
            BBLog.e("Exception " + e.toString());
        }
        //默认使用key的url_safe_base64编码字符串作为断点记录文件的文件名
        //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
        KeyGenerator keyGen = new KeyGenerator() {
            public String gen(String key, File file) {
                // 不必使用url_safe_base64转换，uploadManager内部会处理
                // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                return key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
            }
        };

        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
                .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();

        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);

    }

    /**
     * 上传视频
     * @param gameId
     * @param key
     * @param token
     */
    public void upLoadVideo(final int gameId, String key, String token) {
//        data = <File对象、或 文件路径、或 字节数组>
//        String key = <指定七牛服务上的文件名，或 null>;
//        String token = <从服务端SDK获取>;
        final String data = FileUtil.getSaveVideoDirectory() + gameId+".mp4";
        BBLog.e("data " + data);
        if(uploadManager==null){
            init();
        }
        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            File file = new File(data);
                            if(file.exists()){
                                file.delete();
                            }
                            UploadInfoDBHelper.getInstance(WawaApplication.getInstance()).delete(String.valueOf(gameId));
                            BBLog.e(Constants.TAG, "Upload Success "+ UploadInfoDBHelper.getInstance(WawaApplication.getInstance()).
                                    getAllData(UserManager.getCurUserId()).toString());
                        } else {
                            BBLog.e(Constants.TAG, "Upload Fail "+info.toString());
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                    }
                }, new UploadOptions(null, null, false,
                        new UpProgressHandler(){
                            public void progress(String key, double percent){
                                BBLog.e(Constants.TAG,  ": " + percent);
                            }
                        }, null)
        );
    }




}
