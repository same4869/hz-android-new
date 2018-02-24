package com.renren.wawa.net.api;

import com.renren.wawa.model.GameStartBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 开始游戏
 */

public interface GameStartApi {
    @FormUrlEncoded
    @POST("game/begin")
    Observable<GameStartBean> getGameStart(@Field("room_id") String room_id);
}
