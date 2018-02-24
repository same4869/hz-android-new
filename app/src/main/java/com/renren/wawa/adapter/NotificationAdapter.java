package com.renren.wawa.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.renren.wawa.R;
import com.renren.wawa.model.UserNotificationBean;
import com.renren.wawa.utils.StringUtil;


import java.util.List;

/**
 */

public class NotificationAdapter extends BaseQuickAdapter<UserNotificationBean.DataBean.ListsBean, BaseViewHolder> {


    private Context context;

    public NotificationAdapter(List<UserNotificationBean.DataBean.ListsBean> data, Context context) {
        super(R.layout.adapter_notification_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final UserNotificationBean.DataBean.ListsBean sessionsBean) {
        baseViewHolder.setText(R.id.notification_time_tv, sessionsBean.getCreated_at());
        baseViewHolder.setText(R.id.notification_myself_content_tv, sessionsBean.getText());
        baseViewHolder.getView(R.id.notification_myself_content_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = sessionsBean.getUrl();
                if (StringUtil.isNotBlank(url)) {
                    Uri uri = Uri.parse(url);
                    Intent notificationIntent = new Intent(Intent.ACTION_VIEW,
                            uri);
                    context.startActivity(notificationIntent);
                }

            }
        });
    }


}
