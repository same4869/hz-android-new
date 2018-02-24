package com.renren.wawa.net.api;


import com.renren.wawa.model.LiveUserResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface LiveUserApi {
    @POST("user.query_by_live_stream_identifiers")
    Observable<LiveUserResult> fetch_live_users(@Body RequestBody body);

}
