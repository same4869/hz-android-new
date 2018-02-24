package com.renren.wawa.net.api;


import com.renren.wawa.model.WalletRechargeSettings;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 获取充值设定
 */

public interface WalletRechargeSettingsApi {
    @GET("wallet.recharge_settings")
    Observable<WalletRechargeSettings> walletRechargeSettings();
}
