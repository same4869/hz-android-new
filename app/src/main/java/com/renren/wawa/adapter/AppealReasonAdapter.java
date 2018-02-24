package com.renren.wawa.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.renren.wawa.R;
import com.renren.wawa.model.GameDetail;

import java.util.List;

public class AppealReasonAdapter extends BaseQuickAdapter<GameDetail.ReasonsBean, BaseViewHolder> {

    private Boolean[] booleans;

    public AppealReasonAdapter(@Nullable List<GameDetail.ReasonsBean> data) {
        super(R.layout.adapter_appeal_reason, data);
        booleans = new Boolean[data.size()];
        for (int i = 0; i < booleans.length; i++) {
            booleans[i] = false;
        }
    }


    public void updateData(Boolean[] booleans) {
        this.booleans = booleans;
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, GameDetail.ReasonsBean reasonsBean) {

        if (booleans[baseViewHolder.getAdapterPosition()] != null) {
            if (booleans[baseViewHolder.getAdapterPosition()]) {
                baseViewHolder.getView(R.id.select_img).setVisibility(View.VISIBLE);
            } else {
                baseViewHolder.getView(R.id.select_img).setVisibility(View.INVISIBLE);
            }
        }
        baseViewHolder.setText(R.id.appeal_reason_txt, reasonsBean.getDescription());
    }
}

