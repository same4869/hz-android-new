package com.renren.wawa.model;

/**
 * /user/info 获取用户信息
 */

public class UserInfo extends BaseObject {

    /**
     * data : {"id":1,"nickname":"daydaygo","avatar":"","success_count":30}
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
         * id : 1
         * nickname : daydaygo
         * avatar :
         * success_count : 30
         */

        private int id;
        private String nickname;
        private String avatar;
        private int succNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getSuccNum() {
            return succNum;
        }

        public void setSuccNum(int succNum) {
            this.succNum = succNum;
        }
    }
}
