package com.renren.wawa.model;

public class TransactionsBean extends BaseObject{
    /**
     * id : 1
     * category : 游戏扣费
     * remark : 游戏扣费：19
     * amount : 10
     * state : 支付成功
     * created_at : 2017-06-06T15:54:26.905Z
     */

    private String id;
    private String category;
    private String remark;
    private int amount;
    private String state;
    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "TransactionsBean{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", remark='" + remark + '\'' +
                ", amount=" + amount +
                ", state='" + state + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
