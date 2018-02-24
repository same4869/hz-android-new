package com.renren.wawa.net.api;

import com.renren.wawa.model.GameStatusBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 获取当前游戏状态
 */

public interface GameStatusApi {
    @FormUrlEncoded
    @POST("room/user")
    Observable<GameStatusBean> getGameStatus(@Field("room_id") String room_id);
}
