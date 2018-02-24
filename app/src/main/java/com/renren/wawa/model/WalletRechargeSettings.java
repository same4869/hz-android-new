package com.renren.wawa.model;

import java.util.List;

public class WalletRechargeSettings extends BaseObject {

    private List<WeixinBean> weixin;

    public List<WeixinBean> getWeixin() {
        return weixin;
    }

    public void setWeixin(List<WeixinBean> weixin) {
        this.weixin = weixin;
    }

    public static class WeixinBean {
        /**
         * amount : 1000
         * wawa_amount : 100
         * icon : coin-stack1
         * desc : 充20元返30个币
         * plus_amount : 30
         */

        private int amount;
        private int wawa_amount;
        private String icon;
        private String desc;
        private int plus_amount;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getWawa_amount() {
            return wawa_amount;
        }

        public void setWawa_amount(int wawa_amount) {
            this.wawa_amount = wawa_amount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getPlus_amount() {
            return plus_amount;
        }

        public void setPlus_amount(int plus_amount) {
            this.plus_amount = plus_amount;
        }
    }
}
