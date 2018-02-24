package com.renren.wawa.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.wawaji.vip.R;
import com.renren.wawa.adapter.ListInviteAdapter;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.ListInviteBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.view.CommBeatLoadingView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * 邀请码列表页面
 */

public class ListInviteFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.invite_recycler_view)
    RecyclerView inviteRecyclerView;
    @BindView(R.id.invite_list_refresh)
    RefreshLayout inviteListRefresh;
    @BindView(R.id.comm_loading)
    CommBeatLoadingView beatLoadingView;
    @BindView(R.id.invite_layout)
    LinearLayout layout;

    private ListInviteAdapter listInviteAdapter;
    private List<ListInviteBean.DataBean.ListBean> datas = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list_invite;
    }

    @Override
    public void lazyFetchData() {
        getInviteList();
    }

    @Override
    public void initViews() {
        BBLog.d(Constants.TAG, "ListInviteFragment initViews");
        inviteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listInviteAdapter = new ListInviteAdapter(datas, getActivity());
        inviteRecyclerView.setAdapter(listInviteAdapter);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshLayout) {
        inviteListRefresh.finishRefresh();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        inviteListRefresh.finishRefresh();
    }

    private void getInviteList() {
        beatLoadingView.startLoading();
        HttpMethods.getInstance().getListInvite(new Subscriber<ListInviteBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                layout.setVisibility(View.GONE);
                beatLoadingView.setZeroStaticBackground(R.mipmap.comm_net_error_bg, "网络请求失败，请稍后重试");
            }

            @Override
            public void onNext(ListInviteBean listInviteBean) {
                if (listInviteBean != null && listInviteBean.isSucceed() && listInviteBean.getData() != null) {
                    datas = listInviteBean.getData().getList();
                    listInviteAdapter.setNewData(datas);
                    if (listInviteBean.getData().getList().size() == 0) {
                        layout.setVisibility(View.GONE);
                        beatLoadingView.setZeroStaticBackground(R.mipmap.no_data_pic, "暂无数据");
                    } else {
                        beatLoadingView.endLoading(true);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}
