package com.renren.wawa.model;

public class RoomStatusUpdate extends BaseObject {
    private long roomId ;
    private int status ;


    public RoomStatusUpdate(long roomId, int status) {
        this.roomId = roomId;
        this.status = status;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
