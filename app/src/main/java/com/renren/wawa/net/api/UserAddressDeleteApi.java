package com.renren.wawa.net.api;

import com.renren.wawa.model.BaseObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * user/address-delete 删除快递地址
 */
public interface UserAddressDeleteApi {
    @FormUrlEncoded
    @POST("address/delete")
    Observable<BaseObject> getUserAddressDelete(@Field("id") int addressId);
}
