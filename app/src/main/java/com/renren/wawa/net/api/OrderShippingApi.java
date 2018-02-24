package com.renren.wawa.net.api;

import com.renren.wawa.model.BaseObject;
import com.renren.wawa.model.OrderShippingBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * order/shipping  创建发货订单
 */

public interface OrderShippingApi {
    @POST("order/add")
    Observable<BaseObject> getOrderShipping(@Body RequestBody body);

}
