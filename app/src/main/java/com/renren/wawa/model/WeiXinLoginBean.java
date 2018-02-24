package com.renren.wawa.model;

public class WeiXinLoginBean extends BaseObject {

    /**
     * data : {"token":"9a2acdabb3490341326d7958f429e12ff7b0e018","tcp":"123.206.109.114:9090","live_stream":{"appid":"1400030604","identifier":"wawajiuser_1","signature":"eJxNzFFPgzAUhuH-wu2MaSnM6d0mC26DiFY28Kap60HPEIalDIfxv0sIRm-f53zny3oK*KXc749NaYQ5V2DdWMS6GDIqKA1mCLqPrWzlAZsatKCjy6pCJaQRTKt-s1rlYqC*UYcQwsiUOCPCZ4UahMzM8JW6rmv3J6OeQNd4LHuwCXWpzQj5Q4MFDBPC3OmM2r8va3ztc7hMb1cP3lXh5Z2OZpxtfF6vw8KcU-*ueYfd6SMO-EkSdktUk*YR56u3eRTxZA3XhzjF4j5LQwjki*0UuRd7yTbaLZ475VC*4V7bWd8-UKZbEQ__","expire_until":1519120124},"user":{"id":1,"nickname":"daydaygo","is_assessor":0},"wallet":{"balance":98439}}
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
         * token : 9a2acdabb3490341326d7958f429e12ff7b0e018
         * tcp : 123.206.109.114:9090
         * live_stream : {"appid":"1400030604","identifier":"wawajiuser_1","signature":"eJxNzFFPgzAUhuH-wu2MaSnM6d0mC26DiFY28Kap60HPEIalDIfxv0sIRm-f53zny3oK*KXc749NaYQ5V2DdWMS6GDIqKA1mCLqPrWzlAZsatKCjy6pCJaQRTKt-s1rlYqC*UYcQwsiUOCPCZ4UahMzM8JW6rmv3J6OeQNd4LHuwCXWpzQj5Q4MFDBPC3OmM2r8va3ztc7hMb1cP3lXh5Z2OZpxtfF6vw8KcU-*ueYfd6SMO-EkSdktUk*YR56u3eRTxZA3XhzjF4j5LQwjki*0UuRd7yTbaLZ475VC*4V7bWd8-UKZbEQ__","expire_until":1519120124}
         * user : {"id":1,"nickname":"daydaygo","is_assessor":0}
         * wallet : {"balance":98439}
         */

        private String token;
        private String tcp;
        private LiveStreamBean live_stream;
        private UserBean user;
        private WalletBean wallet;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTcp() {
            return tcp;
        }

        public void setTcp(String tcp) {
            this.tcp = tcp;
        }

        public LiveStreamBean getLive_stream() {
            return live_stream;
        }

        public void setLive_stream(LiveStreamBean live_stream) {
            this.live_stream = live_stream;
        }

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

        public static class LiveStreamBean {
            /**
             * appid : 1400030604
             * identifier : wawajiuser_1
             * signature : eJxNzFFPgzAUhuH-wu2MaSnM6d0mC26DiFY28Kap60HPEIalDIfxv0sIRm-f53zny3oK*KXc749NaYQ5V2DdWMS6GDIqKA1mCLqPrWzlAZsatKCjy6pCJaQRTKt-s1rlYqC*UYcQwsiUOCPCZ4UahMzM8JW6rmv3J6OeQNd4LHuwCXWpzQj5Q4MFDBPC3OmM2r8va3ztc7hMb1cP3lXh5Z2OZpxtfF6vw8KcU-*ueYfd6SMO-EkSdktUk*YR56u3eRTxZA3XhzjF4j5LQwjki*0UuRd7yTbaLZ475VC*4V7bWd8-UKZbEQ__
             * expire_until : 1519120124
             */

            private String appid;
            private String identifier;
            private String signature;
            private int expire_until;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getIdentifier() {
                return identifier;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public int getExpire_until() {
                return expire_until;
            }

            public void setExpire_until(int expire_until) {
                this.expire_until = expire_until;
            }
        }

        public static class UserBean {
            /**
             * id : 1
             * nickname : daydaygo
             * is_assessor : 0
             */

            private int id;
            private String nickname;
            private int is_assessor;

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

            public int getIs_assessor() {
                return is_assessor;
            }

            public void setIs_assessor(int is_assessor) {
                this.is_assessor = is_assessor;
            }
        }

        public static class WalletBean {
            /**
             * balance : 98439
             */

            private int balance;

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }
        }
    }
}
