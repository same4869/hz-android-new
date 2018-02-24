package com.renren.wawa.net.api;


import com.renren.wawa.model.ReportIssue;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 玩家上报游戏异常
 */

public interface ReportIssueApi {
    @FormUrlEncoded
    @POST("current_game.report_issue")
    Observable<ReportIssue> reportIssue(@Field("reason") String reason, @Field("comment") String comment, @Field("session_id") int session_id);

}
