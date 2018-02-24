package com.renren.wawa.model;

/**
 * Created by zhangqilu on 2017/9/16.
 */

public class OrderShippingFeeBean extends BaseObject {

    /**
     * data : {"fee":60,"text":"60 个喵喵币"}
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
         * fee : 60
         * text : 60 个喵喵币
         */

        private int coin;

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }
    }
}
