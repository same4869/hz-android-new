package com.renren.wawa.model;

import java.util.List;

public class UserInvitationCodeBean extends BaseObject {
    /**
     * data : {"inviteCode":"9489","desc":"每邀请一个好友加入，注册成功后，你可以获得个喵喵币，最多可以获多个币。","title":"分享邀请码","shareList":[{"title":"分享到朋友圈","type":"wx_friend_circle","url":"wawaji: //claw.renrenzww.com/share-web-link"},{"title":"微信好友","type":"wx_friend","url":"wawaji: //claw.renrenzww.com/share-web-link?"}]}
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
         * inviteCode : 9489
         * desc : 每邀请一个好友加入，注册成功后，你可以获得个喵喵币，最多可以获多个币。
         * title : 分享邀请码
         * shareList : [{"title":"分享到朋友圈","type":"wx_friend_circle","url":"wawaji: //claw.renrenzww.com/share-web-link"},{"title":"微信好友","type":"wx_friend","url":"wawaji: //claw.renrenzww.com/share-web-link?"}]
         */

        private String inviteCode;
        private String desc;
        private String title;
        private List<ShareListBean> shareList;

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ShareListBean> getShareList() {
            return shareList;
        }

        public void setShareList(List<ShareListBean> shareList) {
            this.shareList = shareList;
        }

        public static class ShareListBean {
            /**
             * title : 分享到朋友圈
             * type : wx_friend_circle
             * url : wawaji: //claw.renrenzww.com/share-web-link
             */

            private String title;
            private String type;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
