package com.renren.wawa.net.api;


import com.renren.wawa.model.ConfigResult;
import com.renren.wawa.model.SigninResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


public interface AccountApi {

    @POST("user.signin_with_email")
    Observable<SigninResult> signin_with_email(@Body RequestBody body);


    /**
     * 用户获取游戏平台 socket 连接。
     * @return
     */
    @POST("me.start")
    Observable<ConfigResult> update_config();

}
