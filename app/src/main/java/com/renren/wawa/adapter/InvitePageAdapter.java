package com.renren.wawa.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.renren.wawa.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * 邀请页面的adapter
 */

public class InvitePageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> titleList;
    private ArrayList<BaseFragment> fragmentList;

    public InvitePageAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<BaseFragment> fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

}
