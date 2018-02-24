package com.renren.wawa.net.api;


import com.renren.wawa.model.VideoConfig;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface VideoUploadConfigApi {
    @FormUrlEncoded
    @POST("current_game.get_video_upload_config")
    Observable<VideoConfig> getVideoUploadConfig(@Field("session_id") int session_id);
}
