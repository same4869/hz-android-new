package com.renren.wawa.net.api;


import com.renren.wawa.model.Servers;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 获取服务器列表
 */

public interface ServersApi {
    @GET("app.servers")
    Observable<Servers> servers();
}
