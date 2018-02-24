package com.renren.wawa.net.api;

import com.renren.wawa.model.AppUploadBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * /app/upload  获取七牛上传 token
 */

public interface AppUploadApi {
    @POST("app/upload")
    Observable<AppUploadBean> getAppUpload(@Body RequestBody body);

}
