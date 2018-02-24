package com.renren.wawa.model;

import java.util.List;

/**
 */

public class UserNotificationBean extends BaseObject {

    /**
     * data : {"lists":[{"id":49,"text":"far","url":"wawaji://claw.same.com/session/212596","created_at":"2017-08-21 16:23:45"},{"id":50,"text":"far","url":"wawaji://claw.same.com/session/212596","created_at":"2017-08-21 16:06:34"}],"page":{"next_id":26,"limit":10}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseObject{
        /**
         * lists : [{"id":49,"text":"far","url":"wawaji://claw.same.com/session/212596","created_at":"2017-08-21 16:23:45"},{"id":50,"text":"far","url":"wawaji://claw.same.com/session/212596","created_at":"2017-08-21 16:06:34"}]
         * page : {"next_id":26,"limit":10}
         */

        private PageBean page;
        private List<ListsBean> lists;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class PageBean extends BaseObject{
            /**
             * next_id : 26
             * limit : 10
             */

            private int next_id;
            private int limit;

            public int getNext_id() {
                return next_id;
            }

            public void setNext_id(int next_id) {
                this.next_id = next_id;
            }

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }
        }

        public static class ListsBean extends BaseObject{
            /**
             * id : 49
             * text : far
             * url : wawaji://claw.same.com/session/212596
             * created_at : 2017-08-21 16:23:45
             */

            private int id;
            private String text;
            private String url;
            private String created_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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
