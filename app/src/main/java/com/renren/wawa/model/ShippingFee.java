package com.renren.wawa.model;

public class ShippingFee extends BaseObject {

    /**
     * shipping_fee : {"text":"运费 60 个喵喵币","fee":60}
     */

    private ShippingFeeBean shipping_fee;

    public ShippingFeeBean getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(ShippingFeeBean shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public static class ShippingFeeBean {
        /**
         * text : 运费 60 个喵喵币
         * fee : 60
         */

        private String text;
        private int fee;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }
    }
}
