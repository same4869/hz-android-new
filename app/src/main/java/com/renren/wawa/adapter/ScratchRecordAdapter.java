package com.renren.wawa.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.renren.wawa.R;
import com.renren.wawa.activity.GameDetailActivity;
import com.renren.wawa.model.UserGameBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.renren.wawa.activity.GameDetailActivity.GAME_ID;


public class ScratchRecordAdapter extends BaseQuickAdapter<UserGameBean.DataBean.ListsBean, BaseViewHolder> {
    private Context context;

    public ScratchRecordAdapter(List<UserGameBean.DataBean.ListsBean> data, Context context) {
        super(R.layout.scratch_record_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final UserGameBean.DataBean.ListsBean sessionsBean) {
        String infoFailed = null;
        baseViewHolder.setText(R.id.session_nickname, sessionsBean.getWawa().getName());
        baseViewHolder.setText(R.id.session_time, sessionsBean.getCreated_at());
        TextView sessionStatus = baseViewHolder.getView(R.id.session_status);

        //0 新建 1 游戏中 2 抓到了娃娃 3 没有抓到娃娃 4 游戏失败
        switch (sessionsBean.getStatus()) {
            case 0:
                baseViewHolder.setText(R.id.session_status, "新建");
                break;
            case 1:
                baseViewHolder.setText(R.id.session_status, "游戏中");
                break;
            case 2:
                baseViewHolder.setText(R.id.session_status, "抓取成功");
                baseViewHolder.setTextColor(R.id.session_status, Color.parseColor("#7ED321"));
                break;
            case 3:
                infoFailed = "抓取失败";
                baseViewHolder.setText(R.id.session_status, infoFailed);
                baseViewHolder.setTextColor(R.id.session_status, Color.parseColor("#9B9B9B"));
                break;
            case 4:
                baseViewHolder.setText(R.id.session_status, "游戏失败");
                break;
            case 5:
                baseViewHolder.setText(R.id.session_status, "游戏失败未扣费");
                break;


        }
        if (sessionsBean.getWawa().getImgUrl() != null) {
            Picasso.with(context).load(sessionsBean.getWawa().getImgUrl().get(0)).into((ImageView) baseViewHolder.getView(R.id.session_avatar));
        }
        final String finalInfoFailed = infoFailed;
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameDetailActivity.class);
                intent.putExtra(GameDetailActivity.TITLE, finalInfoFailed);
                intent.putExtra(GAME_ID, sessionsBean.getId());
                context.startActivity(intent);
            }
        });
    }
}
