package com.renren.wawa.model;

import java.util.List;

/**
 * 获取应用配置
 */

public class AppConfig extends BaseObject {


    /**
     * config : {"login_setting":{"allow_login_types":["email","weixin"]},"user_invite_action_body":{"content_type":"text/plain","title":"分享邀请码","text":"邀请好友加入可以免费获得喵喵币"},"user_invite_action_dest_url":"http://t.same.com/s/-1j5o","app_store_assessors":[1,7],"action_api_room_play_jump_invite_code_body":{"content_type":"text/plain","title":"喵喵币不够了","text":"邀请好友加入可以免费获得喵喵币"},"wx92f26a7f4a53ee54_wx_access_token":{"access_token":"Ll2JhUU3aj5G1IjkCxYzKPGvgGjA_SBhKA88FNA_HwDXt6odqjMwtLULoiOUmm_Xrj-CCwfvkeY2iLxsBQGKIA8J9vLfRbcnnykr_zPoeZWD8BmQslrISIL08ypsxmrqGONfABAUCB","expires_in":7200,"refresh_time":1502938745},"wx92f26a7f4a53ee54_wx_ticket":{"errcode":0,"errmsg":"ok","ticket":"HoagFKDcsGMVCIY2vOjf9pgoWy02GHCq1oGPVLF63Bvs3fFrHzYoCFWUeFeVJNFSzIacDh1bFrcc91L6XSaX6A","expires_in":7200,"refresh_time":1502938745},"banners":[{"img":"https://wwj.same.com/product/image/94xdgwe5c24.jpg","url":"http://t.same.com/s/-1j59"},{"img":"https://wwj.same.com/product/image/94xdgwe5c24.jpg","url":"http://t.same.com/s/-1j59"}]}
     */

    private ConfigBean config;

    public ConfigBean getConfig() {
        return config;
    }

    public void setConfig(ConfigBean config) {
        this.config = config;
    }

    public static class ConfigBean {
        /**
         * login_setting : {"allow_login_types":["email","weixin"]}
         * user_invite_action_body : {"content_type":"text/plain","title":"分享邀请码","text":"邀请好友加入可以免费获得喵喵币"}
         * user_invite_action_dest_url : http://t.same.com/s/-1j5o
         * app_store_assessors : [1,7]
         * action_api_room_play_jump_invite_code_body : {"content_type":"text/plain","title":"喵喵币不够了","text":"邀请好友加入可以免费获得喵喵币"}
         * wx92f26a7f4a53ee54_wx_access_token : {"access_token":"Ll2JhUU3aj5G1IjkCxYzKPGvgGjA_SBhKA88FNA_HwDXt6odqjMwtLULoiOUmm_Xrj-CCwfvkeY2iLxsBQGKIA8J9vLfRbcnnykr_zPoeZWD8BmQslrISIL08ypsxmrqGONfABAUCB","expires_in":7200,"refresh_time":1502938745}
         * wx92f26a7f4a53ee54_wx_ticket : {"errcode":0,"errmsg":"ok","ticket":"HoagFKDcsGMVCIY2vOjf9pgoWy02GHCq1oGPVLF63Bvs3fFrHzYoCFWUeFeVJNFSzIacDh1bFrcc91L6XSaX6A","expires_in":7200,"refresh_time":1502938745}
         * banners : [{"img":"https://wwj.same.com/product/image/94xdgwe5c24.jpg","url":"http://t.same.com/s/-1j59"},{"img":"https://wwj.same.com/product/image/94xdgwe5c24.jpg","url":"http://t.same.com/s/-1j59"}]
         */

        private LoginSettingBean login_setting;
        private UserInviteActionBodyBean user_invite_action_body;
        private String user_invite_action_dest_url;
        private ActionApiRoomPlayJumpInviteCodeBodyBean action_api_room_play_jump_invite_code_body;
        private Wx92f26a7f4a53ee54WxAccessTokenBean wx92f26a7f4a53ee54_wx_access_token;
        private Wx92f26a7f4a53ee54WxTicketBean wx92f26a7f4a53ee54_wx_ticket;
        private List<Integer> app_store_assessors;
        private List<BannersBean> banners;

        public LoginSettingBean getLogin_setting() {
            return login_setting;
        }

        public void setLogin_setting(LoginSettingBean login_setting) {
            this.login_setting = login_setting;
        }

        public UserInviteActionBodyBean getUser_invite_action_body() {
            return user_invite_action_body;
        }

        public void setUser_invite_action_body(UserInviteActionBodyBean user_invite_action_body) {
            this.user_invite_action_body = user_invite_action_body;
        }

        public String getUser_invite_action_dest_url() {
            return user_invite_action_dest_url;
        }

        public void setUser_invite_action_dest_url(String user_invite_action_dest_url) {
            this.user_invite_action_dest_url = user_invite_action_dest_url;
        }

        public ActionApiRoomPlayJumpInviteCodeBodyBean getAction_api_room_play_jump_invite_code_body() {
            return action_api_room_play_jump_invite_code_body;
        }

