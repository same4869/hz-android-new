package com.renren.wawa.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wawaji.vip.R;
import com.renren.wawa.model.UserAddressBean;

import java.util.List;

public class UserAddressAdapter extends BaseQuickAdapter<UserAddressBean.DataBean, BaseViewHolder> {

    private Context context;

    public UserAddressAdapter(List<UserAddressBean.DataBean> data, Context context) {
        super(R.layout.adapter_user_address, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final UserAddressBean.DataBean addressesBean) {
        baseViewHolder.setText(R.id.user_nickname,addressesBean.getName());
        baseViewHolder.setText(R.id.user_phone,addressesBean.getPhoneNo());
        baseViewHolder.setText(R.id.user_address,addressesBean.getAddress().replace("|", " "));
        baseViewHolder.addOnClickListener(R.id.modify_address_txt);
        if(baseViewHolder.getAdapterPosition() == (this.getData().size()-1)){
            baseViewHolder.getView(R.id.bottom_view).setVisibility(View.GONE);
        }else{
            baseViewHolder.getView(R.id.bottom_view).setVisibility(View.VISIBLE);
        }


    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(UserAddressBean.DataBean addressesBean, View view);}
}
