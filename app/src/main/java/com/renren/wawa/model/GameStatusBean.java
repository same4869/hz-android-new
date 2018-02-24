package com.renren.wawa.model;

import java.util.List;

public class GameStatusBean extends BaseObject {


    /**
     * data : {"state":1,"user_count":"20","lists":[{"id":1,"avatar":""},{"id":2,"avatar":""},{"id":3,"avatar":""}]}
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
         * state : 1
         * user_count : 20
         * lists : [{"id":1,"avatar":""},{"id":2,"avatar":""},{"id":3,"avatar":""}]
         */

        private int status;
        private String totalNum;
        private List<MemberUserBean> userList;

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public List<MemberUserBean> getUserList() {
            return userList;
        }

        public void setUserList(List<MemberUserBean> userList) {
            this.userList = userList;
        }


        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
