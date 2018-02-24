package com.renren.wawa.fragment;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.inputmethod.InputMethodManager;

import com.renren.wawa.R;
import com.renren.wawa.adapter.InvitePageAdapter;
import com.renren.wawa.config.Constants;
import com.renren.wawa.utils.BBLog;

import java.util.ArrayList;

import butterknife.BindView;

public class InviteFragment extends BaseFragment {
    @BindView(R.id.invite_tabs)
    TabLayout inviteTabs;
    @BindView(R.id.invite_viewpager)
    ViewPager inviteViewpager;

    private InvitePageAdapter adapter;
    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("分享邀请码");
        add("输入邀请码");
        add("邀请码列表");
    }};

    private ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>() {{
        add(new ShareInviteFragment());
        add(new InputInviteFragment());
        add(new ListInviteFragment());
    }};

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    public void lazyFetchData() {

    }

    @Override
    public void initViews() {
        BBLog.d(Constants.TAG, "InviteFragment initViews");

        adapter = new InvitePageAdapter(getChildFragmentManager(), titleList, fragmentList);
        inviteViewpager.setAdapter(adapter);
        inviteTabs.setupWithViewPager(inviteViewpager);
//        inviteTabs.setTabsFromPagerAdapter(adapter);
        inviteTabs.setTabMode(TabLayout.MODE_FIXED);
        inviteViewpager.setCurrentItem(0);
        inviteViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(),
                            0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
