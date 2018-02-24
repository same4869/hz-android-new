package com.renren.wawa.net.api;

import com.renren.wawa.model.UserAddressBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 获取用户的发货地址
 */

public interface UserAddressApi {
    @POST("address/list")
    Observable<UserAddressBean> getUserAddress(@Body RequestBody body);
}
