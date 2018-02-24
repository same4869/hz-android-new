package com.renren.wawa.net.api;

import com.renren.wawa.model.OrderShippingFeeBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * order/shipping-fee 获取订单信息
 */

public interface OrderShippingFeeApi {
    @POST("shipping/coin")
    Observable<OrderShippingFeeBean> orderShippingFee(@Body RequestBody body);
}
