package com.renren.wawa.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.renren.wawa.R;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.fragment.InviteFragment;
import com.renren.wawa.fragment.RechargeFragment;
import com.renren.wawa.fragment.RoomFragment;
import com.renren.wawa.fragment.UserFragment;
import com.renren.wawa.utils.AppUtil;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.CommGameDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 主页面容器
 */
public class WawaMainActivity extends BaseTitleBarActivity {
    public static final int CHANGE_ZONE = 19;

    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    private List<Fragment> fragments = new ArrayList<>();
    private long mExitTime;
    private int curTitlebarPosition;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wawa_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        commTitleBarView.setBackIcon(null);

        fragments.add(new RoomFragment());
        fragments.add(new InviteFragment());
        fragments.add(new RechargeFragment());
        fragments.add(new UserFragment());

        showFragment(fragments.get(0));

        initAHBottomNavigation();

        commTitleBarView.setMenuText("");
        commTitleBarView.setMenuIcon(R.mipmap.settings_icon);
        setChangeTitleBar(0);
    }

    @Override
    public void initData() {

    }

    @Override
    public void menuListener(View v) {
        super.menuListener(v);
        switch (curTitlebarPosition) {
            case 0:
                Message message = Message.obtain();
                message.what = CHANGE_ZONE;
                EventBus.getDefault().post(message);
                break;
            case 1:
                break;
            case 2:
                Intent moneyIntent = new Intent(this, SettingMoneyActivity.class);
                startActivity(moneyIntent);
                break;
            case 3:
                Intent settingIntent = new Intent(this, SettingActivity.class);
                startActivity(settingIntent);
                break;
            default:
                break;
        }
    }

    private void initAHBottomNavigation() {
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("房间", R.drawable.bg_main_room_selector);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("邀请", R.drawable.bg_main_invite_selector);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("充值", R.drawable.bg_main_recharge_selector);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("我的", R.drawable.bg_main_user_selector);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.themeColor));
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Set listener
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                setChangeTitleBar(position);
                showFragment(fragments.get(position % 4));
                return true;
            }
        });
    }

    /**
     * 通过position来更改titlebar的状态
     *
     * @param position
     */
    private void setChangeTitleBar(int position) {
        if (position < 0 || position > 3) {
            return;
        }
        curTitlebarPosition = position;
        switch (position) {
            case 0:
//              commTitleBarView.setVisibility(View.VISIBLE);
                commTitleBarView.setTitleBarText("好抓抓娃娃");
                commTitleBarView.setMenuText("");
                commTitleBarView.setMenuIcon(null);
                break;
            case 1:
                commTitleBarView.setTitleBarText("邀请");
//              commTitleBarView.setVisibility(View.GONE);
                commTitleBarView.setMenuText("");
                commTitleBarView.setMenuIcon(null);
                break;
            case 2:
//              commTitleBarView.setVisibility(View.VISIBLE);
                commTitleBarView.setTitleBarText("充值");
                commTitleBarView.setMenuText("明细");
                commTitleBarView.setMenuIcon(null);
                break;
            case 3:
//              commTitleBarView.setVisibility(View.VISIBLE);
                commTitleBarView.setTitleBarText("我的");
                commTitleBarView.setMenuText("");
                commTitleBarView.setMenuIcon(R.mipmap.settings_icon);
                break;
            default:
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commitAllowingStateLoss();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
