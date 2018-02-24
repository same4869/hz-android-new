package com.renren.wawa.model;

import java.util.List;

public class ListInviteBean extends BaseObject {

    /**
     * data : {"list":[{"friend_user_id":6,"reward_coin_num":60,"created_at":"2017-10-10 00:00:00","avatar":"http://owsm9593l.bkt.clouddn.com/fd79d354","nickname":"xxxx"}],"page":{"next_id":"2017-10-10 00:00:00","limit":"2"}}
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
         * list : [{"friend_user_id":6,"reward_coin_num":60,"created_at":"2017-10-10 00:00:00","avatar":"http://owsm9593l.bkt.clouddn.com/fd79d354","nickname":"xxxx"}]
         * page : {"next_id":"2017-10-10 00:00:00","limit":"2"}
         */

        private PageBean page;
        private List<ListBean> list;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageBean {
            /**
             * next_id : 2017-10-10 00:00:00
             * limit : 2
             */

            private String next_id;
            private String limit;

            public String getNext_id() {
                return next_id;
            }

            public void setNext_id(String next_id) {
                this.next_id = next_id;
            }

            public String getLimit() {
                return limit;
            }

            public void setLimit(String limit) {
                this.limit = limit;
            }
        }

        public static class ListBean {
            /**
             * friend_user_id : 6
             * reward_coin_num : 60
             * created_at : 2017-10-10 00:00:00
             * avatar : http://owsm9593l.bkt.clouddn.com/fd79d354
             * nickname : xxxx
             */

            private int friend_user_id;
            private int reward_coin_num;
            private String created_at;
            private String avatar;
            private String nickname;

            public int getFriend_user_id() {
                return friend_user_id;
            }

            public void setFriend_user_id(int friend_user_id) {
                this.friend_user_id = friend_user_id;
            }

            public int getReward_coin_num() {
                return reward_coin_num;
            }

            public void setReward_coin_num(int reward_coin_num) {
                this.reward_coin_num = reward_coin_num;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}
