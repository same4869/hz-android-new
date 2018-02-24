package com.renren.wawa.net.api;

import com.renren.wawa.model.PaySettingBean;

import retrofit2.http.POST;
import rx.Observable;

/**
 * /pay/setting  充值设置
 */

public interface PaySettingApi {

    @POST("pay/setting")
    Observable<PaySettingBean> getPaySetting();
}
