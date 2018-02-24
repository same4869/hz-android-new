package com.renren.wawa.net.api;


import com.renren.wawa.model.EmailLoginBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 邮箱登录
 */

public interface EmailLoginApi {
    @POST("login")
    Observable<EmailLoginBean> getEmailLogin(@Body RequestBody body);
}
