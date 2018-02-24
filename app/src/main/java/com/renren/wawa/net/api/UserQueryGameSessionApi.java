package com.renren.wawa.net.api;


import com.renren.wawa.model.ScratchRecord;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 查询指定用户的游戏记录
 */

public interface UserQueryGameSessionApi {
    @POST("user.query_game_session")
    Observable<ScratchRecord> userQueryGameSession(@Body RequestBody body);
}
