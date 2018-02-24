package com.renren.wawa.model;

public class GameStartBean extends BaseObject {

    /**
     * data : {"tcp":"115.159.39.145:9091"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tcp : 115.159.39.145:9091
         */

        private String socketUrl;

        public String getSocketUrl() {
            return socketUrl;
        }

        public void setSocketUrl(String socketUrl) {
            this.socketUrl = socketUrl;
        }
    }
}
