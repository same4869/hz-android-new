package com.renren.wawa.net.api;


import com.renren.wawa.model.AppKeysBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 *获取 app 配置
 */

public interface AppKeysApi {
    @POST("config/items")
    Observable<AppKeysBean> getAppKeys(@Body RequestBody body);
}
