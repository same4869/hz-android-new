package com.renren.wawa.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wawaji.vip.R;
import com.renren.wawa.adapter.WeChatRechargeAdapter;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.PaySettingBean;
import com.renren.wawa.model.PayWeiXinBean;
import com.renren.wawa.model.UserInfo;
import com.renren.wawa.model.UserWalletBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.CommRoundAngleImageView;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;

/**
 * 充值页面
 */

public class RechargeFragment extends BaseFragment implements WeChatRechargeAdapter.OnItemClickListener {
    @BindView(R.id.user_img)
    CommRoundAngleImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.icon_totle)
    TextView iconTotle;
    @BindView(R.id.recharge_recycler_view)
    RecyclerView rechargeRecyclerView;
    @BindView(R.id.pay_with_wx)
    LinearLayout payWithWx;
    @BindView(R.id.pay_with_ali)
    LinearLayout payWithAli;
    private List<PaySettingBean.DataBean> weixinBeanList = new ArrayList<>();
    private WeChatRechargeAdapter weChatRechargeAdapter;
//    private int curSelectPay = -1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recharge;
    }

    @Override
    public void lazyFetchData() {
        loadChargeList();
        refreshBalance();
//        getUserInfo(0);
    }

    @Override
    public void initViews() {
        rechargeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rechargeRecyclerView.setNestedScrollingEnabled(false);
    }

//    @OnClick(R.id.pay_with_wx)
//    public void weixinPayClick() {
//        if (curSelectPay != -1) {
//            HttpMethods.getInstance().getPayWeiXin(new Subscriber<PayWeiXinBean>() {
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    BBLog.e(e.toString());
//                }
//
//                @Override
//                public void onNext(PayWeiXinBean payWeiXinBean) {
//                    BBLog.e(payWeiXinBean.toString());
//                    PayWeiXinBean.DataBean weiXinData = payWeiXinBean.getData();
//                    PayReq req = new PayReq();
//                    req.appId = Constants.WE_CHAT_APP_ID;// 微信开放平台审核通过的应用APPID
//                    req.partnerId = weiXinData.getPartnerid();// 微信支付分配的商户号
//                    req.prepayId = weiXinData.getPrepayid();// 预支付订单号，app服务器调用“统一下单”接口获取
//                    req.nonceStr = weiXinData.getNoncestr();// 随机字符串，不长于32位，服务器小哥会给咱生成
//                    req.timeStamp = String.valueOf(weiXinData.getTimestamp());// 时间戳，app服务器小哥给出
//                    req.packageValue = weiXinData.getPackageX();// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
//                    req.sign = weiXinData.getSign();// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
//                    WawaApplication.getInstance().getmWxApi().sendReq(req);
//                }
//            }, weixinBeanList.get(curSelectPay).getAmount());
//        }
//    }

    /**
     * 充值 列表
     */
    private void loadChargeList() {
        HttpMethods.getInstance().getPaySetting(new Subscriber<PaySettingBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PaySettingBean paySettingBean) {
                weixinBeanList = paySettingBean.getData();
                weChatRechargeAdapter = new WeChatRechargeAdapter(weixinBeanList, getActivity());
                rechargeRecyclerView.setAdapter(weChatRechargeAdapter);
                weChatRechargeAdapter.setOnItemClickListener(RechargeFragment.this);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshBalance();
    }

    /**
     * 获取当前喵喵币
     */
    private void refreshBalance() {
        HttpMethods.getInstance().getUserWallet(new Subscriber<UserWalletBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserWalletBean userWalletBean) {
                int mBalance = userWalletBean.getData().getBalance();
                iconTotle.setText(mBalance + "");
            }
        });
    }

    @Override
    public void onItemClickListener(PaySettingBean.DataBean weixinBean, View view, int position) {
//        curSelectPay = position;
        if (WawaApplication.getInstance().getmWxApi().isWXAppInstalled()) {
            HttpMethods.getInstance().getPayWeiXin(new Subscriber<PayWeiXinBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    BBLog.e(e.toString());
                }

                @Override
                public void onNext(PayWeiXinBean payWeiXinBean) {
                    BBLog.e(payWeiXinBean.toString());
                    PayWeiXinBean.DataBean weiXinData = payWeiXinBean.getData();
                    PayReq req = new PayReq();
                    req.appId = Constants.WE_CHAT_APP_ID;// 微信开放平台审核通过的应用APPID
                    req.partnerId = weiXinData.getPartnerid();// 微信支付分配的商户号
                    req.prepayId = weiXinData.getPrepayid();// 预支付订单号，app服务器调用“统一下单”接口获取
                    req.nonceStr = weiXinData.getNoncestr();// 随机字符串，不长于32位，服务器小哥会给咱生成
                    req.timeStamp = String.valueOf(weiXinData.getTimestamp());// 时间戳，app服务器小哥给出
                    req.packageValue = weiXinData.getPackageX();// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
                    req.sign = weiXinData.getSign();// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
                    WawaApplication.getInstance().getmWxApi().sendReq(req);
                }
            }, weixinBean.getPrice());
        } else {
            ToastUtil.showToast("您还未安装微信客户端");
        }
    }

//    /**
//     * 获取当前用户的 profile
//     */
//    private void getUserInfo(long userId) {
//        showLoadingDialog();
//        HttpMethods.getInstance().getUserInfo(userId, new Subscriber<UserInfo>() {
//            @Override
//            public void onCompleted() {
//                cancelLoadingDialog();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                cancelLoadingDialog();
//            }
//
//            @Override
//            public void onNext(UserInfo userInfo) {
//                if (userInfo != null && userInfo.isSucceed()) {
//                    if (StringUtil.isNotBlank(userInfo.getData().getAvatar())) {
//                        Picasso.with(getActivity()).load(userInfo.getData().getAvatar()).into(userImg);
//
//                    }
//                    userName.setText(userInfo.getData().getNickname() + "(ID:" + userInfo.getData().getId() + ")");
//                }
//            }
//        });
//    }
}
