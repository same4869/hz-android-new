package com.renren.wawa.net.api;

import com.renren.wawa.model.UserInfo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * /user/info  获取用户信息
 */

public interface UserInfoApi {
    @POST("user")
    Observable<UserInfo> getUserInfo(@Body RequestBody body);
}
