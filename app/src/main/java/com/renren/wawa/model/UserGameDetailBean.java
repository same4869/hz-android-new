package com.renren.wawa.model;


import java.util.List;

public class UserGameDetailBean extends BaseObject {
    /**
     * data : {"session":{"id":479371,"created_at":"2017-09-15 11:57:44","product_id":130,"state_new":2,"video_url":""},"product":{"name":"玻尿酸鼠鼠(粉色)","sku":"wawa_081217_01","refund_price":132,"imgUrl":["https://wwj.same.com/product/image/63bc8dcbdag.jpg"],"description":""},"action":[{"url":"wawaji://claw.same.com/product-order-item?action=exchange-to-coin&ids=&session_id=479371","type":"button","description":"● 选择兑换喵喵币将失去这个娃娃，得到一定数量的喵喵币","text":"换喵喵币"},{"url":"wawaji://claw.same.com/product-order-item?action=request-shipping&ids=&session_id=479371","type":"button","description":"● 娃娃寄存时间为抓取成功之后的15天，15天后未申请发货，系统将会自动为您将娃娃兑换成相应的喵喵币。\n● 请尽量选择两只及以上数量的娃娃【一起申请发货】，可以免运费哦！\n● 如果您【单独申请】了两只娃娃分别发货，无论地址是否一致，都是不享受免运费发货的哦。\n● 目前仅支持中国大陆地区范围内邮寄。","text":"请求发货"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseObject {
        /**
         * session : {"id":479371,"created_at":"2017-09-15 11:57:44","product_id":130,"state_new":2,"video_url":""}
         * product : {"name":"玻尿酸鼠鼠(粉色)","sku":"wawa_081217_01","refund_price":132,"imgUrl":["https://wwj.same.com/product/image/63bc8dcbdag.jpg"],"description":""}
         * action : [{"url":"wawaji://claw.same.com/product-order-item?action=exchange-to-coin&ids=&session_id=479371","type":"button","description":"● 选择兑换喵喵币将失去这个娃娃，得到一定数量的喵喵币","text":"换喵喵币"},{"url":"wawaji://claw.same.com/product-order-item?action=request-shipping&ids=&session_id=479371","type":"button","description":"● 娃娃寄存时间为抓取成功之后的15天，15天后未申请发货，系统将会自动为您将娃娃兑换成相应的喵喵币。\n● 请尽量选择两只及以上数量的娃娃【一起申请发货】，可以免运费哦！\n● 如果您【单独申请】了两只娃娃分别发货，无论地址是否一致，都是不享受免运费发货的哦。\n● 目前仅支持中国大陆地区范围内邮寄。","text":"请求发货"}]
         */

        private SessionBean game;
        private ProductBean wawa;
        private ProductOrderBean orderInfo;// 对应的订单项
        private int status;
        private String statusText;
        private WawaItemBean wawa_item;

        public WawaItemBean getWawa_item() {
            return wawa_item;
        }

        public void setWawa_item(WawaItemBean wawa_item) {
            this.wawa_item = wawa_item;
        }

        public SessionBean getGame() {
            return game;
        }

        public void setGame(SessionBean game) {
            this.game = game;
        }

        public ProductBean getWawa() {
            return wawa;
        }

        public void setWawa(ProductBean wawa) {
            this.wawa = wawa;
        }

        public ProductOrderBean getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(ProductOrderBean orderInfo) {
            this.orderInfo = orderInfo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }

        public static class WawaItemBean extends BaseObject {
            private int id;
            private String created_at;
            private String state;
            private int order_id;
            private int quantity;

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

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }
        }

        public static class SessionBean extends BaseObject {
            /**
             * id : 479371
             * created_at : 2017-09-15 11:57:44
             * product_id : 130
             * state_new : 2
             * video_url :
             */

            private int id;
            private String created_at;
            private int product_id;
            private int status;

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
        }

        public static class ProductBean extends BaseObject {
            /**
             * name : 玻尿酸鼠鼠(粉色)
             * sku : wawa_081217_01
             * refund_price : 132
             * imgUrl : ["https://wwj.same.com/product/image/63bc8dcbdag.jpg"]
             * description :
             */

            private String name;
            private int refund_coin;
            private String description;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<String> getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(List<String> imgUrl) {
                this.imgUrl = imgUrl;
            }
        }

        public static class ProductOrderBean extends BaseObject {
            private String address;
            private String phoneNo;
            private String name;
            private String expressCompany;
            private String expressOrderNo;
            private int orderNo;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhoneNo() {
                return phoneNo;
            }

            public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getExpressCompany() {
                return expressCompany;
            }

            public void setExpressCompany(String expressCompany) {
                this.expressCompany = expressCompany;
            }

            public String getExpressOrderNo() {
                return expressOrderNo;
            }

            public void setExpressOrderNo(String expressOrderNo) {
                this.expressOrderNo = expressOrderNo;
            }

            public int getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(int orderNo) {
                this.orderNo = orderNo;
            }
        }
    }

}
