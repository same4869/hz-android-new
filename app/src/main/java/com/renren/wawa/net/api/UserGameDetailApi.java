package com.renren.wawa.net.api;

import com.renren.wawa.model.UserGameDetailBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 根据 id 获取一个游戏记录详情
 */

public interface UserGameDetailApi {
    @FormUrlEncoded
    @POST("game")
    Observable<UserGameDetailBean> getUserGameDetail(@Field("id") String id);
}
