package com.renren.wawa.model;

import java.util.List;

public class UserWalletFlowBean extends BaseObject {

    /**
     * data : {"lists":[{"id":476810,"category":"退款","remark":"房间 你的好朋友Ted 游戏退费:  29","amount":29,"created_at":"2017-08-18 21:41:42","state":"支付成功"}],"page":{"next_id":374740,"limit":10}}
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
         * lists : [{"id":476810,"category":"退款","remark":"房间 你的好朋友Ted 游戏退费:  29","amount":29,"created_at":"2017-08-18 21:41:42","state":"支付成功"}]
         * page : {"next_id":374740,"limit":10}
         */

        private List<ListsBean> logList;

        public List<ListsBean> getLogList() {
            return logList;
        }

        public void setLogList(List<ListsBean> logList) {
            this.logList = logList;
        }

        public static class ListsBean {
            /**
             * id : 476810
             * category : 退款
             * remark : 房间 你的好朋友Ted 游戏退费:  29
             * amount : 29
             * created_at : 2017-08-18 21:41:42
             * state : 支付成功
             */

            private String type;
            private String remark;
            private int amount;
            private String created_at;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

        }
    }
}
