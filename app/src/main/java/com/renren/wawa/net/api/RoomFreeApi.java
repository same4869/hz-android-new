package com.renren.wawa.net.api;
import com.renren.wawa.model.RoomFreeBean;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 获取空闲房间
 */

public interface RoomFreeApi {
    @POST("room/free")
    Observable<RoomFreeBean> getRoomFree() ;
}

