package com.renren.wawa.model;

public class LiveStream extends BaseObject {

    private int room_id;

    private String master_user_id;

    private String slave01_user_id;

    private LiveURL live_url;

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getMaster_user_id() {
        return master_user_id;
    }

    public void setMaster_user_id(String master_user_id) {
        this.master_user_id = master_user_id;
    }

    public String getSlave01_user_id() {
        return slave01_user_id;
    }

    public void setSlave01_user_id(String slave01_user_id) {
        this.slave01_user_id = slave01_user_id;
    }

    public LiveURL getLive_url() {
        return live_url;
    }

    public void setLive_url(LiveURL live_url) {
        this.live_url = live_url;
    }

    public static class LiveURL {
        private String master;
        private String slave01;

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public String getSlave01() {
            return slave01;
        }

        public void setSlave01(String slave01) {
            this.slave01 = slave01;
        }
    }
}
