package com.renren.wawa.model;

import java.util.List;

public class ScratchRecord extends BaseObject {

    /**
     * sessions : [{"id":173,"video_url":"http://wwj.same.com/room/p92r1002s173-d77d82ac.mp4","state":"fin_uncrawled","state_text":"抓取失败","created_at":"2017-08-29T06:48:03.000Z","created_ts":1503989283,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":172,"video_url":"http://wwj.same.com/room/p92r1002s172-372312a9.mp4","state":"fin_uncrawled","state_text":"抓取失败","created_at":"2017-08-29T06:47:34.000Z","created_ts":1503989254,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":171,"video_url":"http://wwj.same.com/room/p92r1002s171-05672246.mp4","state":"fin_crawled","state_text":"抓取成功","created_at":"2017-08-29T06:46:19.000Z","created_ts":1503989179,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":170,"video_url":"http://wwj.same.com/room/p92r1002s170-26580217.mp4","state":"fin_uncrawled","state_text":"抓取失败","created_at":"2017-08-29T06:10:01.000Z","created_ts":1503987001,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":169,"video_url":"http://wwj.same.com/room/p92r1002s169-6e008b98.mp4","state":"fin_uncrawled","state_text":"抓取失败","created_at":"2017-08-29T06:09:28.000Z","created_ts":1503986968,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":168,"video_url":"http://wwj.same.com/room/p92r1002s168-bf65060a.mp4","state":"fin_uncrawled","state_text":"抓取失败","created_at":"2017-08-29T06:08:06.000Z","created_ts":1503986886,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":167,"video_url":"http://wwj.same.com/room/p92r1002s167-3d4b8485.mp4","state":"fin_uncrawled","state_text":"抓取失败","created_at":"2017-08-29T06:07:38.000Z","created_ts":1503986858,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":166,"video_url":"http://wwj.same.com/room/p92r1002s166-7678a47d.mp4","state":"fin_uncrawled","state_text":"抓取失败","created_at":"2017-08-28T13:59:44.000Z","created_ts":1503928784,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":165,"video_url":"http://wwj.same.com/room/p92r1002s165-b47afd12.mp4","state":"fin_crawled","state_text":"抓取成功","created_at":"2017-08-28T10:24:15.000Z","created_ts":1503915855,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":164,"video_url":"http://wwj.same.com/room/p92r1002s164-02a9705a.mp4","state":"fin_crawled","state_text":"抓取成功","created_at":"2017-08-28T10:23:48.000Z","created_ts":1503915828,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":163,"video_url":"http://wwj.same.com/room/p92r1002s163-6d99dc8b.mp4","state":"fin_uncrawled","state_text":"抓取失败","created_at":"2017-08-28T10:22:35.000Z","created_ts":1503915755,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}},{"id":162,"video_url":"http://wwj.same.com/room/p92r1002s162-3e410c28.mp4","state":"fin_uncrawled","state_text":"抓取失败","created_at":"2017-08-28T10:22:18.000Z","created_ts":1503915738,"product":{"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}}]
     * pages : {"next_id":148,"limit":20}
     */

    private PagesBean pages;
    private List<SessionsBean> sessions;

    public PagesBean getPages() {
        return pages;
    }

    public void setPages(PagesBean pages) {
        this.pages = pages;
    }

    public List<SessionsBean> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionsBean> sessions) {
        this.sessions = sessions;
    }

    public static class PagesBean {
        /**
         * next_id : 148
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

    public static class SessionsBean {
        /**
         * id : 173
         * video_url : http://wwj.same.com/room/p92r1002s173-d77d82ac.mp4
         * state : fin_uncrawled
         * state_text : 抓取失败
         * created_at : 2017-08-29T06:48:03.000Z
         * created_ts : 1503989283
         * product : {"sku":"wawa_073017_05","name":"可妮兔收纳盒","images":["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"],"refund_price":143}
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
             * sku : wawa_073017_05
             * name : 可妮兔收纳盒
             * images : ["https://wwj.same.com/product/image/pa8lpvyrxw.jpg"]
             * refund_price : 143
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
