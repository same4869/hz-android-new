package com.renren.wawa.net.api;


import com.renren.wawa.model.CheckoutShipping;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 创建一个发货订单
 */

public interface CheckoutShippingApi {
    @POST("product_order.checkout_shipping")
    Observable<CheckoutShipping> checkoutShipping(@Body RequestBody body);

}
