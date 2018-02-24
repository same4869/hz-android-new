package com.renren.wawa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wawaji.vip.R;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.base.BaseActivity;
import com.renren.wawa.config.Constants;
import com.renren.wawa.manager.GameManager;
import com.renren.wawa.manager.UserManager;
import com.renren.wawa.model.EmailLoginBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.sp.CommSetting;
import com.renren.wawa.utils.AppUtil;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 登录页面
 */

public class SigninActivity extends BaseActivity {
    private static final String TAG = "SigninActivity";

    @BindView(R.id.signin_email)
    EditText mEmailInput;
    @BindView(R.id.signin_passwd)
    EditText mPasswdInput;

    @BindView(R.id.signin_button)
    Button signinButton;
    @BindView(R.id.terms_and_policy_check_box)
    CheckBox termsAndPolicyCheckBox;
    @BindView(R.id.terms_and_policy_txt)
    TextView termsAndPolicyTxt;
    @BindView(R.id.terms_and_policy_layout)
    LinearLayout termsAndPolicyLayout;

    @BindView(R.id.select_login_type_tv)
    TextView selectLoginTypeTv;
    private boolean isWeChatLogin = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_signin;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.terms_and_policy_txt)
    public void termsAndPolicyOnClick() {
        Intent intent = new Intent(this, CommWebActivity.class);
        intent.putExtra(CommWebActivity.COMM_WEB_URL, Constants.USER_PROTOCOL_URL);
        intent.putExtra(CommWebActivity.COMM_WEB_TITLE, getString(R.string.user_Protocol));
        startActivity(intent);
    }

    /**
     * 选择登录方式
     */
    @OnClick(R.id.select_login_type_tv)
    public void selectLoginTypeClick() {
        isWeChatLogin = !isWeChatLogin;
        if (isWeChatLogin) {
            mEmailInput.setVisibility(View.GONE);
            mPasswdInput.setVisibility(View.GONE);
            signinButton.setText(getString(R.string.we_chat_login));
            signinButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_wechat_white), null, null, null);
            selectLoginTypeTv.setText(getString(R.string.select_email_login));
        } else {
            mEmailInput.setVisibility(View.VISIBLE);
            mPasswdInput.setVisibility(View.VISIBLE);
            signinButton.setText(getString(R.string.signin_email));
            signinButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.login_img_email), null, null, null);
            selectLoginTypeTv.setText(getString(R.string.select_we_chat_login));
            if(Constants.isDebug){
                mEmailInput.setText("zhao002@renrenzww.com");
                mPasswdInput.setText("foobar");

//                mEmailInput.setText("wushengwuxi01@163.com");
//                mPasswdInput.setText("foobar");
            }
        }
    }

    /**
     * 登录
     */
    @OnClick(R.id.signin_button)
    public void signinClick() {
        if (!termsAndPolicyCheckBox.isChecked()) {
            ToastUtil.showToast(getString(R.string.terms_and_policy_check));
            return;
        }
        if (AppUtil.isFastDoubleClick()) {
            return;
        }
        if (isWeChatLogin) {//微信登录
            if (!WawaApplication.getInstance().getmWxApi().isWXAppInstalled()) {
                ToastUtil.showToast("您还未安装微信客户端");
                return;
            }
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "diandi_wx_login";
            WawaApplication.getInstance().getmWxApi().sendReq(req);
        } else {//邮箱登录
            String email = mEmailInput.getText().toString();
            String passwd = mPasswdInput.getText().toString();

            if (StringUtil.isBlank(email)) {
                ToastUtil.showToast(getString(R.string.email_null));
                return;
            }

            if (!StringUtil.checkEmail(email)) {
                ToastUtil.showToast(getString(R.string.email_type_error));
                return;
            }

            if (StringUtil.isBlank(passwd)) {
                ToastUtil.showToast(getString(R.string.passwd_null));
                return;
            }

            HttpMethods.getInstance().getEmailLogin(email, passwd, new Subscriber<EmailLoginBean>() {
                @Override
                public void onCompleted() {
                    startActivity(new Intent(SigninActivity.this, WawaMainActivity.class));
                    finish();
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(EmailLoginBean emailLoginBean) {
                    UserManager.saveUserProfile(emailLoginBean);
                    CommSetting.setToken(emailLoginBean.getData().getSessionId());
                    GameManager.getInstance().start();
                }
            });
        }

    }

    /**
     * 微信登录
     *
     * @param code
     */
    private void getWeXinLogin(String code) {
        HttpMethods.getInstance().getWeXinLogin(code, new Subscriber<EmailLoginBean>() {
            @Override
            public void onCompleted() {
                startActivity(new Intent(SigninActivity.this, WawaMainActivity.class));
                finish();
            }

            @Override
            public void onError(Throwable e) {
                BBLog.d("kkkkkkkk","getWeXinLogin e.getMessage()" + e.getMessage());
            }

            @Override
            public void onNext(EmailLoginBean emailLoginBean) {
                UserManager.saveUserProfile(emailLoginBean);
                CommSetting.setToken(emailLoginBean.getData().getSessionId());
                GameManager.getInstance().start();
            }
        });
    }

    @Subscribe
    public void onEventMainThread(Message message) {
        BBLog.e("onEventMainThread ", message.toString());
        switch (message.what) {
            case Constants.WE_CHAT_LOGIN_SUCCESS:
                String code = message.getData().getString("code");
                getWeXinLogin(code);
                break;
            case Constants.WE_CHAT_LOGIN_FAILED:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
