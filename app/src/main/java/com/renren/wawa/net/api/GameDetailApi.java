package com.renren.wawa.net.api;


import com.renren.wawa.model.GameDetail;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 根据 id 获取一个游戏记录详情
 */

public interface GameDetailApi {
    @GET("game_session.get_by_id")
    Observable<GameDetail> GameDetail(@Query("session_id") int session_id);
}
