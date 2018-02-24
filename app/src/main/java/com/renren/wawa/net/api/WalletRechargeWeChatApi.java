package com.renren.wawa.net.api;

import com.renren.wawa.model.WalletRechargeWeChat;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 用户发起微信充值
 */
public interface WalletRechargeWeChatApi {
    @POST("wallet.recharge_weixin")
    Observable<WalletRechargeWeChat> walletRechargeWechat(@Body RequestBody body);
}
