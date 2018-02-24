package com.renren.wawa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wawaji.vip.R;
import com.renren.wawa.activity.GameDetailActivity;
import com.renren.wawa.activity.ScratchVideoActivity;
import com.renren.wawa.model.UserGameBean;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.ViewUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.renren.wawa.activity.GameDetailActivity.GAME_ID;
import static com.renren.wawa.activity.ScratchVideoActivity.VIDEO_URL;


public class ScratchSuccessAdapter extends BaseQuickAdapter<UserGameBean.DataBean.ListsBean, BaseViewHolder> {


    private Context context;
    private boolean isMe;

    public ScratchSuccessAdapter(List<UserGameBean.DataBean.ListsBean> data, Context context, boolean isMe) {
        super(R.layout.scratch_success_item, data);
        this.context = context;
        this.isMe = isMe;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final UserGameBean.DataBean.ListsBean recentSessionsBean) {

        if (baseViewHolder.getAdapterPosition() % 2 == 0) {
            ViewUtil.setViewMargin(baseViewHolder.itemView, true, 0, 10, 0, 20);
        } else if (baseViewHolder.getAdapterPosition() % 2 == 1) {
            ViewUtil.setViewMargin(baseViewHolder.itemView, true, 10, 0, 0, 20);
        }
        baseViewHolder.setText(R.id.session_nickname, recentSessionsBean.getWawa().getName());
        baseViewHolder.setText(R.id.session_time, recentSessionsBean.getCreated_at());

        //baseViewHolder.setText(R.id.session_time, TimeUtil.stampToDate(TimeUtil.dateToStamp(recentSessionsBean.getCreated_at())));
        BBLog.e(recentSessionsBean.getCreated_at());
        if (recentSessionsBean.getWawa().getImgUrl() != null) {
            Picasso.with(context).load(recentSessionsBean.getWawa().getImgUrl().get(0)).into((ImageView) baseViewHolder.getView(R.id.session_avatar));
        }
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//查看详细记录
                if (isMe) {
                    Intent intent = new Intent(context, GameDetailActivity.class);
                    intent.putExtra(GAME_ID, recentSessionsBean.getId());
                    context.startActivity(intent);
                }

            }
        });

        baseViewHolder.getView(R.id.session_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//观看录像
//                Intent intent = new Intent(context, ScratchVideoActivity.class);
//                intent.putExtra(VIDEO_URL, recentSessionsBean.getVideo_url());
//                context.startActivity(intent);
            }
        });
    }
}
