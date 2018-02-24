package com.renren.wawa.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wawaji.vip.R;
import com.renren.wawa.adapter.WeChatRechargeAdapter;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.PaySettingBean;
import com.renren.wawa.model.PayWeiXinBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.ScreenUtil;
import com.renren.wawa.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * 微信充值弹框
 * Created by xunwang on 2017/9/23.
 */

public class WeChatRechargeDialog extends Dialog implements WeChatRechargeAdapter.OnItemClickListener {
    @BindView(R.id.comm_dialog_title)
    TextView commDialogTitle;
    @BindView(R.id.recharge_recycler_view)
    RecyclerView rechargeRecyclerView;
    @BindView(R.id.comm_dialog_root)
    FrameLayout commDialogRoot;
    @BindView(R.id.cancel_txt)
    TextView cancelTxt;
    private List<PaySettingBean.DataBean> weixinBeanList = new ArrayList<>();
    private Context context;
    private WeChatRechargeAdapter weChatRechargeAdapter;

    public WeChatRechargeDialog(@NonNull Context context, List<PaySettingBean.DataBean> weixinBeanList) {
        super(context, R.style.CommDialogStyle);
        this.context = context;
        this.weixinBeanList = weixinBeanList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_we_chat_recharge, null);
        setContentView(view);
        init(view);
        applyLayoutParams();
    }

    private void init(View view) {
        setCanceledOnTouchOutside(true);
        cancelTxt = view.findViewById(R.id.cancel_txt);
        rechargeRecyclerView = view.findViewById(R.id.recharge_recycler_view);
        rechargeRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        weChatRechargeAdapter = new WeChatRechargeAdapter(weixinBeanList, context);
        rechargeRecyclerView.setAdapter(weChatRechargeAdapter);
        weChatRechargeAdapter.setOnItemClickListener(this);
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    /**
     * 设置位置大小
     */
    private void applyLayoutParams() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lp.width = (int) (ScreenUtil.getScreenHeight() * 0.9);
        } else {
            lp.width = (int) (ScreenUtil.getScreenWidth() * 0.9);
        }
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

    }


    @Override
    public void onItemClickListener(PaySettingBean.DataBean weixinBean, View view, int position) {
        dismiss();

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
}
