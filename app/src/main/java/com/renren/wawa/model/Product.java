package com.renren.wawa.model;

import java.util.List;

public class Product extends BaseObject{

    private String sku;

    private String name;

    private List<String> images;

    private int refund_price;

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getRefund_price() {
        return refund_price;
    }

    public void setRefund_price(int refund_price) {
        this.refund_price = refund_price;
    }
}
