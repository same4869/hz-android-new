package com.renren.wawa.model;

import java.util.List;

public class RoomInfoBean extends BaseObject {

    /**
     * data : {"room":{"id":1,"cover":"https://wwj.same.com/product/image/qu1thcsdovj.jpg","crawled_times":0,"machine_token":"ma:1","price_in_fen":29,"name":"比你可爱的单身狗","type":"wawajimachine","updated_at":"2017-08-16 12:31:38","is_admin_only":1,"product_id":123,"live_stream":{"room_id":101,"master_user_id":"wawajimachine_master_1","slave01_user_id":"wawajimachine_slave01_1"}},"games":[{"id":334655,"created_at":"2017-08-16 11:39:57","player_id":40733,"player":{"id":40733,"is_admin":0,"nickname":"小艺欣 -","avatar":""},"video_url":"http://wwj.same.com/room/p123r1s334655-e2cebc32.mp4","state":"抓到了娃娃"}],"product":{"name":"比你可爱的单身狗","sku":"wawa_081017_02","refund_price":105,"description":"","images":["https://wwj.same.com/product/image/5ymvlj0u9r5.jpg","https://wwj.same.com/product/image/4d7oh2sqyff.jpeg","https://wwj.same.com/product/image/euu4j7txz45.jpeg"]}}
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
         * room : {"id":1,"cover":"https://wwj.same.com/product/image/qu1thcsdovj.jpg","crawled_times":0,"machine_token":"ma:1","price_in_fen":29,"name":"比你可爱的单身狗","type":"wawajimachine","updated_at":"2017-08-16 12:31:38","is_admin_only":1,"product_id":123,"live_stream":{"room_id":101,"master_user_id":"wawajimachine_master_1","slave01_user_id":"wawajimachine_slave01_1"}}
         * games : [{"id":334655,"created_at":"2017-08-16 11:39:57","player_id":40733,"player":{"id":40733,"is_admin":0,"nickname":"小艺欣 -","avatar":""},"video_url":"http://wwj.same.com/room/p123r1s334655-e2cebc32.mp4","state":"抓到了娃娃"}]
         * product : {"name":"比你可爱的单身狗","sku":"wawa_081017_02","refund_price":105,"description":"","images":["https://wwj.same.com/product/image/5ymvlj0u9r5.jpg","https://wwj.same.com/product/image/4d7oh2sqyff.jpeg","https://wwj.same.com/product/image/euu4j7txz45.jpeg"]}
         */

        private RoomBean roomInfo;
        private ProductBean wawa;
        private List<GamesBean> gameList;

        public RoomBean getRoomInfo() {
            return roomInfo;
        }

        public void setRoomInfo(RoomBean roomInfo) {
            this.roomInfo = roomInfo;
        }

        public ProductBean getWawa() {
            return wawa;
        }

        public void setWawa(ProductBean wawa) {
            this.wawa = wawa;
        }

        public List<GamesBean> getGameList() {
            return gameList;
        }

        public void setGameList(List<GamesBean> gameList) {
            this.gameList = gameList;
        }

        public static class RoomBean {
            /**
             * id : 1
             * cover : https://wwj.same.com/product/image/qu1thcsdovj.jpg
             * crawled_times : 0
             * machine_token : ma:1
             * price_in_fen : 29
             * name : 比你可爱的单身狗
             * type : wawajimachine
             * updated_at : 2017-08-16 12:31:38
             * is_admin_only : 1
             * product_id : 123
             * live_stream : {"room_id":101,"master_user_id":"wawajimachine_master_1","slave01_user_id":"wawajimachine_slave01_1"}
             */

            private int id;
            private String imgUrl;
            private int coin;
            private String name;
            private int role;
            private LiveStreamBean live;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public LiveStreamBean getLive() {
                return live;
            }

            public void setLive(LiveStreamBean live) {
                this.live = live;
            }

            public static class LiveStreamBean {
                /**
                 * room_id : 101
                 * master_user_id : wawajimachine_master_1
                 * slave01_user_id : wawajimachine_slave01_1
                 */

                private int room_id;
                private String mid;
                private String sid;

                public int getRoom_id() {
                    return room_id;
                }

                public void setRoom_id(int room_id) {
                    this.room_id = room_id;
                }

                public String getMid() {
                    return mid;
                }

                public void setMid(String mid) {
                    this.mid = mid;
                }

                public String getSid() {
                    return sid;
                }

                public void setSid(String sid) {
                    this.sid = sid;
                }
            }
        }

        public static class ProductBean {
            /**
             * name : 比你可爱的单身狗
             * sku : wawa_081017_02
             * refund_price : 105
             * description :
             * images : ["https://wwj.same.com/product/image/5ymvlj0u9r5.jpg","https://wwj.same.com/product/image/4d7oh2sqyff.jpeg","https://wwj.same.com/product/image/euu4j7txz45.jpeg"]
             */

            private String name;
            private List<String> imgUrl;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getImages() {
                return imgUrl;
            }

            public void setImages(List<String> images) {
                this.imgUrl = images;
            }
        }

        public static class GamesBean {
            /**
             * id : 334655
             * created_at : 2017-08-16 11:39:57
             * player_id : 40733
             * player : {"id":40733,"is_admin":0,"nickname":"小艺欣 -","avatar":""}
             * video_url : http://wwj.same.com/room/p123r1s334655-e2cebc32.mp4
             * state : 抓到了娃娃
             */

            private int id;
            private String created_at;
            private PlayerBean player;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public PlayerBean getPlayer() {
                return player;
            }

            public void setPlayer(PlayerBean player) {
                this.player = player;
            }

            public static class PlayerBean {
                /**
                 * id : 40733
                 * is_admin : 0
                 * nickname : 小艺欣 -
                 * avatar :
                 */

                private int id;
                private String nickname;
                private String avatar;

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
            }
        }
    }
}
