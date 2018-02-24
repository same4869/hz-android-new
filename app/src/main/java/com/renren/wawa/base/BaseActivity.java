package com.renren.wawa.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.view.CommDialog;
import com.renren.wawa.view.CommLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    private boolean isDestroyed = false;
    protected CommDialog commDialog;
    protected CommLoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.isDebug) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams
                    .FLAG_KEEP_SCREEN_ON);
        }
        WawaApplication.getInstance().pushActivityToCustomStack(this);
        int layoutId = getLayoutId();
        if(layoutId != 0) {
            setContentView(layoutId);
        }
        ButterKnife.bind(this);
        initViews(savedInstanceState);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WawaApplication.getInstance().popActivityFromCustomStack(this);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(WawaApplication.getInstance());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(WawaApplication.getInstance());
    }

    /**
     * 沉浸式状态栏
     */
    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);

        }
    }

    public void showLoadingDialog() {
        showLoadingDialog(null);
    }

    public void showLoadingDialog(String message) {
        showLoadingDialog(message, true);
    }

    public void showLoadingDialog(String message, boolean cancelable) {
        if (isActivityDestroyed()) {
            return;
        }

        cancelLoadingDialog();

        loadingDialog = new CommLoadingDialog(this);
        loadingDialog.show();
        if (message != null) {
            loadingDialog.setContentMessage(message);
        }
        loadingDialog.setCancelable(cancelable);
    }

    public void cancelLoadingDialog() {
        if (isActivityDestroyed()) {
            loadingDialog = null;
            return;
        }
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
        loadingDialog = null;
    }

    public boolean isActivityDestroyed() {
        return isDestroyed;
    }

    @Override
    public void sendBroadcast(Intent intent) {
        sendBroadcast(intent, false);
    }

    public void sendBroadcast(Intent intent, boolean system) {
        if (system) {
            super.sendBroadcast(intent);
        } else {
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }
    }

    public void cancelDialog() {
        if (isActivityDestroyed()) {
            commDialog = null;
            return;
        }
        if (commDialog != null && commDialog.isShowing()) {
            commDialog.dismiss();
        }
        commDialog = null;
    }
}
