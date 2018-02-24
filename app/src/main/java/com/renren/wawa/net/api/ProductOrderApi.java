package com.renren.wawa.net.api;


import com.renren.wawa.model.ProductorOrder;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 查询商品订单项
 */
public interface ProductOrderApi {
    @FormUrlEncoded
    @POST("product_order_item.query")
    Observable<ProductorOrder> productorOrder(@Field("action") String action, @Field("with_product") boolean with_product,
                                              @Field("with_product_order") boolean with_product_order);
}
