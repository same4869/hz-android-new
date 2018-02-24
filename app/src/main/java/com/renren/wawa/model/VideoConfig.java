package com.renren.wawa.model;

public class VideoConfig extends BaseObject {


    /**
     * qiniu : {"token":"WpDE2kaUkmA87QnKEeEQ-ZFuH3Jjx4Rrp7qOC2d1:pbsk4B3mkqGMCrdHNRBDCS71jZg=:eyJyZXR1cm5Cb2R5Ijoie1wia2V5XCI6ICQoa2V5KX0iLCJzYXZlS2V5Ijoicm9vbS9wMTMxcjEyMnMxNjAyMzA4LTEzMzcwNmZjLm1wNCIsInNjb3BlIjoid2F3YWppIiwiZGVhZGxpbmUiOjE1MDQ1ODkwMjh9","expired_at":1504589028,"base_url":"http://wwj.same.com","url":"http://wwj.same.com/room/p131r122s1602308-133706fc.mp4","key":"room/p131r122s1602308-133706fc.mp4"}
     */

    private QiniuBean qiniu;

    public QiniuBean getQiniu() {
        return qiniu;
    }

    public void setQiniu(QiniuBean qiniu) {
        this.qiniu = qiniu;
    }

    public static class QiniuBean {
        /**
         * token : WpDE2kaUkmA87QnKEeEQ-ZFuH3Jjx4Rrp7qOC2d1:pbsk4B3mkqGMCrdHNRBDCS71jZg=:eyJyZXR1cm5Cb2R5Ijoie1wia2V5XCI6ICQoa2V5KX0iLCJzYXZlS2V5Ijoicm9vbS9wMTMxcjEyMnMxNjAyMzA4LTEzMzcwNmZjLm1wNCIsInNjb3BlIjoid2F3YWppIiwiZGVhZGxpbmUiOjE1MDQ1ODkwMjh9
         * expired_at : 1504589028
         * base_url : http://wwj.same.com
         * url : http://wwj.same.com/room/p131r122s1602308-133706fc.mp4
         * key : room/p131r122s1602308-133706fc.mp4
         */

        private String token;
        private int expired_at;
        private String base_url;
        private String url;
        private String key;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(int expired_at) {
            this.expired_at = expired_at;
        }

        public String getBase_url() {
            return base_url;
        }

        public void setBase_url(String base_url) {
            this.base_url = base_url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
