package com.renren.wawa.net.api;


import com.renren.wawa.model.ShippingFee;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 计算商品订单项的邮费
 */

public interface ShippingFeeApi {
    @POST("product_order.get_shipping_fee")
    Observable<ShippingFee> shippingFee(@Body RequestBody body);
}
