package com.renren.wawa.model;

import java.util.List;

public class CheckoutShipping extends BaseObject {

    /**
     * product_order : {"id":1,"state_text":"待处理","type":"shipping","address":{"recipent_mobile":"13800138000","recipent_address":"foo","recipent_name":"bar"}}
     * product_order_items : [{"id":1,"quantity":1,"state_text":"待处理","product":{"sku":"product:1","name":"测试商品","description":"隔壁家留下来的原味洋娃娃","images":["http://exmaple.com/image.png"]}}]
     */

    private ProductOrderBean product_order;
    private List<ProductOrderItemsBean> product_order_items;

    public ProductOrderBean getProduct_order() {
        return product_order;
    }

    public void setProduct_order(ProductOrderBean product_order) {
        this.product_order = product_order;
    }

    public List<ProductOrderItemsBean> getProduct_order_items() {
        return product_order_items;
    }

    public void setProduct_order_items(List<ProductOrderItemsBean> product_order_items) {
        this.product_order_items = product_order_items;
    }

    public static class ProductOrderBean {
        /**
         * id : 1
         * state_text : 待处理
         * type : shipping
         * address : {"recipent_mobile":"13800138000","recipent_address":"foo","recipent_name":"bar"}
         */

        private int id;
        private String state_text;
        private String type;
        private AddressBean address;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public static class AddressBean {
            /**
             * recipent_mobile : 13800138000
             * recipent_address : foo
             * recipent_name : bar
             */

            private String recipent_mobile;
            private String recipent_address;
            private String recipent_name;

            public String getRecipent_mobile() {
                return recipent_mobile;
            }

            public void setRecipent_mobile(String recipent_mobile) {
                this.recipent_mobile = recipent_mobile;
            }

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
        }
    }

    public static class ProductOrderItemsBean {
        /**
         * id : 1
         * quantity : 1
         * state_text : 待处理
         * product : {"sku":"product:1","name":"测试商品","description":"隔壁家留下来的原味洋娃娃","images":["http://exmaple.com/image.png"]}
         */

        private int id;
        private int quantity;
        private String state_text;
        private ProductBean product;

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

        public String getState_text() {
            return state_text;
        }

        public void setState_text(String state_text) {
            this.state_text = state_text;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public static class ProductBean {
            /**
             * sku : product:1
             * name : 测试商品
             * description : 隔壁家留下来的原味洋娃娃
             * images : ["http://exmaple.com/image.png"]
             */

            private String sku;
            private String name;
            private String description;
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
