package com.renren.wawa.model;

import java.util.List;

/**
 * Created by same on 2017/8/18.
 */

public class UserWalletTransactions extends BaseObject {


    /**
     * transactions : [{"id":211,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-29T06:48:03.000Z"},{"id":210,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-29T06:47:34.000Z"},{"id":209,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-29T06:46:19.000Z"},{"id":208,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-29T06:10:00.000Z"},{"id":207,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-29T06:09:28.000Z"},{"id":206,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-29T06:08:06.000Z"},{"id":205,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-29T06:07:38.000Z"},{"id":204,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-28T13:59:44.000Z"},{"id":203,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-28T10:24:15.000Z"},{"id":202,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-28T10:23:48.000Z"},{"id":201,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-28T10:22:35.000Z"},{"id":200,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-28T10:22:18.000Z"},{"id":199,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-28T10:21:47.000Z"},{"id":198,"category":"消费订单","amount":-60,"remark":"娃娃运费 60","state":"支付成功","created_at":"2017-08-28T10:21:18.000Z"},{"id":197,"category":"退款","amount":143,"remark":"娃娃兑币 143","state":"支付成功","created_at":"2017-08-28T10:00:49.000Z"},{"id":196,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-28T09:58:53.000Z"},{"id":195,"category":"退款","amount":143,"remark":"娃娃兑币 143","state":"支付成功","created_at":"2017-08-28T09:56:54.000Z"},{"id":189,"category":"消费订单","amount":-60,"remark":"娃娃运费 60","state":"支付成功","created_at":"2017-08-28T09:35:02.000Z"},{"id":188,"category":"退款","amount":143,"remark":"娃娃兑币 143","state":"支付成功","created_at":"2017-08-28T09:22:59.000Z"},{"id":187,"category":"游戏扣费","amount":-33,"remark":"房间 可妮兔收纳盒 游戏扣费: 33","state":"支付成功","created_at":"2017-08-28T08:35:15.000Z"}]
     * pages : {"offset":186,"limit":20}
     */

    private PagesBean pages;
    private List<TransactionsBean> transactions;

    public PagesBean getPages() {
        return pages;
    }

    public void setPages(PagesBean pages) {
        this.pages = pages;
    }

    public List<TransactionsBean> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionsBean> transactions) {
        this.transactions = transactions;
    }

    public static class PagesBean {
        /**
         * offset : 186
         * limit : 20
         */

        private int offset;
        private int limit;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }
    }

}
