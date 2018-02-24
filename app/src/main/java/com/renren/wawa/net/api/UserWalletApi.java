package com.renren.wawa.net.api;

import com.renren.wawa.model.UserWalletBean;

import retrofit2.http.POST;
import rx.Observable;

/**
 * user/wallet 获取当前用户钱包信息
 */

public interface UserWalletApi {
    @POST("user/wallet")
    Observable<UserWalletBean> getUserWallet();
}
