package com.renren.wawa.model;

import java.util.List;

public class UserInfoMeProfileBean extends BaseObject {


    /**
     * user : {"id":39396,"nickname":"雪海(张启露)","avatar":"http://wwj.same.com/wx/a:39396:555a3e7f","live_stream_user_id":"wawajiuser_39396","gender":"male","is_staff":0,"is_assessor":false}
     * game_sessions : {"total":2,"recent_sessions":[{"id":557565,"video_url":"http://wwj.same.com/room/p186r13s557565-17a3f1a6.mp4","state":"fin_crawled","state_text":"抓取成功","created_at":"2017-08-24T05:08:37.000Z","created_ts":1503551317,"product":{"sku":"wawa_082117_01","name":"河马姆明","images":["https://wwj.same.com/product/image/enxpvd0u0o4.jpg","https://wwj.same.com/product/image/tvxg8gfigxh.jpg"],"refund_price":105}},{"id":331138,"video_url":"http://wwj.same.com/room/p135r7s331138-b7dda3c1.mp4","state":"fin_crawled","state_text":"抓取成功","created_at":"2017-08-16T00:51:27.000Z","created_ts":1502844687,"product":{"sku":"wawa_081217_06","name":"变装猪布朗熊","images":["https://wwj.same.com/product/image/bfng9uncf2w.jpg","https://wwj.same.com/product/image/mfpjavybla.jpg","https://wwj.same.com/product/image/a7ls3wrfc1a.jpg","https://wwj.same.com/product/image/m1mewzs0dt.jpg","https://wwj.same.com/product/image/gdxgsm6g79m.jpg","https://wwj.same.com/product/image/1zcdywhpkvrh.jpg"],"refund_price":315}}]}
     */

    private UserBean user;
    private GameSessionsBean game_sessions;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public GameSessionsBean getGame_sessions() {
        return game_sessions;
    }

    public void setGame_sessions(GameSessionsBean game_sessions) {
        this.game_sessions = game_sessions;
    }

    public static class UserBean {
        /**
         * id : 39396
         * nickname : 雪海(张启露)
         * avatar : http://wwj.same.com/wx/a:39396:555a3e7f
         * live_stream_user_id : wawajiuser_39396
         * gender : male
         * is_staff : 0
         * is_assessor : false
         */

        private int id;
        private String nickname;
        private String avatar;
        private String live_stream_user_id;
        private String gender;
        private int is_staff;
        private boolean is_assessor;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getLive_stream_user_id() {
            return live_stream_user_id;
        }

        public void setLive_stream_user_id(String live_stream_user_id) {
            this.live_stream_user_id = live_stream_user_id;
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

        public boolean isIs_assessor() {
            return is_assessor;
        }

        public void setIs_assessor(boolean is_assessor) {
            this.is_assessor = is_assessor;
        }
    }

    public static class GameSessionsBean {
        /**
         * total : 2
         * recent_sessions : [{"id":557565,"video_url":"http://wwj.same.com/room/p186r13s557565-17a3f1a6.mp4","state":"fin_crawled","state_text":"抓取成功","created_at":"2017-08-24T05:08:37.000Z","created_ts":1503551317,"product":{"sku":"wawa_082117_01","name":"河马姆明","images":["https://wwj.same.com/product/image/enxpvd0u0o4.jpg","https://wwj.same.com/product/image/tvxg8gfigxh.jpg"],"refund_price":105}},{"id":331138,"video_url":"http://wwj.same.com/room/p135r7s331138-b7dda3c1.mp4","state":"fin_crawled","state_text":"抓取成功","created_at":"2017-08-16T00:51:27.000Z","created_ts":1502844687,"product":{"sku":"wawa_081217_06","name":"变装猪布朗熊","images":["https://wwj.same.com/product/image/bfng9uncf2w.jpg","https://wwj.same.com/product/image/mfpjavybla.jpg","https://wwj.same.com/product/image/a7ls3wrfc1a.jpg","https://wwj.same.com/product/image/m1mewzs0dt.jpg","https://wwj.same.com/product/image/gdxgsm6g79m.jpg","https://wwj.same.com/product/image/1zcdywhpkvrh.jpg"],"refund_price":315}}]
         */

        private int total;
        private List<RecentSessionsBean> recent_sessions;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RecentSessionsBean> getRecent_sessions() {
            return recent_sessions;
        }

        public void setRecent_sessions(List<RecentSessionsBean> recent_sessions) {
            this.recent_sessions = recent_sessions;
        }

        public static class RecentSessionsBean {
            /**
             * id : 557565
             * video_url : http://wwj.same.com/room/p186r13s557565-17a3f1a6.mp4
             * state : fin_crawled
             * state_text : 抓取成功
             * created_at : 2017-08-24T05:08:37.000Z
             * created_ts : 1503551317
             * product : {"sku":"wawa_082117_01","name":"河马姆明","images":["https://wwj.same.com/product/image/enxpvd0u0o4.jpg","https://wwj.same.com/product/image/tvxg8gfigxh.jpg"],"refund_price":105}
             */

            private int id;
            private String video_url;
            private String state;
            private String state_text;
            private String created_at;
            private int created_ts;
            private ProductBean product;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getVideo_url() {
                return video_url;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getState_text() {
                return state_text;
            }

            public void setState_text(String state_text) {
                this.state_text = state_text;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public int getCreated_ts() {
                return created_ts;
            }

            public void setCreated_ts(int created_ts) {
                this.created_ts = created_ts;
            }

            public ProductBean getProduct() {
                return product;
            }

            public void setProduct(ProductBean product) {
                this.product = product;
            }

            public static class ProductBean {
                /**
                 * sku : wawa_082117_01
                 * name : 河马姆明
                 * images : ["https://wwj.same.com/product/image/enxpvd0u0o4.jpg","https://wwj.same.com/product/image/tvxg8gfigxh.jpg"]
                 * refund_price : 105
                 */

                private String sku;
                private String name;
                private int refund_price;
                private List<String> images;

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getRefund_price() {
                    return refund_price;
                }

                public void setRefund_price(int refund_price) {
                    this.refund_price = refund_price;
                }

                public List<String> getImages() {
                    return images;
                }

                public void setImages(List<String> images) {
                    this.images = images;
                }
            }
        }
    }
}
