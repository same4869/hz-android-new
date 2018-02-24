package com.renren.wawa.model;

public class User extends BaseObject {
    private long id;
    private String nickname;
    private String avatar;
    private String live_stream_user_id;
    private String gender;
    private int is_staff;
    private boolean is_assessor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getLive_stream_user_id() {
        return live_stream_user_id;
    }

    public void setLive_stream_user_id(String live_stream_user_id) {
        this.live_stream_user_id = live_stream_user_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getIs_staff() {
        return is_staff;
    }

    public void setIs_staff(int is_staff) {
        this.is_staff = is_staff;
    }

    public boolean isIs_assessor() {
        return is_assessor;
    }

    public void setIs_assessor(boolean is_assessor) {
        this.is_assessor = is_assessor;
    }
}
