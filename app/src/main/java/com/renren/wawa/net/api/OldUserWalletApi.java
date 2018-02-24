package com.renren.wawa.net.api;


import com.renren.wawa.model.UserWallet;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by same on 2017/8/17.\
 * 获取玩家当前钱包数据
 */

public interface OldUserWalletApi {

    @GET("me.wallet")
    Observable<UserWallet> getUserWallet();
}
