package com.renren.wawa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wawaji.vip.R;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.config.Constants;
import com.renren.wawa.manager.UserManager;
import com.renren.wawa.sp.CommSetting;
import com.renren.wawa.view.CommDialog;
import com.renren.wawa.view.CommFuctionEntryBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置页面
 */

public class SettingActivity extends BaseTitleBarActivity implements CommFuctionEntryBar.OnSwitchChangeListener {
    @BindView(R.id.settings_background_music)
    CommFuctionEntryBar settingsBackgroundMusic;
    @BindView(R.id.settings_sound_effect)
    CommFuctionEntryBar settingsSoundEffect;
    @BindView(R.id.settings_feedback)
    CommFuctionEntryBar settingsFeedback;
    @BindView(R.id.settings_exchange_service)
    CommFuctionEntryBar settingsExchangeService;
    @BindView(R.id.settings_logout)
    CommFuctionEntryBar settingsLogout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        settingsBackgroundMusic.setSwitch(CommSetting.getSettingBackgroundMusic());
        settingsSoundEffect.setSwitch(CommSetting.getSettingSoundEffect());

        settingsBackgroundMusic.setOnSwitchChangeListener(this);
        settingsSoundEffect.setOnSwitchChangeListener(this);

//        开发者选项
        if(UserManager.getEmailLoginBean().getData()!=null&&UserManager.getEmailLoginBean().getData().getUserInfo()!=null){
            int developerMode = UserManager.getEmailLoginBean().getData().getUserInfo().getIs_staff();
            if(developerMode == 1){
                commTitleBarView.getMenuView().setVisibility(View.VISIBLE);
                commTitleBarView.getMenuView().setText(getString(R.string.developer));
            }else{
                commTitleBarView.getMenuView().setVisibility(View.GONE);
            }
        }
    }


    @OnClick(R.id.settings_feedback)
    public void feedbackBarClick() {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.settings_about)
    public void aboutClick(){
//        Intent intent = new Intent(this, AboutActivity.class);
//        startActivity(intent);
        Intent intent = new Intent(this, CommWebActivity.class);
        intent.putExtra(CommWebActivity.COMM_WEB_URL, Constants.USER_ABOUT_URL);
        intent.putExtra(CommWebActivity.COMM_WEB_TITLE, "关于我们");
        startActivity(intent);
    }

    @OnClick(R.id.settings_logout)
    public void logoutBarClick() {
        commDialog = new CommDialog(this, "", getString(R.string.login_out), false);
        commDialog.setCanceledOnTouchOutside(false);
        commDialog.show();
        commDialog.hideTitle();
        commDialog.setLeftButtonText(getString(R.string.comm_tips_cancel));
        commDialog.setRightButtonText(getString(R.string.comm_tips_confirm));
        commDialog.setLeftButtonPositive(false);
        commDialog.setRightButtonPositive(true);
        commDialog.setRightListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Constants.BROADCAST_LOGOUT);
                sendBroadcast(intent, false);
                cancelDialog();
            }
        });
        commDialog.setLeftListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelDialog();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onSwitchChange(View view, boolean on) {
        switch (view.getId()) {
            case R.id.settings_background_music://背景音乐
                CommSetting.setSettingBackgroundMusic(on);
                break;

            case R.id.settings_sound_effect://音效
                CommSetting.setSettingSoundEffect(on);
                break;
        }
    }
}
