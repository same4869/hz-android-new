package com.renren.wawa.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.wawaji.vip.R;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.NetWorkUtil;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.CommBeatLoadingView;

/**
 * web公共类
 */

public class BaseWebActivity extends BaseTitleBarActivity implements CommBeatLoadingView.OnReloadListener {
    public static final String TAG = BaseWebActivity.class.getSimpleName();
    protected static final int NET_WORK_AVAILABLE = 0;
    protected static final int NET_WORK_NOT_AVAILABLE = 1;

    protected WebView webView;
    protected CommBeatLoadingView loadingView;
    protected RelativeLayout webviewContainer;

    protected static boolean isError = false;
    public String url;
    protected static boolean isLoadingViewShouldVis;
    // 同一个H5页面内跳转时，没网会先onReceivedError再onPageFinished，这个时候不应该隐藏loadingView

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        webviewContainer = (RelativeLayout) findViewById(R.id.webview_container);
        if (webView == null) {
            webView = new WebView(getApplicationContext());
            RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                    .MATCH_PARENT);
            webView.setLayoutParams(rl);
        }
        webviewContainer.addView(webView);
        setWebSetting(webView.getSettings());
        loadingView = (CommBeatLoadingView) findViewById(R.id.opinion_callback_loading);
        loadingView.setContentText(getString(R.string.pull_to_refresh_refreshing_label));
        loadingView.startLoading();
        loadingView.setOnReloadListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_web;
    }

    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    protected void setWebSetting(WebSettings setting) {
        setting.setJavaScriptEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        setting.setAppCacheEnabled(false);
        setting.setBuiltInZoomControls(false);
        setting.setSupportZoom(false);
        setting.setDomStorageEnabled(true);
        setting.setUseWideViewPort(true);

    }

    protected void loadUrl(String url) {
        this.url = url;
        webView.loadUrl(url);
    }

    public static class BaseWebViewClient extends WebViewClient {

        public IWebViewClient mIWebViewClient;

        public BaseWebViewClient(IWebViewClient mIWebViewClient) {
            this.mIWebViewClient = mIWebViewClient;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (onShouldInterceptRequest != null) {
                onShouldInterceptRequest.OnShouldInterceptRequest(request.getUrl().toString());
            }
            BBLog.d("kkkkkkkk", "shouldInterceptRequest request: " + request.getUrl());
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (overrideUrlLoadingListener != null) {
                return overrideUrlLoadingListener.onOverrideUrlLoading(url);
            }
            if (url != null && url.startsWith("wwj")) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(WawaApplication.getContext(), CommonInvokerActivity.class);
                intent.setData(uri);
                WawaApplication.getContext().startActivity(intent);
                return true;
            }
            BBLog.d("kkkkkkkk", "shouldOverrideUrlLoading url: " + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            BBLog.d("kkkkkkkk", "onPageStarted url: " + url);
            if (mIWebViewClient != null) {
                mIWebViewClient.onPageStarted();
            }
            isLoadingViewShouldVis = false;
            isError = false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            BBLog.d("kkkkkkkk", "onPageFinished url: " + url);
            if (!isLoadingViewShouldVis && mIWebViewClient != null) {
                mIWebViewClient.onPageFinished();
                view.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            BBLog.d("kkkkkkkk", "onReceivedError errorCode: " + errorCode + " description: " + description);
            if (mIWebViewClient != null) {
                mIWebViewClient.onReceivedError();
            }
            isError = true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            BBLog.d("kkkkkkkk", "onReceivedHttpError");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            BBLog.d("kkkkkkkk", "onReceivedError");
        }
    }

    /**
     * 防止内存泄漏，
     * 执行WebViewClient中对应的方法
     */
    public interface IWebViewClient {
        void onPageStarted();

        void onPageFinished();

        void onReceivedError();

    }

    private static OnOverrideUrlLoadingListener overrideUrlLoadingListener;
    private static OnShouldInterceptRequestListener onShouldInterceptRequest;

    //拦截接口
    public interface OnOverrideUrlLoadingListener {
        boolean onOverrideUrlLoading(String url);
    }

    public interface OnShouldInterceptRequestListener {
        boolean OnShouldInterceptRequest(String url);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        clearWebViewRes();
        super.onDestroy();
    }

    public void clearWebViewRes() {
        if (webView != null) {
            BBLog.e(TAG, "cjjjjj BaseVideoWebActivity onDestroy()");
            webView.removeAllViews();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.setTag(null);
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (isError) {
            setResult(RESULT_OK);
            finish();
            return;
        }
        super.onBackPressed();
    }

    protected void checkNetWorkAvailable(int netWorkKey) {
        switch (netWorkKey) {
            case NET_WORK_AVAILABLE:
                webView.setVisibility(View.VISIBLE);
                loadingView.endLoading(true);
                webView.loadUrl(url);
                break;
            case NET_WORK_NOT_AVAILABLE:
                isLoadingViewShouldVis = true;
                webView.setVisibility(View.GONE);
                loadingView.setZeroStaticBackgroundClickAble(R.mipmap.comm_net_error_bg, "网络好像不太好呀，点我刷新");
                break;
            default:
                break;
        }
    }

    @Override
    public void onReload() {
        if (NetWorkUtil.checkNetWork(getApplicationContext())) {
            checkNetWorkAvailable(NET_WORK_AVAILABLE);
        } else {
            ToastUtil.showToast(getString(R.string.error_network));
        }
    }
}
