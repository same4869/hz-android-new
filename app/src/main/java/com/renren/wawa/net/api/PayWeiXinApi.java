package com.renren.wawa.net.api;

import com.renren.wawa.model.PayWeiXinBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * /pay/weixin 微信充值
 */
public interface PayWeiXinApi {
    @POST("pay/weixin")
    Observable<PayWeiXinBean> getPayWeiXin(@Body RequestBody body);
}
