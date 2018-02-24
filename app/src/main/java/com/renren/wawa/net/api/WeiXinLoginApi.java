package com.renren.wawa.net.api;

import com.renren.wawa.model.EmailLoginBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 使用微信登录
 */

public interface WeiXinLoginApi {
    @POST("login")
    Observable<EmailLoginBean> getWeiXinLogin(@Body RequestBody body);
}
