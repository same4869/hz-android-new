package com.renren.wawa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wawaji.vip.R;
import com.renren.wawa.model.RoomInfoBean;
import com.renren.wawa.model.UserWalletFlowBean;
import com.renren.wawa.utils.CircleTransform;
import com.renren.wawa.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 游戏中最近抓取成功记录
 */

public class GameRecentScratchSuccessAdapter extends BaseQuickAdapter<RoomInfoBean.DataBean.GamesBean, BaseViewHolder> {


    private Context context;

    public GameRecentScratchSuccessAdapter(List<RoomInfoBean.DataBean.GamesBean> data, Context context) {
        super(R.layout.session_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final RoomInfoBean.DataBean.GamesBean gamesBean) {
        if(StringUtil.isNotBlank(gamesBean.getPlayer().getAvatar())){
            Picasso.with(context).load(gamesBean.getPlayer().getAvatar()).transform(new CircleTransform()).into((ImageView) baseViewHolder.getView(R.id.session_avatar));
        }else{
            Picasso.with(context).load(R.mipmap.ic_launcher).into((ImageView) baseViewHolder.getView(R.id.session_avatar));
        }
        baseViewHolder.setText(R.id.session_nickname,gamesBean.getPlayer().getNickname());
        baseViewHolder.setText(R.id.session_time,gamesBean.getCreated_at());
    }



}
