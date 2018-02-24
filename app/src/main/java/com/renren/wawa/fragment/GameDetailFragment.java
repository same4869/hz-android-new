package com.renren.wawa.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wawaji.vip.R;
import com.renren.wawa.adapter.InvitePageAdapter;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.RoomInfoBean;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.view.CommViewPage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GameDetailFragment extends BaseFragment {
    @BindView(R.id.invite_tabs)
    TabLayout inviteTabs;
    @BindView(R.id.comm_viewpager)
    CommViewPage commViewpager;
    Unbinder unbinder;
    private GameScratchSuccessFragment gameScratchSuccessFragment;
    private GameWaWaDetailFragment gameWaWaDetailFragment;
    private RoomInfoBean roomInfoBean;


    private InvitePageAdapter adapter;
    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("抓中记录");
        add("娃娃详情");
    }};

    private ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();


    @Override
    public int getLayoutId() {
        return R.layout.fragment_game_detail;
    }

    @Override
    public void lazyFetchData() {

    }

    @Override
    public void initViews() {
        BBLog.d(Constants.TAG, "InviteFragment initViews");

        adapter = new InvitePageAdapter(getChildFragmentManager(), titleList, fragmentList);
        commViewpager.setAdapter(adapter);
        inviteTabs.setupWithViewPager(commViewpager);
//        inviteTabs.setTabsFromPagerAdapter(adapter);
        inviteTabs.setTabMode(TabLayout.MODE_FIXED);
        commViewpager.setCurrentItem(0);
        commViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //fragmentList.add(new InputInviteFragment());
        gameWaWaDetailFragment = new GameWaWaDetailFragment();
        gameScratchSuccessFragment = new GameScratchSuccessFragment();
        fragmentList.add(gameScratchSuccessFragment);
        fragmentList.add(gameWaWaDetailFragment);


        adapter.notifyDataSetChanged();

    }


    public void setData(RoomInfoBean roomInfoBean) {
      this.roomInfoBean = roomInfoBean;
        gameWaWaDetailFragment.setData(roomInfoBean.getData().getWawa().getImages());
        gameScratchSuccessFragment.setData(roomInfoBean.getData().getGameList());
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
