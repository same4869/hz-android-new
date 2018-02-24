package com.renren.wawa.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.renren.wawa.R;
import com.renren.wawa.adapter.AccountBillAdapter;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.UserWalletFlowBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.view.CommBeatLoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * 明细页面
 */

public class SettingMoneyActivity extends BaseTitleBarActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.account_bill_recycler_view)
    RecyclerView accountBillRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.comm_loading)
    CommBeatLoadingView beatLoadingView;

    private AccountBillAdapter accountBillAdapter;
    private List<UserWalletFlowBean.DataBean.ListsBean> transactionsBeanList = new ArrayList<>();

    private static int limit = 1000;
    private int currentPage = 1;
    private boolean refreshFlag = false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_money;
    }

    @Override
    public void initData() {
        showLoadingDialog();
        accountBillRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        accountBillAdapter = new AccountBillAdapter(transactionsBeanList, this);
        accountBillRecyclerView.setAdapter(accountBillAdapter);
        refreshLayout.setOnRefreshListener(this);
        accountBillAdapter.setOnLoadMoreListener(this, accountBillRecyclerView);
        getUserWalletFlow();
    }

    /**
     * 获取用户流水
     *
     */
    private void getUserWalletFlow() {
        HttpMethods.getInstance().getUserWalletFlow(limit, currentPage,
                new Subscriber<UserWalletFlowBean>() {
                    @Override
                    public void onCompleted() {
                        refreshLayout.setRefreshing(false);
                        cancelLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        cancelLoadingDialog();
                    }

                    @Override
                    public void onNext(UserWalletFlowBean userWalletFlowBean) {
                        if (userWalletFlowBean != null && userWalletFlowBean.isSucceed()) {
                            if (refreshFlag) {
                                accountBillAdapter.setNewData(userWalletFlowBean.getData().getLogList());
                            } else {
                                accountBillAdapter.addData(userWalletFlowBean.getData().getLogList());
                            }
                            accountBillAdapter.loadMoreComplete();

                            if (userWalletFlowBean.getData().getLogList().size() == 0) {////数据全部加载完毕
                                if (currentPage == 1) {
                                    refreshLayout.setVisibility(View.GONE);
                                    beatLoadingView.setZeroStaticBackground(R.mipmap.no_data_pic, "暂无数据");
                                }
                            } else {
                                currentPage++;
                            }

                            accountBillAdapter.loadMoreEnd();
                        }
                    }
                });
    }


    @Override
    public void onRefresh() {
        refreshFlag = true;
        currentPage = 1;
        getUserWalletFlow();
    }

    @Override
    public void onLoadMoreRequested() {
        refreshFlag = false;
        getUserWalletFlow();

    }
}
