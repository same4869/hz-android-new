package com.renren.wawa.net.api;

import com.renren.wawa.model.UserAddressCreate;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * /user/address-create 新建快递地址
 */

public interface UserAddressCreateApi {
    @POST("address/add")
    Observable<UserAddressCreate> userAddressCreate(@Body RequestBody body);
}
