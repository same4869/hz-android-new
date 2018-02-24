package com.renren.wawa.net.api;


import com.renren.wawa.model.UserAddressList;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 获取用户的发货地址
 */

public interface UserAddressListApi {
    @GET("user_address.list")
    Observable<UserAddressList> userAddressList();
}
