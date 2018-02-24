package com.renren.wawa.net.api;

import com.renren.wawa.model.RoomInfoBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 获取房间信息
 */

public interface RoomInfoApi {
    @FormUrlEncoded
    @POST("room/detail")
    Observable<RoomInfoBean> getRoomInfo(@Field("room_id") String room_id);
}
