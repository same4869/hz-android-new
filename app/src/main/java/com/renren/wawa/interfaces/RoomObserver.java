package com.renren.wawa.interfaces;

import com.renren.wawa.model.RoomStatusUpdate;
import com.renren.wawa.model.RoomUserMessage;

/**
 * 房间状态回调接口
 */

public interface RoomObserver {
    void onRoomStateUpdated(RoomStatusUpdate roomStatusUpdate);

    void onRoomMemberUpdated(boolean isJoin, RoomUserMessage roomUserMessage);

    void onBroadcastReceived(RoomUserMessage roomUserMessage);
}
