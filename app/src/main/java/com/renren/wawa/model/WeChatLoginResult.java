package com.renren.wawa.model;

import java.util.List;

public class WeChatLoginResult extends BaseObject {

    /**
     * id : 1
     * nickname : foobar
     */

    private UserBean user;
    /**
     * balance : 0
     */

    private WalletBean wallet;
    /**
     * user : {"id":1,"nickname":"foobar"}
     * wallet : {"balance":0}
     * token : foobar
     */

    private String token;
    /**
     * extra_action : {"type":"choices","body":{"content_type":"text/plain","title":"暂时停止新用户注册","text":"娃娃机累了，请明日再来；关注微信公众号 直播抓娃娃，第一时间获取开放时间和娃娃更新信息。"},"options":[{"title":"好的","type":"cancel"}]}
     */

    private ExtraActionBean extra_action;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public WalletBean getWallet() {
        return wallet;
    }

    public void setWallet(WalletBean wallet) {
        this.wallet = wallet;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ExtraActionBean getExtra_action() {
        return extra_action;
    }

    public void setExtra_action(ExtraActionBean extra_action) {
        this.extra_action = extra_action;
    }

    public static class UserBean {
        private int id;
        private String nickname;

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
    }

    public static class WalletBean {
        private int balance;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }

    public static class ExtraActionBean {
        /**
         * type : choices
         * body : {"content_type":"text/plain","title":"暂时停止新用户注册","text":"娃娃机累了，请明日再来；关注微信公众号 直播抓娃娃，第一时间获取开放时间和娃娃更新信息。"}
         * options : [{"title":"好的","type":"cancel"}]
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
             * title : 暂时停止新用户注册
             * text : 娃娃机累了，请明日再来；关注微信公众号 直播抓娃娃，第一时间获取开放时间和娃娃更新信息。
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
             * title : 好的
             * type : cancel
             */

            private String title;
            private String type;

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
        }
    }

    @Override
    public String toString() {
        return "WeChatLoginResult{" +
                "user=" + user +
                ", wallet=" + wallet +
                ", token='" + token + '\'' +
                ", extra_action=" + extra_action +
                '}';
    }
}
