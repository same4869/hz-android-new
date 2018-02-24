package com.renren.wawa.net.api;


import com.renren.wawa.model.UserInfoMeProfileBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 根据 id 获取用户 profile
 */

public interface UserGetByIdApi {
    @GET("user.get_by_id")
    Observable<UserInfoMeProfileBean>  userGetById(@Query("user_id") long userId);
}
