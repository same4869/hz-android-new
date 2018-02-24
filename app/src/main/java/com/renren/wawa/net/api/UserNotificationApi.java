package com.renren.wawa.net.api;


import com.renren.wawa.model.UserNotificationBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * /user/notification  系统通知
 */

public interface UserNotificationApi {
    @POST("user/notification")
    Observable<UserNotificationBean> userNotification(@Body RequestBody body);
}
