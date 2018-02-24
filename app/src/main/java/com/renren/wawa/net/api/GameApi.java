package com.renren.wawa.net.api;

import com.renren.wawa.model.GameResult;
import com.renren.wawa.model.RoomListResult;
import com.renren.wawa.model.RoomResult;
import com.renren.wawa.model.SessionResult;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


public interface GameApi {

    @GET("game_room.list")
    Observable<RoomListResult> rooms();

    @GET("game_room.info")
    Observable<RoomResult> room(@Query("room_id") String room_id);

    @GET("game_room.current_session")
    Observable<SessionResult> session(@Query("room_id") String room_id);

    @POST("game_room.play")
    Observable<GameResult> play(@Query("room_id") String room_id);
}
