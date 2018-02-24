package com.renren.wawa.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renren.wawa.R;
import com.renren.wawa.adapter.GameWaWaDetailAdapter;
import com.renren.wawa.adapter.ListInviteAdapter;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.ListInviteBean;
import com.renren.wawa.model.RoomInfoBean;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.view.CommBeatLoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 邀请码列表页面
 */

public class GameWaWaDetailFragment extends BaseFragment {


    @BindView(R.id.game_recycler_view)
    RecyclerView gameRecyclerView;
    @BindView(R.id.comm_loading)
    CommBeatLoadingView beatLoadingView;
    Unbinder unbinder;
    private GameWaWaDetailAdapter gameWaWaDetailAdapter;
    private List<String> datas = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_game_scratch_success;
    }

    @Override
    public void lazyFetchData() {
        gameWaWaDetailAdapter.setNewData(datas);
        if(datas==null||datas.size() == 0){
            beatLoadingView.setVisibility(View.VISIBLE);
            beatLoadingView.setZeroStaticBackground(R.mipmap.no_data_pic, "暂无数据");
        }else{
            beatLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViews() {
        BBLog.d(Constants.TAG, "ListInviteFragment initViews");
        gameRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        gameWaWaDetailAdapter = new GameWaWaDetailAdapter(datas, getActivity());
        gameRecyclerView.setAdapter(gameWaWaDetailAdapter);
        if(datas==null||datas.size() == 0){
            beatLoadingView.setVisibility(View.VISIBLE);
            beatLoadingView.setZeroStaticBackground(R.mipmap.no_data_pic, "暂无数据");
        }else{
            beatLoadingView.setVisibility(View.GONE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    public void setData(List<String> datas) {
            this.datas = datas;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
