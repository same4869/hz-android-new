package com.renren.wawa.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.renren.wawa.R;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.base.BaseActivity;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 微信支付
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";
    private static final int WE_CHAT_PAY_SUCCESS = 0;//成功
    private static final int WE_CHAT_PAY_FAILED = -1;//失败
    private static final int WE_CHAT_PAY_CANCEL = -2;//用户取消
    private IWXAPI api = WawaApplication.getInstance().getmWxApi();

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        api.handleIntent(getIntent(), this);
        showLoadingDialog();
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        BBLog.e(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode ==WE_CHAT_PAY_SUCCESS) {
                ToastUtil.showToast(getString(R.string.pay_success));
            }else if (resp.errCode ==WE_CHAT_PAY_FAILED) {
                ToastUtil.showToast(getString(R.string.pay_failed));
            }else if (resp.errCode ==WE_CHAT_PAY_CANCEL) {
                ToastUtil.showToast(getString(R.string.pay_cancel));
            }
            finish();
        }
    }
}