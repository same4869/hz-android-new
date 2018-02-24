package com.renren.wawa.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.renren.wawa.R;
import com.renren.wawa.model.UserWalletFlowBean;

import java.util.List;

/**
 * 消费账单明细
 */

public class AccountBillAdapter extends BaseQuickAdapter<UserWalletFlowBean.DataBean.ListsBean, BaseViewHolder> {


    private Context context;

    public AccountBillAdapter(List<UserWalletFlowBean.DataBean.ListsBean> data, Context context) {
        super(R.layout.account_bill_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final UserWalletFlowBean.DataBean.ListsBean transactionsBean) {
        baseViewHolder.setText(R.id.account_bill_content,transactionsBean.getRemark());
        //baseViewHolder.setText(R.id.account_bill_time, TimeUtil.stampToDate(TimeUtil.dateToStamp(transactionsBean.getCreated_at())));
        baseViewHolder.setText(R.id.account_bill_time, transactionsBean.getCreated_at());

        int amount = transactionsBean.getAmount();
        if(amount<=0){
            baseViewHolder.setText(R.id.account_bill_money,""+amount);
            baseViewHolder.setTextColor(R.id.account_bill_money, Color.RED);
        }else{
            baseViewHolder.setText(R.id.account_bill_money,"+"+amount);
            baseViewHolder.setTextColor(R.id.account_bill_money, Color.GREEN);
        }
    }



}
