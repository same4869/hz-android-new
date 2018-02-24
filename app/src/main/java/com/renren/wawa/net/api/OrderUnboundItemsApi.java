package com.renren.wawa.net.api;

import com.renren.wawa.model.OrderUnboundItemsBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * order/unbound-items   获取未申请发货的商品列表
 */

public interface OrderUnboundItemsApi {

    @POST("wawa/unapplyList")
    Observable<OrderUnboundItemsBean> getOrderUnboundItems(@Body RequestBody body);
}
