package com.renren.wawa.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GameConfig extends BaseObject {

    private String tcp;

    private String ws;

    private String token;

    @SerializedName("live_stream")
    private LiveConfig liveConfig;

    public String getTcp() {
        return tcp;
    }

    public void setTcp(String tcp) {
        this.tcp = tcp;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LiveConfig getLiveConfig() {
        return liveConfig;
    }

    public void setLiveConfig(LiveConfig liveConfig) {
        this.liveConfig = liveConfig;
    }

    private static class LiveConfig implements Serializable {
        private String appid;

        private String identifier;

        private String signature;

        private long expire_until;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public long getExpire_until() {
            return expire_until;
        }

        public void setExpire_until(long expire_until) {
            this.expire_until = expire_until;
        }
    }
}
