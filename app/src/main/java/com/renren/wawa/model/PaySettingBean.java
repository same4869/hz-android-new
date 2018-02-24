package com.renren.wawa.model;

import java.util.List;

public class PaySettingBean extends BaseObject {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * amount : 1000
         * wawa_amount : 100
         * icon : coin-stack1
         * desc : 充20元返30个币
         * plus_amount : 30
         */

        private int price;
        private int coin;
        private String text;
        private int original_coin;
        private int plus_coin;

        public int getOriginal_coin() {
            return original_coin;
        }

        public void setOriginal_coin(int original_coin) {
            this.original_coin = original_coin;
        }

        public int getPlus_coin() {
            return plus_coin;
        }

        public void setPlus_coin(int plus_coin) {
            this.plus_coin = plus_coin;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
