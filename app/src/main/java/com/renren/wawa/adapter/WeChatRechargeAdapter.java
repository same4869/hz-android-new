package com.renren.wawa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.renren.wawa.R;
import com.renren.wawa.model.PaySettingBean;

import java.util.List;

/**
 * 微信充值adapter
 */

public class WeChatRechargeAdapter extends BaseQuickAdapter<PaySettingBean.DataBean, BaseViewHolder> {
    private OnItemClickListener onItemClickListener;
    private int curSelectPay = -1;


    public WeChatRechargeAdapter(List<PaySettingBean.DataBean> data, Context context) {
        super(R.layout.adapter_we_chat_recharge_item, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final PaySettingBean.DataBean weixinBean) {
        baseViewHolder.setText(R.id.money_txt, "￥" + weixinBean.getPrice() / 100.0 + "0");
        baseViewHolder.setText(R.id.holl_money_txt, weixinBean.getOriginal_coin() + "");
        baseViewHolder.setText(R.id.holl_money_txt_tip, "+" + weixinBean.getPlus_coin());
        if (!TextUtils.isEmpty(weixinBean.getText())) {
            baseViewHolder.setText(R.id.holl_money_detail, weixinBean.getText());
            baseViewHolder.getView(R.id.holl_money_detail).setVisibility(View.VISIBLE);
        } else {
            baseViewHolder.getView(R.id.holl_money_detail).setVisibility(View.GONE);
        }
//        if(baseViewHolder.getLayoutPosition() == curSelectPay){
//            baseViewHolder.setTextColor(R.id.money_txt, Color.parseColor("#77FFFFFF"));
//        }else{
//            baseViewHolder.setTextColor(R.id.money_txt, Color.parseColor("#FFFFFF"));
//        }

//        if (weixinBean.getIcon().equals("coin-stack") || weixinBean.getIcon().equals("coin-stack1")) {
//            baseViewHolder.setImageResource(R.id.money_iv, R.mipmap.game_currency_1);
//        } else if (weixinBean.getIcon().equals("coin-stack2")) {
//            baseViewHolder.setImageResource(R.id.money_iv, R.mipmap.game_currency_2);
//        } else if (weixinBean.getIcon().equals("coin-stack3")) {
        baseViewHolder.setImageResource(R.id.money_iv, R.mipmap.game_currency_3);
//        }

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    curSelectPay = baseViewHolder.getLayoutPosition();
//                    baseViewHolder.setTextColor(R.id.money_txt, Color.parseColor("#77FFFFFF"));
                    notifyDataSetChanged();
                    onItemClickListener.onItemClickListener(weixinBean, v, curSelectPay);
                }
            }

        });
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(PaySettingBean.DataBean weixinBean, View view, int position);
    }
}
