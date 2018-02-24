package com.renren.wawa.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvitationCode extends BaseObject {

    /**
     * invitation : {"code":"1234","description":"每邀请一个好友加入，注册成功后，你可以获得60个喵喵币，最多可以获多6000个币。","weixin_share":{"type":"actionSheet","body":{"content_type":"text/plain","title":"喵喵币不够了","text":"邀请好友加入可以免费获得喵喵币"},"options":[{"title":"分享到朋友圈","type":"default","url":"wawaji://claw.same.com/share-web-link?to=weixin_timeline&url=http%3A%2F%2Ft.same.com%2Fs%2F-1j5o&title=%E9%9C%87%E6%83%8A%21+%E7%8E%B0%E5%9C%A8%E5%B0%8F%E5%AD%A6%E7%94%9F%E9%83%BD%E5%9C%A8%E7%94%A8%E6%89%8B%E6%9C%BA%E8%BF%9C%E7%A8%8B%E6%8E%A7%E5%88%B6%E6%8A%93%E5%A8%83%E5%A8%83%21+%E7%94%A9%E5%87%BA%E5%86%85%E6%B5%8B%E9%82%80%E8%AF%B7%E7%A0%81%EF%BC%9A82593%EF%BC%8C%E6%9D%A5%E4%B8%80%E8%B5%B7%E6%8A%93%E5%A8%83%E5%A8%83%EF%BC%81&desc="},{"title":"微信好友","type":"default","url":"wawaji://claw.same.com/share-web-link?to=weixin_friend&url=http%3A%2F%2Ft.same.com%2Fs%2F-1j5o&title=%E9%9C%87%E6%83%8A%21+%E7%8E%B0%E5%9C%A8%E5%B0%8F%E5%AD%A6%E7%94%9F%E9%83%BD%E5%9C%A8%E7%94%A8%E6%89%8B%E6%9C%BA%E8%BF%9C%E7%A8%8B%E6%8E%A7%E5%88%B6%E6%8A%93%E5%A8%83%E5%A8%83%21+%E7%94%A9%E5%87%BA%E5%86%85%E6%B5%8B%E9%82%80%E8%AF%B7%E7%A0%81%EF%BC%9A82593%EF%BC%8C%E6%9D%A5%E4%B8%80%E8%B5%B7%E6%8A%93%E5%A8%83%E5%A8%83%EF%BC%81&desc="},{"title":"取消","type":"cancel"}]}}
     */

    private InvitationBean invitation;

    public InvitationBean getInvitation() {
        return invitation;
    }

    public void setInvitation(InvitationBean invitation) {
        this.invitation = invitation;
    }

    public static class InvitationBean {
        /**
         * code : 1234
         * description : 每邀请一个好友加入，注册成功后，你可以获得60个喵喵币，最多可以获多6000个币。
         * weixin_share : {"type":"actionSheet","body":{"content_type":"text/plain","title":"喵喵币不够了","text":"邀请好友加入可以免费获得喵喵币"},"options":[{"title":"分享到朋友圈","type":"default","url":"wawaji://claw.same.com/share-web-link?to=weixin_timeline&url=http%3A%2F%2Ft.same.com%2Fs%2F-1j5o&title=%E9%9C%87%E6%83%8A%21+%E7%8E%B0%E5%9C%A8%E5%B0%8F%E5%AD%A6%E7%94%9F%E9%83%BD%E5%9C%A8%E7%94%A8%E6%89%8B%E6%9C%BA%E8%BF%9C%E7%A8%8B%E6%8E%A7%E5%88%B6%E6%8A%93%E5%A8%83%E5%A8%83%21+%E7%94%A9%E5%87%BA%E5%86%85%E6%B5%8B%E9%82%80%E8%AF%B7%E7%A0%81%EF%BC%9A82593%EF%BC%8C%E6%9D%A5%E4%B8%80%E8%B5%B7%E6%8A%93%E5%A8%83%E5%A8%83%EF%BC%81&desc="},{"title":"微信好友","type":"default","url":"wawaji://claw.same.com/share-web-link?to=weixin_friend&url=http%3A%2F%2Ft.same.com%2Fs%2F-1j5o&title=%E9%9C%87%E6%83%8A%21+%E7%8E%B0%E5%9C%A8%E5%B0%8F%E5%AD%A6%E7%94%9F%E9%83%BD%E5%9C%A8%E7%94%A8%E6%89%8B%E6%9C%BA%E8%BF%9C%E7%A8%8B%E6%8E%A7%E5%88%B6%E6%8A%93%E5%A8%83%E5%A8%83%21+%E7%94%A9%E5%87%BA%E5%86%85%E6%B5%8B%E9%82%80%E8%AF%B7%E7%A0%81%EF%BC%9A82593%EF%BC%8C%E6%9D%A5%E4%B8%80%E8%B5%B7%E6%8A%93%E5%A8%83%E5%A8%83%EF%BC%81&desc="},{"title":"取消","type":"cancel"}]}
         */

        @SerializedName("code")
        private String codeX;
        private String description;
        private WeixinShareBean weixin_share;

        public String getCodeX() {
            return codeX;
        }

        public void setCodeX(String codeX) {
            this.codeX = codeX;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public WeixinShareBean getWeixin_share() {
            return weixin_share;
        }

        public void setWeixin_share(WeixinShareBean weixin_share) {
            this.weixin_share = weixin_share;
        }

        public static class WeixinShareBean {
            /**
             * type : actionSheet
             * body : {"content_type":"text/plain","title":"喵喵币不够了","text":"邀请好友加入可以免费获得喵喵币"}
             * options : [{"title":"分享到朋友圈","type":"default","url":"wawaji://claw.same.com/share-web-link?to=weixin_timeline&url=http%3A%2F%2Ft.same.com%2Fs%2F-1j5o&title=%E9%9C%87%E6%83%8A%21+%E7%8E%B0%E5%9C%A8%E5%B0%8F%E5%AD%A6%E7%94%9F%E9%83%BD%E5%9C%A8%E7%94%A8%E6%89%8B%E6%9C%BA%E8%BF%9C%E7%A8%8B%E6%8E%A7%E5%88%B6%E6%8A%93%E5%A8%83%E5%A8%83%21+%E7%94%A9%E5%87%BA%E5%86%85%E6%B5%8B%E9%82%80%E8%AF%B7%E7%A0%81%EF%BC%9A82593%EF%BC%8C%E6%9D%A5%E4%B8%80%E8%B5%B7%E6%8A%93%E5%A8%83%E5%A8%83%EF%BC%81&desc="},{"title":"微信好友","type":"default","url":"wawaji://claw.same.com/share-web-link?to=weixin_friend&url=http%3A%2F%2Ft.same.com%2Fs%2F-1j5o&title=%E9%9C%87%E6%83%8A%21+%E7%8E%B0%E5%9C%A8%E5%B0%8F%E5%AD%A6%E7%94%9F%E9%83%BD%E5%9C%A8%E7%94%A8%E6%89%8B%E6%9C%BA%E8%BF%9C%E7%A8%8B%E6%8E%A7%E5%88%B6%E6%8A%93%E5%A8%83%E5%A8%83%21+%E7%94%A9%E5%87%BA%E5%86%85%E6%B5%8B%E9%82%80%E8%AF%B7%E7%A0%81%EF%BC%9A82593%EF%BC%8C%E6%9D%A5%E4%B8%80%E8%B5%B7%E6%8A%93%E5%A8%83%E5%A8%83%EF%BC%81&desc="},{"title":"取消","type":"cancel"}]
             */

            private String type;
            private BodyBean body;
            private List<OptionsBean> options;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public BodyBean getBody() {
                return body;
            }

            public void setBody(BodyBean body) {
                this.body = body;
            }

            public List<OptionsBean> getOptions() {
                return options;
            }

            public void setOptions(List<OptionsBean> options) {
                this.options = options;
            }

            public static class BodyBean {
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

            public static class OptionsBean {
                /**
                 * title : 分享到朋友圈
                 * type : default
                 * url : wawaji://claw.same.com/share-web-link?to=weixin_timeline&url=http%3A%2F%2Ft.same.com%2Fs%2F-1j5o&title=%E9%9C%87%E6%83%8A%21+%E7%8E%B0%E5%9C%A8%E5%B0%8F%E5%AD%A6%E7%94%9F%E9%83%BD%E5%9C%A8%E7%94%A8%E6%89%8B%E6%9C%BA%E8%BF%9C%E7%A8%8B%E6%8E%A7%E5%88%B6%E6%8A%93%E5%A8%83%E5%A8%83%21+%E7%94%A9%E5%87%BA%E5%86%85%E6%B5%8B%E9%82%80%E8%AF%B7%E7%A0%81%EF%BC%9A82593%EF%BC%8C%E6%9D%A5%E4%B8%80%E8%B5%B7%E6%8A%93%E5%A8%83%E5%A8%83%EF%BC%81&desc=
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
}
