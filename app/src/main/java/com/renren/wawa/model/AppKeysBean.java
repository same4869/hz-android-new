package com.renren.wawa.model;

import java.util.List;


public class AppKeysBean extends BaseObject {


    /**
     * data : {"login_setting":{"allow_login_types":["weixin","email"]},"banners":[{"img":"https://wwj.same.com/product/image/94xdgwe5c24.jpg","url":"http://t.same.com/s/-1j59"},{"img":"https://wwj.same.com/product/image/94xdgwe5c24.jpg","url":"http://t.same.com/s/-1j59"}]}
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
         * login_setting : {"allow_login_types":["weixin","email"]}
         * banners : [{"img":"https://wwj.same.com/product/image/94xdgwe5c24.jpg","url":"http://t.same.com/s/-1j59"},{"img":"https://wwj.same.com/product/image/94xdgwe5c24.jpg","url":"http://t.same.com/s/-1j59"}]
         */

        private LoginSettingBean login_setting;
        private List<BannersBean> banners;

        public LoginSettingBean getLogin_setting() {
            return login_setting;
        }

        public void setLogin_setting(LoginSettingBean login_setting) {
            this.login_setting = login_setting;
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
