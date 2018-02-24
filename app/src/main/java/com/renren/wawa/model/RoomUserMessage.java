package com.renren.wawa.model;

public class RoomUserMessage extends BaseObject {
    private long roomId;
    private long uid;
    private String nickname;
    private String avatar;

    public RoomUserMessage(long roomId, long uid, String nickname, String avatar) {
        this.roomId = roomId;
        this.uid = uid;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
