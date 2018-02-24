package com.wawaji.vip.wxapi;

import android.os.Bundle;
import android.os.Message;

import com.wawaji.vip.R;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.base.BaseActivity;
import com.renren.wawa.config.Constants;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

/**
 * 微信登录  必须要的不能改名字
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    public int getLayoutId() {
        return R.layout.pay_result;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        //如果没回调onResp，八成是这句没有写
        WawaApplication.getInstance().getmWxApi().handleIntent(getIntent(), this);
    }

    @Override
    public void initData() {

    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        BBLog.e(TAG, "onResp: " + resp.errStr + " " + "错误码 : " + resp.errCode);
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()){
                    BBLog.e(TAG, "onResp:取消分享 ");
                    ToastUtil.showToast("取消分享");
                }
                else {
                    BBLog.e(TAG, "onResp:登录失败 ");
                    ToastUtil.showToast("登录失败");
                    Message message = new Message();
                    message.what = Constants.WE_CHAT_LOGIN_FAILED;
                    EventBus.getDefault().post(message);
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        ToastUtil.showToast("微信登录成功");

                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        //LogUtils.sf("code = " + code);
                        BBLog.e(TAG, "onResp: RETURN_MSG_TYPE_LOGIN " + code);
                        Message message = new Message();
                        message.what = Constants.WE_CHAT_LOGIN_SUCCESS;
                        Bundle bundle = new Bundle();
                        bundle.putString("code", code);
                        message.setData(bundle);
                        EventBus.getDefault().post(message);
                        finish();
                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtil.showToast("微信分享成功");
                        BBLog.e(TAG, "onResp:RETURN_MSG_TYPE_SHARE  微信分享成功 ");
                        finish();
                        break;
                }
                break;
        }
    }

}
