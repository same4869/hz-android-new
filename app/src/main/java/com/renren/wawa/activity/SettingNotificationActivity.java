package com.renren.wawa.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wawaji.vip.R;
import com.renren.wawa.adapter.NotificationAdapter;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.model.UserNotificationBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.view.CommBeatLoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * 通知中心
 */

public class SettingNotificationActivity extends BaseTitleBarActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.notification_recycler_view)
    RecyclerView notificationRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private static int limit = 20;
    @BindView(R.id.comm_loading)
    CommBeatLoadingView commLoading;
    private int currentPage = 0;
    private boolean refreshFlag = false;
    private NotificationAdapter notificationAdapter;
    private List<UserNotificationBean.DataBean.ListsBean> notificationList = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_notification;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter = new NotificationAdapter(notificationList, this);
        notificationRecyclerView.setAdapter(notificationAdapter);
        refreshLayout.setOnRefreshListener(this);
        notificationAdapter.setOnLoadMoreListener(this, notificationRecyclerView);
        getUserNotification(currentPage);
    }

    private void getUserNotification(int nextId) {
        commLoading.startLoading();
        HttpMethods.getInstance().getUserNotifition(nextId, 20, new Subscriber<UserNotificationBean>() {
            @Override
            public void onCompleted() {
                refreshLayout.setRefreshing(false);
                commLoading.endLoading(true);
            }

            @Override
            public void onError(Throwable e) {
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(UserNotificationBean userNotificationBean) {
                if (userNotificationBean != null && userNotificationBean.isSucceed()) {
                    if (refreshFlag) {
                        notificationAdapter.setNewData(userNotificationBean.getData().getLists());
                    } else {
                        notificationAdapter.addData(userNotificationBean.getData().getLists());
                    }
                    notificationAdapter.loadMoreComplete();
                    if (userNotificationBean.getData().getPage() == null) {////数据全部加载完毕
                        notificationAdapter.loadMoreEnd();
                    } else {
                        currentPage = userNotificationBean.getData().getPage().getNext_id();
                    }

                    if (notificationAdapter.getData().size() == 0) {
                        commLoading.setZeroStaticBackground(R.mipmap.no_data_pic, "暂无数据");
                    } else {
                        commLoading.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }


    @Override
    public void onRefresh() {
        refreshFlag = true;
        currentPage = 0;
        getUserNotification(currentPage);

    }

    @Override
    public void onLoadMoreRequested() {
        refreshFlag = false;
        getUserNotification(currentPage);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
