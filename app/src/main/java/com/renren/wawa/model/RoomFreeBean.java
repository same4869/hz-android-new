package com.renren.wawa.model;



public class RoomFreeBean extends BaseObject {

    /**
     * data : {"room_id":1}
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
         * room_id : 1
         */

        private int room_id;

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }
    }
}
