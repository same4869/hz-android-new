package com.renren.wawa.net.api;


import com.renren.wawa.model.CheckOutRefund;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 创建一个兑换喵喵币订单
 */

public interface CheckOutRefundApi {

    @POST("product_order.checkout_refund")
    Observable<CheckOutRefund> checkOutRefund(@Body RequestBody body);
}
