package com.renren.wawa.net.api;


import com.renren.wawa.model.UserInfoMeProfileBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 获取当前用户的 profile
 */

public interface UserInfoMeProfileApi {
    @GET("user.me")
     Observable<UserInfoMeProfileBean> userInfoMeProfile();
}
