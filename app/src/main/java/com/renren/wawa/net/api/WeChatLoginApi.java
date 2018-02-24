package com.renren.wawa.net.api;


import com.renren.wawa.model.WeChatLoginResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface WeChatLoginApi {
    /**
     * 微信登录
     */
    @POST("user.weixin_code")
    Observable<WeChatLoginResult> weChatLogin(@Body RequestBody body);
}
