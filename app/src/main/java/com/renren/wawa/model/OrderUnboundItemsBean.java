package com.renren.wawa.model;

import java.util.List;

public class OrderUnboundItemsBean extends BaseObject {

    /**
     * data : {"product_order_items":[{"id":489,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_270617_01","name":"草莓巧克力泰迪熊","images":["http://wwj.same.com/product/image/wawa_160626_room01_cover.jpg","http://wwj.same.com/product/image/wawa_160626_room01_01.jpg","http://wwj.same.com/product/image/wawa_160626_room01_02.jpg"],"description":[]}},{"id":488,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_270617_01","name":"草莓巧克力泰迪熊","images":["http://wwj.same.com/product/image/wawa_160626_room01_cover.jpg","http://wwj.same.com/product/image/wawa_160626_room01_01.jpg","http://wwj.same.com/product/image/wawa_160626_room01_02.jpg"],"description":[]}},{"id":487,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_02","name":"巴巴爸爸","images":["http://wwj.same.com/product/image/wawa_160622_room05_01.JPG","http://wwj.same.com/product/image/wawa_160622_room05_02.JPG","http://wwj.same.com/product/image/wawa_160622_room05_03.JPG"],"description":[]}},{"id":486,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_02","name":"巴巴爸爸","images":["http://wwj.same.com/product/image/wawa_160622_room05_01.JPG","http://wwj.same.com/product/image/wawa_160622_room05_02.JPG","http://wwj.same.com/product/image/wawa_160622_room05_03.JPG"],"description":[]}},{"id":485,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_02","name":"巴巴爸爸","images":["http://wwj.same.com/product/image/wawa_160622_room05_01.JPG","http://wwj.same.com/product/image/wawa_160622_room05_02.JPG","http://wwj.same.com/product/image/wawa_160622_room05_03.JPG"],"description":[]}},{"id":484,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_02","name":"巴巴爸爸","images":["http://wwj.same.com/product/image/wawa_160622_room05_01.JPG","http://wwj.same.com/product/image/wawa_160622_room05_02.JPG","http://wwj.same.com/product/image/wawa_160622_room05_03.JPG"],"description":[]}},{"id":483,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_01","name":"红色马里奥","images":["http://wwj.same.com/product/image/wawa_160622_room03_cover.jpg"],"description":[]}},{"id":482,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_01","name":"红色马里奥","images":["http://wwj.same.com/product/image/wawa_160622_room03_cover.jpg"],"description":[]}},{"id":481,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_01","name":"红色马里奥","images":["http://wwj.same.com/product/image/wawa_160622_room03_cover.jpg"],"description":[]}},{"id":480,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_270617_02","name":"可爱的蓝孩子","images":["http://wwj.same.com/product/image/wawa_160627_room02_01.jpg","http://wwj.same.com/product/image/wawa_160627_room02_02.jpg"],"description":[]}}],"page":{"next_id":480,"limit":10}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseObject{
        /**
         * product_order_items : [{"id":489,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_270617_01","name":"草莓巧克力泰迪熊","images":["http://wwj.same.com/product/image/wawa_160626_room01_cover.jpg","http://wwj.same.com/product/image/wawa_160626_room01_01.jpg","http://wwj.same.com/product/image/wawa_160626_room01_02.jpg"],"description":[]}},{"id":488,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_270617_01","name":"草莓巧克力泰迪熊","images":["http://wwj.same.com/product/image/wawa_160626_room01_cover.jpg","http://wwj.same.com/product/image/wawa_160626_room01_01.jpg","http://wwj.same.com/product/image/wawa_160626_room01_02.jpg"],"description":[]}},{"id":487,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_02","name":"巴巴爸爸","images":["http://wwj.same.com/product/image/wawa_160622_room05_01.JPG","http://wwj.same.com/product/image/wawa_160622_room05_02.JPG","http://wwj.same.com/product/image/wawa_160622_room05_03.JPG"],"description":[]}},{"id":486,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_02","name":"巴巴爸爸","images":["http://wwj.same.com/product/image/wawa_160622_room05_01.JPG","http://wwj.same.com/product/image/wawa_160622_room05_02.JPG","http://wwj.same.com/product/image/wawa_160622_room05_03.JPG"],"description":[]}},{"id":485,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_02","name":"巴巴爸爸","images":["http://wwj.same.com/product/image/wawa_160622_room05_01.JPG","http://wwj.same.com/product/image/wawa_160622_room05_02.JPG","http://wwj.same.com/product/image/wawa_160622_room05_03.JPG"],"description":[]}},{"id":484,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_02","name":"巴巴爸爸","images":["http://wwj.same.com/product/image/wawa_160622_room05_01.JPG","http://wwj.same.com/product/image/wawa_160622_room05_02.JPG","http://wwj.same.com/product/image/wawa_160622_room05_03.JPG"],"description":[]}},{"id":483,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_01","name":"红色马里奥","images":["http://wwj.same.com/product/image/wawa_160622_room03_cover.jpg"],"description":[]}},{"id":482,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_01","name":"红色马里奥","images":["http://wwj.same.com/product/image/wawa_160622_room03_cover.jpg"],"description":[]}},{"id":481,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_220617_01","name":"红色马里奥","images":["http://wwj.same.com/product/image/wawa_160622_room03_cover.jpg"],"description":[]}},{"id":480,"quantity":1,"state_text":"未绑定的订单","product":{"sku":"wawa_270617_02","name":"可爱的蓝孩子","images":["http://wwj.same.com/product/image/wawa_160627_room02_01.jpg","http://wwj.same.com/product/image/wawa_160627_room02_02.jpg"],"description":[]}}]
         * page : {"next_id":480,"limit":10}
         */

        private List<ProductBean> wawaList;

        public List<ProductBean> getWawaList() {
            return wawaList;
        }

        public void setWawaList(List<ProductBean> wawaList) {
            this.wawaList = wawaList;
        }

        public static class ProductBean extends BaseObject{
            /**
             * sku : wawa_270617_01
             * name : 草莓巧克力泰迪熊
             * images : ["http://wwj.same.com/product/image/wawa_160626_room01_cover.jpg","http://wwj.same.com/product/image/wawa_160626_room01_01.jpg","http://wwj.same.com/product/image/wawa_160626_room01_02.jpg"]
             * description : []
             */

            private int id;
            private String name;
            private String imgUrl;
            private boolean isChecked;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }
    }
}
