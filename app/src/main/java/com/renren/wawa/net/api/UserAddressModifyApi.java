package com.renren.wawa.net.api;

import com.renren.wawa.model.BaseObject;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * user/address-modify  修改快递地址
 */

public interface UserAddressModifyApi {
    @POST("address/edit")
    Observable<BaseObject> getUserAddressModify(@Body RequestBody body);
}
