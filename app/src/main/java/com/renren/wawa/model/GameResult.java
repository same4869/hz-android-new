package com.renren.wawa.model;

public class GameResult extends BaseObject {


    /**
     * game : {"tcp":"123.206.198.16:5578","ws":"ws://123.206.198.16:15559","token":"b76276be96fca1d56abbe3e3f101d8fc3f786a04","session":{"id":1656947},"qiniu":{"token":"WpDE2kaUkmA87QnKEeEQ-ZFuH3Jjx4Rrp7qOC2d1:nAp3CoXNZyvEYX6HDn-s4Iwm-bo=:eyJyZXR1cm5Cb2R5Ijoie1wia2V5XCI6ICQoa2V5KX0iLCJzYXZlS2V5Ijoicm9vbS9wMTk0cjExNXMxNjU2OTQ3LWQ3MmNiNGU5Lm1wNCIsInNjb3BlIjoid2F3YWppIiwiZGVhZGxpbmUiOjE1MDQ2MDUyMzB9","expired_at":1504605230,"base_url":"http://wwj.same.com","url":"http://wwj.same.com/room/p194r115s1656947-d72cb4e9.mp4","key":"room/p194r115s1656947-d72cb4e9.mp4"},"bridge":{"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZXNzaW9uX2lkIjoxNjU2OTQ3LCJtYWNoaW5lX3Rva2VuIjoibWE6MTM2IiwicWluaXUiOnsidG9rZW4iOiJXcERFMmthVWttQTg3UW5LRWVFUS1aRnVIM0pqeDRScnA3cU9DMmQxOm5BcDNDb1hOWnl2RVlYNkhEbi1zNEl3bS1ibz06ZXlKeVpYUjFjbTVDYjJSNUlqb2llMXdpYTJWNVhDSTZJQ1FvYTJWNUtYMGlMQ0p6WVhabFMyVjVJam9pY205dmJTOXdNVGswY2pFeE5YTXhOalUyT1RRM0xXUTNNbU5pTkdVNUxtMXdOQ0lzSW5OamIzQmxJam9pZDJGM1lXcHBJaXdpWkdWaFpHeHBibVVpT2pFMU1EUTJNRFV5TXpCOSIsImV4cGlyZWRfYXQiOjE1MDQ2MDUyMzAsImJhc2VfdXJsIjoiaHR0cDovL3d3ai5zYW1lLmNvbSIsInVybCI6Imh0dHA6Ly93d2ouc2FtZS5jb20vcm9vbS9wMTk0cjExNXMxNjU2OTQ3LWQ3MmNiNGU5Lm1wNCIsImtleSI6InJvb20vcDE5NHIxMTVzMTY1Njk0Ny1kNzJjYjRlOS5tcDQifSwiaWF0IjoxNTA0NjAxNjMwLCJleHAiOjE1MDQ2MDIyMzB9.cmGOmKauPxAw-6mv_8oyXl5w_ynogaAbOH4FzLTpF2E","start_url":"http://bridge.wwj.same.com"}}
     */

    private GameBean game;

    public GameBean getGame() {
        return game;
    }

    public void setGame(GameBean game) {
        this.game = game;
    }

    public static class GameBean {
        /**
         * tcp : 123.206.198.16:5578
         * ws : ws://123.206.198.16:15559
         * token : b76276be96fca1d56abbe3e3f101d8fc3f786a04
         * session : {"id":1656947}
         * qiniu : {"token":"WpDE2kaUkmA87QnKEeEQ-ZFuH3Jjx4Rrp7qOC2d1:nAp3CoXNZyvEYX6HDn-s4Iwm-bo=:eyJyZXR1cm5Cb2R5Ijoie1wia2V5XCI6ICQoa2V5KX0iLCJzYXZlS2V5Ijoicm9vbS9wMTk0cjExNXMxNjU2OTQ3LWQ3MmNiNGU5Lm1wNCIsInNjb3BlIjoid2F3YWppIiwiZGVhZGxpbmUiOjE1MDQ2MDUyMzB9","expired_at":1504605230,"base_url":"http://wwj.same.com","url":"http://wwj.same.com/room/p194r115s1656947-d72cb4e9.mp4","key":"room/p194r115s1656947-d72cb4e9.mp4"}
         * bridge : {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZXNzaW9uX2lkIjoxNjU2OTQ3LCJtYWNoaW5lX3Rva2VuIjoibWE6MTM2IiwicWluaXUiOnsidG9rZW4iOiJXcERFMmthVWttQTg3UW5LRWVFUS1aRnVIM0pqeDRScnA3cU9DMmQxOm5BcDNDb1hOWnl2RVlYNkhEbi1zNEl3bS1ibz06ZXlKeVpYUjFjbTVDYjJSNUlqb2llMXdpYTJWNVhDSTZJQ1FvYTJWNUtYMGlMQ0p6WVhabFMyVjVJam9pY205dmJTOXdNVGswY2pFeE5YTXhOalUyT1RRM0xXUTNNbU5pTkdVNUxtMXdOQ0lzSW5OamIzQmxJam9pZDJGM1lXcHBJaXdpWkdWaFpHeHBibVVpT2pFMU1EUTJNRFV5TXpCOSIsImV4cGlyZWRfYXQiOjE1MDQ2MDUyMzAsImJhc2VfdXJsIjoiaHR0cDovL3d3ai5zYW1lLmNvbSIsInVybCI6Imh0dHA6Ly93d2ouc2FtZS5jb20vcm9vbS9wMTk0cjExNXMxNjU2OTQ3LWQ3MmNiNGU5Lm1wNCIsImtleSI6InJvb20vcDE5NHIxMTVzMTY1Njk0Ny1kNzJjYjRlOS5tcDQifSwiaWF0IjoxNTA0NjAxNjMwLCJleHAiOjE1MDQ2MDIyMzB9.cmGOmKauPxAw-6mv_8oyXl5w_ynogaAbOH4FzLTpF2E","start_url":"http://bridge.wwj.same.com"}
         */

