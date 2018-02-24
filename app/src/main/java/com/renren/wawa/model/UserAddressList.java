package com.renren.wawa.model;

import java.util.List;

/**
 * 获取用户的发货地址
 */

public class UserAddressList extends BaseObject {


    private List<AddressesBean> addresses;

    public List<AddressesBean> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressesBean> addresses) {
        this.addresses = addresses;
    }

    public static class AddressesBean {
        /**
         * id : 1
         * recipent_address : foo
         * recipent_name : bar
         * recipent_mobile : 13800138000
         */

        private int id;
        private String recipent_address;
        private String recipent_name;
        private String recipent_mobile;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRecipent_address() {
            return recipent_address;
        }

        public void setRecipent_address(String recipent_address) {
            this.recipent_address = recipent_address;
        }

        public String getRecipent_name() {
            return recipent_name;
        }

        public void setRecipent_name(String recipent_name) {
            this.recipent_name = recipent_name;
        }

        public String getRecipent_mobile() {
            return recipent_mobile;
        }

        public void setRecipent_mobile(String recipent_mobile) {
            this.recipent_mobile = recipent_mobile;
        }
    }
}
