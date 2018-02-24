package com.renren.wawa.fragment;

import android.net.Uri;
import android.widget.Button;
import android.widget.TextView;

import com.wawaji.vip.R;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.ShareContentText;
import com.renren.wawa.model.UserInvitationCodeBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.NetWorkUtil;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.utils.WeChatShare;
import com.renren.wawa.view.CommListDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static com.renren.wawa.utils.WeChatShare.WEIXIN_FRIEND;
import static com.renren.wawa.utils.WeChatShare.WEIXIN_TIMELINE;
import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneTimeline;

/**
 * 分享邀请码页面
 */

public class ShareInviteFragment extends BaseFragment {
    @BindView(R.id.invitation_code_txt)
    TextView invitationCodeTxt;
    @BindView(R.id.invitation_tips)
    TextView invitationTips;

    private List<UserInvitationCodeBean.DataBean.ShareListBean> optionsBeanList = new ArrayList<>();
    private String title, desc;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_share_invite;
    }

    @Override
    public void lazyFetchData() {
        getInvitationCode();
    }

    @Override
    public void initViews() {
        BBLog.d(Constants.TAG, "ShareInviteFragment initViews");
    }

    @OnClick(R.id.invitation_code_share_btn)
    public void invitationCodeShareClick() {
        if (!NetWorkUtil.checkNetWork(getActivity())) {
            ToastUtil.showToast(getString(R.string.error_network));
            return;
        }
        if (!WawaApplication.getInstance().getmWxApi().isWXAppInstalled()) {
            ToastUtil.showToast("您还未安装微信客户端");
            return;
        }
        CommListDialog listDialog = new CommListDialog(getActivity(), "分享邀请码");
        List<String> nameList = new ArrayList<>();
        for (UserInvitationCodeBean.DataBean.ShareListBean optionsBean : optionsBeanList) {
            if ("cancel".equals(optionsBean.getType())) {
                continue;
            }
            nameList.add(optionsBean.getTitle());
        }
        listDialog.setDataList(nameList);
        listDialog.setOnListDialogItemClickListener(new CommListDialog.OnListDialogItemClickListener() {

            @Override
            public void onItemClick(int position) {
                String data = optionsBeanList.get(position).getUrl();
                Uri uri = Uri.parse(data);
//                String type = uri.getQueryParameter("to");
                String type = optionsBeanList.get(position).getType();
                String url = optionsBeanList.get(position).getUrl();//uri.getQueryParameter("url");
//                String title = uri.getQueryParameter("title");
//                String content = uri.getQueryParameter("desc");
                BBLog.d("kkkkkkkk", "share data --> " + data + " url --> " + url);
                ShareContentText shareContentText = new ShareContentText(desc, title, url);
                int weChatType;
                if (WEIXIN_FRIEND.equals(type)) {//微信好友
                    weChatType = WXSceneSession;
                } else if (WEIXIN_TIMELINE.equals(type)) { //微信朋友圈
                    weChatType = WXSceneTimeline;
                } else {
                    ToastUtil.showToast("分享有错误");
                    return;
                }
                WeChatShare.shareByWebchat(shareContentText, weChatType);
            }
        });
        listDialog.show();
    }

    /**
     * 获取邀请码
     */
    private void getInvitationCode() {
        HttpMethods.getInstance().getUserInvitationCode(
                new Subscriber<UserInvitationCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(UserInvitationCodeBean userInvitationCodeBean) {
                        if (userInvitationCodeBean != null && userInvitationCodeBean.isSucceed()) {
                            invitationCodeTxt.setText("" + userInvitationCodeBean.getData().getInviteCode());
                            invitationTips.setText(userInvitationCodeBean.getData().getDesc());
                            optionsBeanList = userInvitationCodeBean.getData().getShareList();
                            title = userInvitationCodeBean.getData().getTitle();
                            desc = userInvitationCodeBean.getData().getDesc();
                        }
                    }
                }
        );

    }
}