        private String tcp;
        private String ws;
        private String token;
        private SessionBean session;
        private QiniuBean qiniu;
        private BridgeBean bridge;

        public String getTcp() {
            return tcp;
        }

        public void setTcp(String tcp) {
            this.tcp = tcp;
        }

        public String getWs() {
            return ws;
        }

        public void setWs(String ws) {
            this.ws = ws;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public SessionBean getSession() {
            return session;
        }

        public void setSession(SessionBean session) {
            this.session = session;
        }

        public QiniuBean getQiniu() {
            return qiniu;
        }

        public void setQiniu(QiniuBean qiniu) {
            this.qiniu = qiniu;
        }

        public BridgeBean getBridge() {
            return bridge;
        }

        public void setBridge(BridgeBean bridge) {
            this.bridge = bridge;
        }

        public static class SessionBean {
            /**
             * id : 1656947
             */

            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class QiniuBean {
            /**
             * token : WpDE2kaUkmA87QnKEeEQ-ZFuH3Jjx4Rrp7qOC2d1:nAp3CoXNZyvEYX6HDn-s4Iwm-bo=:eyJyZXR1cm5Cb2R5Ijoie1wia2V5XCI6ICQoa2V5KX0iLCJzYXZlS2V5Ijoicm9vbS9wMTk0cjExNXMxNjU2OTQ3LWQ3MmNiNGU5Lm1wNCIsInNjb3BlIjoid2F3YWppIiwiZGVhZGxpbmUiOjE1MDQ2MDUyMzB9
             * expired_at : 1504605230
             * base_url : http://wwj.same.com
             * url : http://wwj.same.com/room/p194r115s1656947-d72cb4e9.mp4
             * key : room/p194r115s1656947-d72cb4e9.mp4
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

        public static class BridgeBean {
            /**
             * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZXNzaW9uX2lkIjoxNjU2OTQ3LCJtYWNoaW5lX3Rva2VuIjoibWE6MTM2IiwicWluaXUiOnsidG9rZW4iOiJXcERFMmthVWttQTg3UW5LRWVFUS1aRnVIM0pqeDRScnA3cU9DMmQxOm5BcDNDb1hOWnl2RVlYNkhEbi1zNEl3bS1ibz06ZXlKeVpYUjFjbTVDYjJSNUlqb2llMXdpYTJWNVhDSTZJQ1FvYTJWNUtYMGlMQ0p6WVhabFMyVjVJam9pY205dmJTOXdNVGswY2pFeE5YTXhOalUyT1RRM0xXUTNNbU5pTkdVNUxtMXdOQ0lzSW5OamIzQmxJam9pZDJGM1lXcHBJaXdpWkdWaFpHeHBibVVpT2pFMU1EUTJNRFV5TXpCOSIsImV4cGlyZWRfYXQiOjE1MDQ2MDUyMzAsImJhc2VfdXJsIjoiaHR0cDovL3d3ai5zYW1lLmNvbSIsInVybCI6Imh0dHA6Ly93d2ouc2FtZS5jb20vcm9vbS9wMTk0cjExNXMxNjU2OTQ3LWQ3MmNiNGU5Lm1wNCIsImtleSI6InJvb20vcDE5NHIxMTVzMTY1Njk0Ny1kNzJjYjRlOS5tcDQifSwiaWF0IjoxNTA0NjAxNjMwLCJleHAiOjE1MDQ2MDIyMzB9.cmGOmKauPxAw-6mv_8oyXl5w_ynogaAbOH4FzLTpF2E
             * start_url : http://bridge.wwj.same.com
             */

            private String token;
            private String start_url;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getStart_url() {
                return start_url;
            }

            public void setStart_url(String start_url) {
                this.start_url = start_url;
            }
        }
    }
}
