package com.renren.wawa.net.api;

import com.renren.wawa.model.BaseObject;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface FeedbackApi {
    @POST("user/feedback")
    Observable<BaseObject> startFeedback(@Body RequestBody body);
}
