package com.renren.wawa.model;

public class ReportIssue extends BaseObject {

    /**
     * player_log_upload : {"token":"xxx","base_url":"yyy"}
     */

    private PlayerLogUploadBean player_log_upload;

    public PlayerLogUploadBean getPlayer_log_upload() {
        return player_log_upload;
    }

    public void setPlayer_log_upload(PlayerLogUploadBean player_log_upload) {
        this.player_log_upload = player_log_upload;
    }

    public static class PlayerLogUploadBean {
        /**
         * token : xxx
         * base_url : yyy
         */

        private String token;
        private String base_url;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getBase_url() {
            return base_url;
        }

        public void setBase_url(String base_url) {
            this.base_url = base_url;
        }
    }
}
