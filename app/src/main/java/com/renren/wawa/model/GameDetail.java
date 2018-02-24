package com.renren.wawa.model;

import java.util.List;

public class GameDetail extends BaseObject {
    /**
     * product : {"sku":"wawa_081417_01","name":"变装恐龙布朗熊","images":["https://wwj.same.com/product/image/17utu8wy0nl.jpg"],"refund_price":280}
     * session : {"id":1715108,"video_url":"http://wwj.same.com/room/p143r111s1715108-3eea93c7.mp4","state":"fin_uncrawled","state_text":"抓取失败","created_at":"2017-09-06T05:33:28.000Z","created_ts":1504676008}
     * actions : [{"type":"button","text":"申诉","description":"游戏过程中遇到任何问题请点击申诉按钮反馈","url":"wawaji://claw.same.com/session/1715108?action=appeal"}]
     * reasons : [{"url":"wawaji://claw.same.com/session/1715108?action=refund","description":"游戏中工作人员补货"},{"url":"wawaji://claw.same.com/session/1715108?action=refund","description":"没能成功上机就扣币"},{"url":"wawaji://claw.same.com/session/1715108?action=refund","description":"爪子没反应，无法移动"},{"url":"wawaji://claw.same.com/session/1715108?action=refund","description":"弹出\u201c机器正在维护\u201d等提示后断线"},{"url":"wawaji://claw.same.com/session/1715108?action=refund","description":"侧面摄像头故障（遇到此情况请不要重复上机）"},{"url":"wawaji://claw.same.com/session/1715108?action=refund","description":"摄像头歪到无法使用"},{"url":"wawaji://claw.same.com/session/1715108?action=undetected","description":"娃娃掉进洞口了却显示失败"},{"url":"wawaji://claw.same.com/session/1715108?action=undetected","description":"娃娃被爪子吊在洞口正上方"},{"url":"wawaji://claw.same.com/session/1715108?action=misreport","description":"其他原因"}]
     */
    /**
     * product : {"sku":"wawa_082117_01","name":"河马姆明","images":["https://wwj.same.com/product/image/enxpvd0u0o4.jpg","https://wwj.same.com/product/image/tvxg8gfigxh.jpg"],"refund_price":105}
     * session : {"id":557565,"video_url":"http://wwj.same.com/room/p186r13s557565-17a3f1a6.mp4","state":"fin_crawled","state_text":"抓取成功","created_at":"2017-08-24T05:08:37.000Z","created_ts":1503551317}
     * actions : [{"type":"button","text":"换喵喵币","description":"● 选择兑换喵喵币后将无法再请求发货","url":"wawaji://claw.same.com/product-order-item?action=exchange-to-coin&ids=24909&session_id=557565"},{"type":"button","text":"请求发货","description":"● 娃娃寄存时间为抓取成功后的 15 天，15 天后未申请发货将会自动兑换成相应的喵喵币\n● 当寄存的娃娃数量达到两只或以上时可以请求免运费发货\n● 默认发货快递为圆通，邮寄范围仅支持中国大陆地区","url":"wawaji://claw.same.com/product-order-item?action=request-shipping&ids=24909&session_id=557565"}]
     * product_order_items : [{"id":24909,"quantity":1,"state_text":"寄存中"}]
     * product_order : {"id":null,"state_text":"寄存中","address":{}}
     */
    private ProductBean product;// 对应的商品信息
    private SessionBean session;// 游戏记录信息
    private ProductOrderBean product_order;// 对应的订单项
    private List<ActionsBean> actions;
    private List<ProductOrderItemsBean> product_order_items;
    private List<ReasonsBean> reasons;

    public List<ReasonsBean> getReasons() {
        return reasons;
    }

    public void setReasons(List<ReasonsBean> reasons) {
        this.reasons = reasons;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public ProductOrderBean getProduct_order() {
        return product_order;
    }

    public void setProduct_order(ProductOrderBean product_order) {
        this.product_order = product_order;
    }

    public List<ActionsBean> getActions() {
        return actions;
    }

    public void setActions(List<ActionsBean> actions) {
        this.actions = actions;
    }

    public List<ProductOrderItemsBean> getProduct_order_items() {
        return product_order_items;
    }

    public void setProduct_order_items(List<ProductOrderItemsBean> product_order_items) {
        this.product_order_items = product_order_items;
    }

    public static class ProductBean extends BaseObject{
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

    public static class SessionBean extends BaseObject{
        /**
         * id : 557565
         * video_url : http://wwj.same.com/room/p186r13s557565-17a3f1a6.mp4
         * state : fin_crawled
         * state_text : 抓取成功
         * created_at : 2017-08-24T05:08:37.000Z
         * created_ts : 1503551317
         */

        private int id;
        private String video_url;
        private String state;
        private String state_text;
        private String created_at;
        private int created_ts;

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
    }

    public static class ProductOrderBean extends BaseObject{
        /**
         * id : 8164
         * state_text : 已发货
         * category : shipping
         * address : {"recipent_address":"上海 杨浦 |三门路485弄","recipent_name":"张启露","recipent_mobile":"13764354191"}
         * extra : {"shipping_comment":"yuantong","shipping_order_no":"111111111111"}
         */

        private int id;
        private String state_text;
        private String category;
        private AddressBean address;
        private ExtraBean extra;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getState_text() {
            return state_text;
        }

        public void setState_text(String state_text) {
            this.state_text = state_text;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public static class AddressBean {
            /**
             * recipent_address : 上海 杨浦 |三门路485弄
             * recipent_name : 张启露
             * recipent_mobile : 13764354191
             */

            private String recipent_address;
            private String recipent_name;
            private String recipent_mobile;

            public String getRecipent_address() {
                return recipent_address;
            }

            public void setRecipent_address(String recipent_address) {
                this.recipent_address = recipent_address;
            }

            public String getRecipent_name() {
                return recipent_name;
            }

            public void setRecipent_name(String recipent_name) {
                this.recipent_name = recipent_name;
            }

            public String getRecipent_mobile() {
                return recipent_mobile;
            }

            public void setRecipent_mobile(String recipent_mobile) {
                this.recipent_mobile = recipent_mobile;
            }
        }


        public static class ExtraBean {
            /**
             * remark : 咯你永远图投资
             */
            private String remark;
            private String shipping_comment;
            private String shipping_order_no;

            public String getShipping_comment() {
                return shipping_comment;
            }

            public void setShipping_comment(String shipping_comment) {
                this.shipping_comment = shipping_comment;
            }

            public String getShipping_order_no() {
                return shipping_order_no;
            }

            public void setShipping_order_no(String shipping_order_no) {
                this.shipping_order_no = shipping_order_no;
            }



            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }

    public static class ActionsBean extends BaseObject{
        /**
         * type : button
         * text : 换喵喵币
         * description : ● 选择兑换喵喵币后将无法再请求发货
         * url : wawaji://claw.same.com/product-order-item?action=exchange-to-coin&ids=24909&session_id=557565
         */

        private String type;
        private String text;
        private String description;
        private String url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ProductOrderItemsBean extends BaseObject{
        /**
         * id : 24909
         * quantity : 1
         * state_text : 寄存中
         */

        private int id;
        private int quantity;
        private String state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    public static class ReasonsBean extends BaseObject{
        /**
         * url : wawaji://claw.same.com/session/1715108?action=refund
         * description : 游戏中工作人员补货
         */

        private String url;
        private String description;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


}
