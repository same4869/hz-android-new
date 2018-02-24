package com.renren.wawa.net.api;

import com.renren.wawa.model.UserWalletFlowBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * /user/wallet-flow获取当前用户钱包流水
 */

public interface UserWalletFlowApi {
    @POST("account/log")
    Observable<UserWalletFlowBean> getUserWalletFlow(@Body RequestBody body);
}
