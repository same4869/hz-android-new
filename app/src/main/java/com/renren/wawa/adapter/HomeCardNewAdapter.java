package com.renren.wawa.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wawaji.vip.R;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.RoomListBean;
import com.renren.wawa.utils.ViewUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页娃娃房间adapter
 */

public class HomeCardNewAdapter extends BaseQuickAdapter<RoomListBean.DataBean.RoomsBean, BaseViewHolder> {

    private Context context;
    private List<RoomListBean.DataBean.RoomsBean> data = new ArrayList<>();
    private final static int idle = 0;//空闲
    private final static int normal = 1;//游戏中
    private final static int offline = 2;//维修


    public HomeCardNewAdapter(Context context, @Nullable List<RoomListBean.DataBean.RoomsBean> data) {
        super(R.layout.home_card_item, data);
        this.context = context;
        this.data = data;
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, RoomListBean.DataBean.RoomsBean room) {
        if (baseViewHolder.getAdapterPosition() == 0) {
            return;
        }

        if (baseViewHolder.getAdapterPosition() % 2 == 1) {
            ViewUtil.setViewMargin(baseViewHolder.itemView, true, 10, 5, 10, 10);
        } else if (baseViewHolder.getAdapterPosition() % 2 == 0) {
            ViewUtil.setViewMargin(baseViewHolder.itemView, true, 5, 10, 10, 10);
        }
        if (Constants.isDebug) {
            baseViewHolder.setText(R.id.room_card_name, "(" + room.getId() + ")" + room.getName());
        } else {
            baseViewHolder.setText(R.id.room_card_name, room.getName());
        }
        baseViewHolder.setText(R.id.room_card_price, Long.toString(room.getCoin()) + "/次");
        switch (room.getStatus()) {
            case idle:
                baseViewHolder.setImageResource(R.id.room_card_icon, R.drawable.room_icon_free_default);
                baseViewHolder.setVisible(R.id.off_line_image, false);
                baseViewHolder.setText(R.id.room_card_status, "空闲中");
                break;
            case normal:
                baseViewHolder.setImageResource(R.id.room_card_icon, R.drawable.room_icon_busy_default);
                baseViewHolder.setVisible(R.id.off_line_image, false);
                baseViewHolder.setText(R.id.room_card_status, "游戏中");
                break;
            case offline:
                baseViewHolder.setImageResource(R.id.room_card_icon, R.drawable.room_icon_repair_default);
                baseViewHolder.setVisible(R.id.off_line_image, true);
                baseViewHolder.setImageResource(R.id.off_line_image, R.mipmap.off_line_mask_img);
                baseViewHolder.setText(R.id.room_card_status, "维护中");
                break;
        }

        Picasso.with(context).load(room.getImgUrl() + "?imageView2/1/w/300/h/300").into((ImageView) baseViewHolder.getView(R.id.room_card_doll));
    }

}