        public void setAction_api_room_play_jump_invite_code_body(ActionApiRoomPlayJumpInviteCodeBodyBean action_api_room_play_jump_invite_code_body) {
            this.action_api_room_play_jump_invite_code_body = action_api_room_play_jump_invite_code_body;
        }

        public Wx92f26a7f4a53ee54WxAccessTokenBean getWx92f26a7f4a53ee54_wx_access_token() {
            return wx92f26a7f4a53ee54_wx_access_token;
        }

        public void setWx92f26a7f4a53ee54_wx_access_token(Wx92f26a7f4a53ee54WxAccessTokenBean wx92f26a7f4a53ee54_wx_access_token) {
            this.wx92f26a7f4a53ee54_wx_access_token = wx92f26a7f4a53ee54_wx_access_token;
        }

        public Wx92f26a7f4a53ee54WxTicketBean getWx92f26a7f4a53ee54_wx_ticket() {
            return wx92f26a7f4a53ee54_wx_ticket;
        }

        public void setWx92f26a7f4a53ee54_wx_ticket(Wx92f26a7f4a53ee54WxTicketBean wx92f26a7f4a53ee54_wx_ticket) {
            this.wx92f26a7f4a53ee54_wx_ticket = wx92f26a7f4a53ee54_wx_ticket;
        }

        public List<Integer> getApp_store_assessors() {
            return app_store_assessors;
        }

        public void setApp_store_assessors(List<Integer> app_store_assessors) {
            this.app_store_assessors = app_store_assessors;
        }

        public List<BannersBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public static class LoginSettingBean {
            private List<String> allow_login_types;

            public List<String> getAllow_login_types() {
                return allow_login_types;
            }

            public void setAllow_login_types(List<String> allow_login_types) {
                this.allow_login_types = allow_login_types;
            }
        }

        public static class UserInviteActionBodyBean {
            /**
             * content_type : text/plain
             * title : 分享邀请码
             * text : 邀请好友加入可以免费获得喵喵币
             */

            private String content_type;
            private String title;
            private String text;

            public String getContent_type() {
                return content_type;
            }

            public void setContent_type(String content_type) {
                this.content_type = content_type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        public static class ActionApiRoomPlayJumpInviteCodeBodyBean {
            /**
             * content_type : text/plain
             * title : 喵喵币不够了
             * text : 邀请好友加入可以免费获得喵喵币
             */

            private String content_type;
            private String title;
            private String text;

            public String getContent_type() {
                return content_type;
            }

            public void setContent_type(String content_type) {
                this.content_type = content_type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        public static class Wx92f26a7f4a53ee54WxAccessTokenBean {
            /**
             * access_token : Ll2JhUU3aj5G1IjkCxYzKPGvgGjA_SBhKA88FNA_HwDXt6odqjMwtLULoiOUmm_Xrj-CCwfvkeY2iLxsBQGKIA8J9vLfRbcnnykr_zPoeZWD8BmQslrISIL08ypsxmrqGONfABAUCB
             * expires_in : 7200
             * refresh_time : 1502938745
             */

            private String access_token;
            private int expires_in;
            private int refresh_time;

            public String getAccess_token() {
                return access_token;
            }

            public void setAccess_token(String access_token) {
                this.access_token = access_token;
            }

            public int getExpires_in() {
                return expires_in;
            }

            public void setExpires_in(int expires_in) {
                this.expires_in = expires_in;
            }

            public int getRefresh_time() {
                return refresh_time;
            }

            public void setRefresh_time(int refresh_time) {
                this.refresh_time = refresh_time;
            }
        }

        public static class Wx92f26a7f4a53ee54WxTicketBean {
            /**
             * errcode : 0
             * errmsg : ok
             * ticket : HoagFKDcsGMVCIY2vOjf9pgoWy02GHCq1oGPVLF63Bvs3fFrHzYoCFWUeFeVJNFSzIacDh1bFrcc91L6XSaX6A
             * expires_in : 7200
             * refresh_time : 1502938745
             */

            private int errcode;
            private String errmsg;
            private String ticket;
            private int expires_in;
            private int refresh_time;

            public int getErrcode() {
                return errcode;
            }

            public void setErrcode(int errcode) {
                this.errcode = errcode;
            }

            public String getErrmsg() {
                return errmsg;
            }

            public void setErrmsg(String errmsg) {
                this.errmsg = errmsg;
            }

            public String getTicket() {
                return ticket;
            }

            public void setTicket(String ticket) {
                this.ticket = ticket;
            }

            public int getExpires_in() {
                return expires_in;
            }

            public void setExpires_in(int expires_in) {
                this.expires_in = expires_in;
            }

            public int getRefresh_time() {
                return refresh_time;
            }

            public void setRefresh_time(int refresh_time) {
                this.refresh_time = refresh_time;
            }
        }

        public static class BannersBean {
            /**
             * img : https://wwj.same.com/product/image/94xdgwe5c24.jpg
             * url : http://t.same.com/s/-1j59
             */

            private String img;
            private String url;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
