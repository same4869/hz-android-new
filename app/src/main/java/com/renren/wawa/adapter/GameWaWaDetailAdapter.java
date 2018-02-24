package com.renren.wawa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.renren.wawa.R;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.UserWalletFlowBean;
import com.renren.wawa.utils.AppUtil;
import com.renren.wawa.utils.BBLog;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *  游戏中娃娃详情
 */

public class GameWaWaDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    private Context context;

    public GameWaWaDetailAdapter(List<String> data, Context context) {
        super(R.layout.description_item, data);
        this.context = context;
        BBLog.e(Constants.TAG,"data "+data.toString());
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final String imgUrl) {

        ImageView imageView = baseViewHolder.getView(R.id.description_image);
        ViewGroup.LayoutParams para = imageView.getLayoutParams();
        int screenWeight = AppUtil.getScreenWidth(WawaApplication.getInstance()) - AppUtil.dp2px(20);
        para.width = screenWeight;
        para.height = screenWeight;
        imageView.setLayoutParams(para);
        Picasso.with(context).load(imgUrl+"?imageView2/1/w/300/h/300").fit().into(imageView);
    }



}
