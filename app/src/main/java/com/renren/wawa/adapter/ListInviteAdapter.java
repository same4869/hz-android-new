package com.renren.wawa.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.renren.wawa.R;
import com.renren.wawa.model.ListInviteBean;
import com.renren.wawa.model.RoomListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 邀请码列表adapter
 */

public class ListInviteAdapter extends BaseQuickAdapter<ListInviteBean.DataBean.ListBean, BaseViewHolder> {
    private Context context;

    public ListInviteAdapter(@Nullable List<ListInviteBean.DataBean.ListBean> data, Context context) {
        super(R.layout.list_invite_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ListInviteBean.DataBean.ListBean roomsBean) {
        baseViewHolder.setText(R.id.list_invite_name, roomsBean.getNickname());
        baseViewHolder.setText(R.id.list_invite_item_tv, roomsBean.getReward_coin_num() + "喵喵币");
        baseViewHolder.setText(R.id.list_invite_item_time, roomsBean.getCreated_at());
        if (roomsBean.getAvatar() != null) {
            Picasso.with(context).load(roomsBean.getAvatar()).into((ImageView) baseViewHolder.getView(R.id.list_invite_img));
        }
    }
}
