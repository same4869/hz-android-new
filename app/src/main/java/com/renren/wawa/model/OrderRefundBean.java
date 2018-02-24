package com.renren.wawa.model;

import java.util.List;

public class OrderRefundBean extends BaseObject {


    /**
     * data : {"order":{"id":"7805","state":"已兑币","type":null,"address":null},"items":{"id":21803,"state":"已兑币","product":{"name":"玻尿酸鼠鼠(粉色)","sku":"wawa_081217_01","refund_price":132,"images":["https://wwj.same.com/product/image/63bc8dcbdag.jpg"],"description":""}}}
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
         * order : {"id":"7805","state":"已兑币","type":null,"address":null}
         * items : {"id":21803,"state":"已兑币","product":{"name":"玻尿酸鼠鼠(粉色)","sku":"wawa_081217_01","refund_price":132,"images":["https://wwj.same.com/product/image/63bc8dcbdag.jpg"],"description":""}}
         */

        private OrderBean order;
        private ItemsBean items;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public ItemsBean getItems() {
            return items;
        }

        public void setItems(ItemsBean items) {
            this.items = items;
        }

        public static class OrderBean {
            /**
             * id : 7805
             * state : 已兑币
             * type : null
             * address : null
             */

            private String id;
            private String state;
            private Object type;
            private Object address;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }
        }

        public static class ItemsBean {
            /**
             * id : 21803
             * state : 已兑币
             * product : {"name":"玻尿酸鼠鼠(粉色)","sku":"wawa_081217_01","refund_price":132,"images":["https://wwj.same.com/product/image/63bc8dcbdag.jpg"],"description":""}
             */

            private int id;
            private String state;
            private ProductBean product;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public ProductBean getProduct() {
                return product;
            }

            public void setProduct(ProductBean product) {
                this.product = product;
            }

            public static class ProductBean {
                /**
                 * name : 玻尿酸鼠鼠(粉色)
                 * sku : wawa_081217_01
                 * refund_price : 132
                 * images : ["https://wwj.same.com/product/image/63bc8dcbdag.jpg"]
                 * description :
                 */

                private String name;
                private String sku;
                private int refund_price;
                private String description;
                private List<String> images;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public int getRefund_price() {
                    return refund_price;
                }

                public void setRefund_price(int refund_price) {
                    this.refund_price = refund_price;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
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
