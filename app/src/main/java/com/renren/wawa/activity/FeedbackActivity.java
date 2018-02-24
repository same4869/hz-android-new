package com.renren.wawa.activity;

import android.text.TextUtils;
import android.widget.EditText;

import com.renren.wawa.R;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.model.BaseObject;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 用户反馈
 */

public class FeedbackActivity extends BaseTitleBarActivity {
    @BindView(R.id.feedback_text)
    EditText feedbackText;
    @BindView(R.id.feedback_moblie)
    EditText feedbackMoblie;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.feed_back_btn)
    public void feedBackClick() {
        requestFeedBack(feedbackMoblie.getText().toString(), feedbackText.getText().toString());
    }

    private void requestFeedBack(String mobile, String text) {
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(text)) {
            ToastUtil.showToast("内容与手机号不能为空");
            return;
        }
        if (!StringUtil.checkMobile(mobile)) {
            ToastUtil.showToast("请填写正确的手机号");
            return;
        }
        HttpMethods.getInstance().startFeedBack(mobile, text, new Subscriber<BaseObject>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                BBLog.e(e.toString());
            }

            @Override
            public void onNext(BaseObject userGameDetailBean) {
                ToastUtil.showToast("感谢您的反馈");
                finish();
            }
        });
    }
}
