package com.renren.wawa.net.api;

import com.renren.wawa.model.OrderRefundBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * /order/refund 将抓到的娃娃兑换成喵喵币
 */

public interface OrderRefundApi {

    @POST("order/changeCoin")
    Observable<OrderRefundBean> getOrderRefund(@Body RequestBody body);
}
