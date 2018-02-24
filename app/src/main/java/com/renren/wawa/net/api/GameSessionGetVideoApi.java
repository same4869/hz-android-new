package com.renren.wawa.net.api;


import com.renren.wawa.model.BaseObject;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 加载云端视频接口
 */

public interface GameSessionGetVideoApi {
    @GET("game_session.getVideo")
    Observable<BaseObject> gameSessionGetVideo(@Query("session_id") int sessionId);
}
