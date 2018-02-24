package com.renren.wawa.model;

import com.google.gson.annotations.SerializedName;

public class Room extends BaseObject{

    public static final String MACHINE_STATE_NORMAL = "normal";

    public String id;

    public String name;

    public String state;

    public String state_text;

    public String machine_state;

    public String cover;

    public int price;

    public int crawled_times;

    public String server;

    private String machine_token;

    @SerializedName("live_stream_settings")
    public LiveStream liveStream;

    public void refreshState(Room room) {
        state = room.state;
        state_text = room.state_text;
        machine_state = room.machine_state;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getMachine_token() {
        return machine_token;
    }

    public void setMachine_token(String machine_token) {
        this.machine_token = machine_token;
    }
}
