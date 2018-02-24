package com.renren.wawa.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.renren.wawa.base.BaseWebActivity;
import com.renren.wawa.utils.BBLog;

/**
 * 公共webview页面
 */

public class CommWebActivity extends BaseWebActivity {
    private static final String TAG = CommWebActivity.class.getSimpleName();
    public static final String COMM_WEB_TITLE = "web_title";
    public static final String COMM_WEB_URL = "web_url";
    public static final String COMM_WEB_HIDE_TITLE = "web_hide_title";
    public static final String COMM_WEB_IS_SHOW_MENU = "web_is_show_menu";
    protected String title;

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        title = getIntent().getStringExtra(COMM_WEB_TITLE);
        String url = getIntent().getStringExtra(COMM_WEB_URL);
        boolean isHideTitle = getIntent().getBooleanExtra(COMM_WEB_HIDE_TITLE, false);
        boolean isShowMenu = getIntent().getBooleanExtra(COMM_WEB_IS_SHOW_MENU, false);

        setBarTitle(title);
        if (isHideTitle) {
            commTitleBarView.setVisibility(View.GONE);
        }
        if (!isShowMenu) {
            commTitleBarView.setMenuVisible(View.GONE);
        } else {
            commTitleBarView.setMenuVisible(View.VISIBLE);
        }
        commTitleBarView.setMenu2Visible(View.GONE);

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.requestFocus();
        setWebViewClient();
//        url = url.replace("https","http");
        BBLog.d("kkkkkkkk", "url --> " + url);
        loadUrl(url);
    }

    protected void setWebViewClient() {
        webView.setWebViewClient(new BaseWebViewClient(new IWebViewClient() {
            @Override
            public void onPageStarted() {
                loadingView.startLoading();
            }

            @Override
            public void onPageFinished() {
                loadingView.endLoading(true);
            }

            @Override
            public void onReceivedError() {
                checkNetWorkAvailable(NET_WORK_NOT_AVAILABLE);
            }


        }));
    }

    @Override
    protected void setWebSetting(WebSettings webSetting) {
        super.setWebSetting(webSetting);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setSaveFormData(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
}
