package com.renren.wawa.model;

import java.util.List;

public class RoomListBean extends BaseObject {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private List<RoomsBean> roomList;

        public List<RoomsBean> getRoomList() {
            return roomList;
        }

        public void setRoomList(List<RoomsBean> roomList) {
            this.roomList = roomList;
        }

        public static class RoomsBean {
            /**
             * id : 68
             * cover : https://wwj.same.com/room/cover/vykf7bq3gko.jpg
             * crawled_times : 0
             * machine_token : ma:89
             * price_in_fen : 44
             * name : 变装恐龙布朗熊
             * type : wawajimachine
             * state : 0
             * live_stream : {"room_id":68,"master_user_id":"wawajimachine_master_68","slave01_user_id":"wawajimachine_slave01_68"}
             */

            private int id;
            private String imgUrl;
            private int coin;
            private String name;
            private LiveStreamBean live;
            private int status;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

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

            public LiveStreamBean getLive() {
                return live;
            }

            public void setLive(LiveStreamBean live) {
                this.live = live;
            }

            public static class LiveStreamBean {
                /**
                 * room_id : 68
                 * master_user_id : wawajimachine_master_68
                 * slave01_user_id : wawajimachine_slave01_68
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
    }
}