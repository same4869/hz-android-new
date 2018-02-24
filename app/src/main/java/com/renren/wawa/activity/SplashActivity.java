package com.renren.wawa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.wawaji.vip.R;
import com.renren.wawa.base.BaseActivity;
import com.renren.wawa.config.Constants;
import com.renren.wawa.manager.GameManager;
import com.renren.wawa.model.RoomUserMessage;
import com.renren.wawa.sp.CommSetting;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;

import commlib.xun.com.commlib.handler.CommWeakHandler;

/**
 * 启动页
 */

public class SplashActivity extends BaseActivity {

    private final int GOTO_NEXT_PAGE_WHAT = 1001;

    private CommWeakHandler handler = new CommWeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == GOTO_NEXT_PAGE_WHAT) {
                if (StringUtil.isNotBlank(CommSetting.getToken())) {
                    GameManager.getInstance().start();
                    Intent intent = new Intent(SplashActivity.this, WawaMainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                    finish();
                }
            }
            return false;
        }
    });

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        handler.sendEmptyMessageDelayed(GOTO_NEXT_PAGE_WHAT, 3000);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
