package com.renren.wawa.net.api;

import com.renren.wawa.model.UserGameIssueBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * /user/game-issue 用户上报游戏异常
 */

public interface UserGameIssueApi {
    @POST("user/game-issue")
    Observable<UserGameIssueBean> getUserGameIssue(@Body RequestBody body);
}
