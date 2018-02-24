package com.renren.wawa.model;

import com.renren.wawa.config.Constants;
import com.renren.wawa.json.JSONToBeanHandler;
import com.renren.wawa.utils.BBLog;

public class EmailLoginBean extends BaseObject implements Cloneable{

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

    public static class DataBean implements Cloneable{
        /**
         * token : 9a2acdabb3490341326d7958f429e12ff7b0e018
         * tcp : 123.206.109.114:9090
         * live_stream : {"appid":"1400030604","identifier":"wawajiuser_1","signature":"eJxNzFFPgzAUhuH-wu2MaSnM6d0mC26DiFY28Kap60HPEIalDIfxv0sIRm-f53zny3oK*KXc749NaYQ5V2DdWMS6GDIqKA1mCLqPrWzlAZsatKCjy6pCJaQRTKt-s1rlYqC*UYcQwsiUOCPCZ4UahMzM8JW6rmv3J6OeQNd4LHuwCXWpzQj5Q4MFDBPC3OmM2r8va3ztc7hMb1cP3lXh5Z2OZpxtfF6vw8KcU-*ueYfd6SMO-EkSdktUk*YR56u3eRTxZA3XhzjF4j5LQwjki*0UuRd7yTbaLZ475VC*4V7bWd8-UKZbEQ__","expire_until":1519120124}
         * user : {"id":1,"nickname":"daydaygo","is_assessor":0}
         * wallet : {"balance":98439}
         */

        private String sessionId;
        private String socketUrl;
        private LiveStreamBean live;
        private UserBean userInfo;
        private WalletBean account;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getSocketUrl() {
            return socketUrl;
        }

        public void setSocketUrl(String socketUrl) {
            this.socketUrl = socketUrl;
        }

        public LiveStreamBean getLive() {
            return live;
        }

        public void setLive(LiveStreamBean live) {
            this.live = live;
        }

        public UserBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserBean userInfo) {
            this.userInfo = userInfo;
        }

        public WalletBean getAccount() {
            return account;
        }

        public void setAccount(WalletBean account) {
            this.account = account;
        }

        public static class LiveStreamBean implements Cloneable{
            /**
             * appid : 1400030604
             * identifier : wawajiuser_1
             * signature : eJxNzFFPgzAUhuH-wu2MaSnM6d0mC26DiFY28Kap60HPEIalDIfxv0sIRm-f53zny3oK*KXc749NaYQ5V2DdWMS6GDIqKA1mCLqPrWzlAZsatKCjy6pCJaQRTKt-s1rlYqC*UYcQwsiUOCPCZ4UahMzM8JW6rmv3J6OeQNd4LHuwCXWpzQj5Q4MFDBPC3OmM2r8va3ztc7hMb1cP3lXh5Z2OZpxtfF6vw8KcU-*ueYfd6SMO-EkSdktUk*YR56u3eRTxZA3XhzjF4j5LQwjki*0UuRd7yTbaLZ475VC*4V7bWd8-UKZbEQ__
             * expire_until : 1519120124
             */

            private String appId;
            private String identifier;
            private String sign;
            private int expire;

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getIdentifier() {
                return identifier;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public int getExpire() {
                return expire;
            }

            public void setExpire(int expire) {
                this.expire = expire;
            }

            @Override
            public Object clone()  {
                LiveStreamBean liveStreamBean;
                try {
                    liveStreamBean = (LiveStreamBean) super.clone();
                    return liveStreamBean;
                } catch (CloneNotSupportedException e) {
                    BBLog.e("wenba", e);
                }
                return null;
            }

//            @Override
//            public String toString() {
//                return "LiveStreamBean{" +
//                        "appid='" + appid + '\'' +
//                        ", identifier='" + identifier + '\'' +
//                        ", signature='" + signature + '\'' +
//                        ", expire_until=" + expire_until +
//                        '}';
//            }
        }

        public static class UserBean implements Cloneable{
            /**
             * id : 1
             * nickname : daydaygo
             * is_assessor : 0
             *
             *
             * {"id":54482,"nickname":"qilu","invite_code":0,"is_admin":1,"is_assessor":0}
             */

            private int id;
            private String nickname;
            private int is_assessor;
            private String avatar;
            private int role;
            private String gender;
            private int is_staff;

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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public int getIs_staff() {
                return is_staff;
            }

            public void setIs_staff(int is_staff) {
                this.is_staff = is_staff;
            }

            @Override
            public Object clone()  {
                UserBean userBean;
                try {
                    userBean = (UserBean) super.clone();
                    return userBean;
                } catch (CloneNotSupportedException e) {
                    BBLog.e(Constants.TAG, e);
                }
                return null;
            }

//            @Override
//            public String toString() {
//                return "UserBean{" +
//                        "id=" + id +
//                        ", nickname='" + nickname + '\'' +
//                        ", is_assessor=" + is_assessor +
//                        ", avatar='" + avatar + '\'' +
//                        ", is_admin=" + is_admin +
//                        ", gender='" + gender + '\'' +
//                        '}';
//            }
        }

        public static class WalletBean implements Cloneable{
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


            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public String toString() {
                return "WalletBean{" +
                        "balance=" + balance +
                        '}';
            }
        }

    }


    @Override
    public Object clone()  {
        EmailLoginBean emailLoginBean;
        try {
            emailLoginBean = (EmailLoginBean) super.clone();
            return emailLoginBean;
        } catch (CloneNotSupportedException e) {
            BBLog.e(Constants.TAG, e);
        }
        return null;
    }


    @Override
    public String toString() {
        try {
            return JSONToBeanHandler.toJsonString(this);
        } catch (Exception e) {
            BBLog.e(Constants.TAG, e);
        }

        return super.toString();
    }

//    @Override
//    public String toString() {
//        return "EmailLoginBean{" +
//                "data=" + data +
//                '}';
//    }
}
