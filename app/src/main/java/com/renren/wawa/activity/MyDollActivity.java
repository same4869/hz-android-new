package com.renren.wawa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.renren.wawa.R;
import com.renren.wawa.adapter.ScratchSuccessAdapter;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.model.UserGameBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.view.CommBeatLoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * 抓住娃娃
 */

public class MyDollActivity extends BaseTitleBarActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.scratch_success_recycler_view)
    RecyclerView scratchSuccessRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.mydoll_layout)
    LinearLayout layout;
    @BindView(R.id.comm_loading)
    CommBeatLoadingView beatLoadingView;

    private ScratchSuccessAdapter scratchSuccessAdapter;
    private List<UserGameBean.DataBean.ListsBean> recentSessionsBeanList = new ArrayList<>();

    private static int limit = 20;
    private int currentPage = 1;
    private boolean refreshFlag = false;
    private String type;
    private long userId = 0;
    private boolean isMe;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_doll;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra("type");
        userId = getIntent().getLongExtra("user_id", 0);
        if (!"other".equals(type)) {
//            commTitleBarView.setMenuText(getResources().getText(R.string.settings));
            isMe = true;
        } else {
            isMe = false;
        }
        scratchSuccessRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        scratchSuccessAdapter = new ScratchSuccessAdapter(recentSessionsBeanList, this, isMe);
        scratchSuccessRecyclerView.setAdapter(scratchSuccessAdapter);
        refreshLayout.setOnRefreshListener(this);
        scratchSuccessAdapter.setOnLoadMoreListener(this, scratchSuccessRecyclerView);
    }

    @Override
    public void initData() {
        getUserGame(currentPage, 2);
    }


    /**
     * 获取游戏信息
     *
     * @param nextId
     * @param state  按照状态进行过滤, 状态有 0 新建 1 游戏中 2 抓到了娃娃 3 没有抓到娃娃 4 游戏失败
     */
    private void getUserGame(int nextId, int state) {
        HttpMethods.getInstance().getUserGame(userId, limit, currentPage, state, new Subscriber<UserGameBean>() {
            @Override
            public void onCompleted() {
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                refreshLayout.setRefreshing(false);
                layout.setVisibility(View.GONE);
                beatLoadingView.setZeroStaticBackground(R.mipmap.comm_net_error_bg, "网络请求失败，请稍后重试");
            }

            @Override
            public void onNext(UserGameBean userGameBean) {
                if (userGameBean != null && userGameBean.isSucceed()) {
                    if (refreshFlag) {
                        scratchSuccessAdapter.setNewData(userGameBean.getData().getGameList());
                    } else {
                        scratchSuccessAdapter.addData(userGameBean.getData().getGameList());
                    }
                    scratchSuccessAdapter.loadMoreComplete();
                    if (userGameBean.getData().getGameList().size() == 0) {
                        if (currentPage == 1) {
                            layout.setVisibility(View.GONE);
                            beatLoadingView.setZeroStaticBackground(R.mipmap.no_data_pic, "暂无数据");
                        }
                    } else {
                        beatLoadingView.endLoading(true);
                        layout.setVisibility(View.VISIBLE);
                        currentPage++;
                    }
                    scratchSuccessAdapter.loadMoreEnd();
                }
            }
        });
    }


    @Override
    public void menuListener(View v) {
        super.menuListener(v);
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        refreshFlag = true;
        currentPage = 1;
        getUserGame(currentPage, 2);

    }

    @Override
    public void onLoadMoreRequested() {
        refreshFlag = false;
        getUserGame(currentPage, 2);
    }
}

