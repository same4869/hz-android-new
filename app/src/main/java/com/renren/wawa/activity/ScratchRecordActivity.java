package com.renren.wawa.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wawaji.vip.R;
import com.renren.wawa.adapter.ScratchRecordAdapter;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.model.UserGameBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.view.CommBeatLoadingView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * 我的抓取记录
 */

public class ScratchRecordActivity extends BaseTitleBarActivity implements BaseQuickAdapter.RequestLoadMoreListener, OnRefreshListener {
    @BindView(R.id.scratch_record_recycler_view)
    RecyclerView scratchRecordRecyclerView;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.scratch_record_layout)
    LinearLayout layout;
    @BindView(R.id.comm_loading)
    CommBeatLoadingView beatLoadingView;

    private ScratchRecordAdapter scratchRecordAdapter;
    private List<UserGameBean.DataBean.ListsBean> sessionsBeanList = new ArrayList<>();
    private static int limit = 1000;
    private int currentPage = 1;
    private boolean refreshFlag = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scratch_record;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        scratchRecordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scratchRecordAdapter = new ScratchRecordAdapter(sessionsBeanList, this);
        scratchRecordRecyclerView.setAdapter(scratchRecordAdapter);
        refreshLayout.setOnRefreshListener(this);
        scratchRecordAdapter.setOnLoadMoreListener(this, scratchRecordRecyclerView);

        getUserGame(currentPage, -1);
    }

    @Override
    public void initData() {

    }

    /**
     * 获取游戏信息
     *
     * @param nextId
     * @param state  按照状态进行过滤, 状态有 0 新建 1 游戏中 2 抓到了娃娃 3 没有抓到娃娃 4 游戏失败
     */
    private void getUserGame(int nextId, int state) {
        HttpMethods.getInstance().getUserGame(0, limit, nextId, state, new Subscriber<UserGameBean>() {
            @Override
            public void onCompleted() {
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(Throwable e) {
                refreshLayout.finishRefresh();
                layout.setVisibility(View.GONE);
                beatLoadingView.setZeroStaticBackground(R.mipmap.comm_net_error_bg, "网络请求失败，请稍后重试");
            }

            @Override
            public void onNext(UserGameBean userGameBean) {
                if (userGameBean != null && userGameBean.isSucceed()) {
                    if (refreshFlag) {
                        scratchRecordAdapter.setNewData(userGameBean.getData().getGameList());
                    } else {
                        scratchRecordAdapter.addData(userGameBean.getData().getGameList());
                    }
                    scratchRecordAdapter.loadMoreComplete();
//                    if (userGameBean.getData().getPage() == null) {////数据全部加载完毕
//                        scratchRecordAdapter.loadMoreEnd();
//                    } else {
//                        currentPage = userGameBean.getData().getPage().getNext_id();
//                    }
                    if (userGameBean.getData().getGameList().size() == 0) {
                        layout.setVisibility(View.GONE);
                        beatLoadingView.setZeroStaticBackground(R.mipmap.no_data_pic, "暂无数据");
                    } else {
                        beatLoadingView.endLoading(true);
                        layout.setVisibility(View.VISIBLE);
                        currentPage++;
                    }
                    scratchRecordAdapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        refreshFlag = false;
        getUserGame(currentPage, -1);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshFlag = true;
        currentPage = 1;
        getUserGame(currentPage, -1);
    }

}
