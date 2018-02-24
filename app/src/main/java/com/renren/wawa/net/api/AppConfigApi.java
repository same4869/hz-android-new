package com.renren.wawa.net.api;


import com.renren.wawa.model.AppConfig;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 获取应用配置
 */

public interface AppConfigApi {
    @GET("app.config")
    Observable<AppConfig> appConfig();
}
