package com.renren.wawa.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wawaji.vip.R;
import com.renren.wawa.adapter.GameRecentScratchSuccessAdapter;
import com.renren.wawa.adapter.ListInviteAdapter;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.ListInviteBean;
import com.renren.wawa.model.RoomInfoBean;
import com.renren.wawa.utils.BBLog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 邀请码列表页面
 */

public class GameScratchSuccessFragment extends BaseFragment {


    @BindView(R.id.game_recycler_view)
    RecyclerView gameRecyclerView;
    private Unbinder unbinder;
    private GameRecentScratchSuccessAdapter gameRecentScratchSuccessAdapter;
    private List<RoomInfoBean.DataBean.GamesBean> gamesBeans = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_game_scratch_success;
    }

    @Override
    public void lazyFetchData() {
        gameRecentScratchSuccessAdapter.setNewData(gamesBeans);
    }

    @Override
    public void initViews() {
        BBLog.d(Constants.TAG, "ListInviteFragment initViews");
        gameRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        gameRecentScratchSuccessAdapter = new GameRecentScratchSuccessAdapter(gamesBeans, getActivity());
        gameRecyclerView.setAdapter(gameRecentScratchSuccessAdapter);
    }


    public void setData(List<RoomInfoBean.DataBean.GamesBean> gamesBeanList) {
        this.gamesBeans = gamesBeanList;
        if(gameRecentScratchSuccessAdapter!=null){
            gameRecentScratchSuccessAdapter.setNewData(gamesBeans);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
