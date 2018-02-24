package com.renren.wawa.model;

public class ZoneChangeBean extends BaseObject {


    /**
     * data : {"tcp":"123.206.109.114:9090"}
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
         * tcp : 123.206.109.114:9090
         */

        private String tcp;

        public String getTcp() {
            return tcp;
        }

        public void setTcp(String tcp) {
            this.tcp = tcp;
        }
    }
}
