package com.renren.wawa.model;

import java.util.List;

public class UserGameBean extends BaseObject {

    /**
     * data : {"lists":[{"id":344154,"created_at":"2017-08-16 19:16:41","state":1,"video_url":"http://wwj.same.com/room/p145r28s344154-3d717a69.mp4","product":{"name":"比你可爱的单身狗","sku":"wawa_081017_02","refund_price":105,"images":["https://wwj.same.com/product/image/5ymvlj0u9r5.jpg","https://wwj.same.com/product/image/4d7oh2sqyff.jpeg","https://wwj.same.com/product/image/euu4j7txz45.jpeg"],"description":""}}],"page":{"next_id":273859,"limit":20}}
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
         * lists : [{"id":344154,"created_at":"2017-08-16 19:16:41","state":1,"video_url":"http://wwj.same.com/room/p145r28s344154-3d717a69.mp4","product":{"name":"比你可爱的单身狗","sku":"wawa_081017_02","refund_price":105,"images":["https://wwj.same.com/product/image/5ymvlj0u9r5.jpg","https://wwj.same.com/product/image/4d7oh2sqyff.jpeg","https://wwj.same.com/product/image/euu4j7txz45.jpeg"],"description":""}}]
         * page : {"next_id":273859,"limit":20}
         */

        private List<ListsBean> gameList;

        public List<ListsBean> getGameList() {
            return gameList;
        }

        public void setGameList(List<ListsBean> gameList) {
            this.gameList = gameList;
        }

        public static class PageBean {
            /**
             * next_id : 273859
             * limit : 20
             */

            private int next_id;
            private int limit;

            public int getNext_id() {
                return next_id;
            }

            public void setNext_id(int next_id) {
                this.next_id = next_id;
            }

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }
        }

        public static class ListsBean {
            /**
             * id : 344154
             * created_at : 2017-08-16 19:16:41
             * state : 1
             * video_url : http://wwj.same.com/room/p145r28s344154-3d717a69.mp4
             * product : {"name":"比你可爱的单身狗","sku":"wawa_081017_02","refund_price":105,"images":["https://wwj.same.com/product/image/5ymvlj0u9r5.jpg","https://wwj.same.com/product/image/4d7oh2sqyff.jpeg","https://wwj.same.com/product/image/euu4j7txz45.jpeg"],"description":""}
             */

            private int id;
            private String created_at;
            private int status;
            private ProductBean wawa;

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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public ProductBean getWawa() {
                return wawa;
            }

            public void setWawa(ProductBean wawa) {
                this.wawa = wawa;
            }

            public static class ProductBean {
                /**
                 * name : 比你可爱的单身狗
                 * sku : wawa_081017_02
                 * refund_price : 105
                 * images : ["https://wwj.same.com/product/image/5ymvlj0u9r5.jpg","https://wwj.same.com/product/image/4d7oh2sqyff.jpeg","https://wwj.same.com/product/image/euu4j7txz45.jpeg"]
                 * description :
                 */

                private String name;
                private int refund_coin;
                private List<String> imgUrl;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getRefund_coin() {
                    return refund_coin;
                }

                public void setRefund_coin(int refund_coin) {
                    this.refund_coin = refund_coin;
                }

                public List<String> getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(List<String> imgUrl) {
                    this.imgUrl = imgUrl;
                }
            }
        }
    }
}
