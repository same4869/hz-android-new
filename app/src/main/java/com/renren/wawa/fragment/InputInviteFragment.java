package com.renren.wawa.fragment;

import android.util.Log;
import android.widget.EditText;

import com.renren.wawa.R;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.BaseObject;
import com.renren.wawa.model.UserAppliedInvitationCodeBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 输入邀请码页面
 */

public class InputInviteFragment extends BaseFragment {
    @BindView(R.id.invitation_code_txt)
    EditText invitationCodeTxt;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_input_invite;
    }

    @Override
    public void lazyFetchData() {
        getUserAppliedInvitationCode();
    }

    @Override
    public void initViews() {
        BBLog.d(Constants.TAG, "InputInviteFragment initViews");
    }

    @OnClick(R.id.input_invitation_code_submit)
    public void inputInvitationCodeSubmitClick() {
        final String code = invitationCodeTxt.getText().toString();
        if (StringUtil.isNotBlank(code)) {
            HttpMethods.getInstance().getUserApplyInvitationCode(code, new Subscriber<BaseObject>() {

                @Override
                public void onCompleted() {
                    invitationCodeTxt.setText("");
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("kkkkkkkk2", e.getMessage());
                }

                @Override
                public void onNext(BaseObject baseObject) {
                    if (baseObject != null) {
                        if (baseObject.isSucceed()) {
                            ToastUtil.showToastWithHandler(getResources().getString(R.string.invitation_success));
                        }
                    }
                }
            });
        } else {
            ToastUtil.showToast("请输入邀请码");
        }
    }

    /**
     * 用户已使用的邀请码
     */
    private void getUserAppliedInvitationCode(){
        HttpMethods.getInstance().getUserAppliedInvitationCode(new Subscriber<UserAppliedInvitationCodeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserAppliedInvitationCodeBean userAppliedInvitationCodeBean) {
                if(userAppliedInvitationCodeBean!=null&&userAppliedInvitationCodeBean.isSucceed()){
                    invitationCodeTxt.setText(userAppliedInvitationCodeBean.getData().getInviteCode());
                    invitationCodeTxt.setSelection(userAppliedInvitationCodeBean.getData().getInviteCode().length());
                }
            }
        });
    }
}
