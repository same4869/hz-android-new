package com.renren.wawa.net.api;


import com.renren.wawa.model.ScratchRecord;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 查询当前用户游戏记录
 */

public interface ScratchRecordApi {
    @POST("me.query_game_session")
     Observable<ScratchRecord> scratchRecord(@Body RequestBody body);
}
