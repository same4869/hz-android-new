package com.renren.wawa.model;

public class UserAddressCreate extends BaseObject {


    /**
     * data : {"recipent_address":"北京市北京市东城区巴巴爸爸","recipent_mobile":"13764354191","recipent_name":"我们","id":"3918"}
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
         * recipent_address : 北京市北京市东城区巴巴爸爸
         * recipent_mobile : 13764354191
         * recipent_name : 我们
         * id : 3918
         */

        private String recipent_address;
        private String recipent_mobile;
        private String recipent_name;
        private String id;

        public String getRecipent_address() {
            return recipent_address;
        }

        public void setRecipent_address(String recipent_address) {
            this.recipent_address = recipent_address;
        }

        public String getRecipent_mobile() {
            return recipent_mobile;
        }

        public void setRecipent_mobile(String recipent_mobile) {
            this.recipent_mobile = recipent_mobile;
        }

        public String getRecipent_name() {
            return recipent_name;
        }

        public void setRecipent_name(String recipent_name) {
            this.recipent_name = recipent_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
