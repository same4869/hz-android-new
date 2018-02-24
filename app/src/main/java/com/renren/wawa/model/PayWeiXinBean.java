package com.renren.wawa.model;

import com.google.gson.annotations.SerializedName;

public class PayWeiXinBean extends BaseObject {


    /**
     * data : {"appid":"wx7cda27b125b9aab7","partnerid":"1473559702","prepayid":"wx2017092114503158fb26d13d0939144197","package":"Sign=WXPay","noncestr":"8bGM1vLgVwLY5qFB","timestamp":1505976631,"sign":"A87807E7D7E0CAA21A0C21A8D8D4B75A"}
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
         * appid : wx7cda27b125b9aab7
         * partnerid : 1473559702
         * prepayid : wx2017092114503158fb26d13d0939144197
         * package : Sign=WXPay
         * noncestr : 8bGM1vLgVwLY5qFB
         * timestamp : 1505976631
         * sign : A87807E7D7E0CAA21A0C21A8D8D4B75A
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private int timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
