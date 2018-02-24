package com.renren.wawa.net.api;

import com.renren.wawa.model.UserGameBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * /user/game 获取用户游戏信息
 */

public interface UserGameApi {
    @POST("game/list")
    Observable<UserGameBean> getUserGame(@Body RequestBody body);
}
