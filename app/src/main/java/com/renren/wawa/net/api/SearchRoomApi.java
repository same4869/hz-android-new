package com.renren.wawa.net.api;


import com.renren.wawa.model.Room;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 搜索房间
 */

public interface SearchRoomApi {
    @GET("admin.get_room_by_token")
    Observable<Room> getSearchRoom(@Query("token") String token);
}
