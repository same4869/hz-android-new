package com.renren.wawa.net.api;

import com.renren.wawa.model.RoomListBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 获取房间列表"
 */

public interface RoomListApi {
    @POST("room/search")
    Observable<RoomListBean> getRoomList(@Body RequestBody body);
}
