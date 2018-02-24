package com.renren.wawa.net.api;

import com.renren.wawa.model.ZoneChangeBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * zone/change  换区
 */
public interface ZoneChangeApi {
    @POST("zone/change")
    Observable<ZoneChangeBean> getZoneChange(@Body RequestBody body);
}