package com.renren.wawa.activity;

import android.app.Activity;
import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wawaji.vip.R;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.model.OrderRefundBean;
import com.renren.wawa.model.UserGameDetailBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.CommDialog;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

public class GameDetailActivity extends BaseTitleBarActivity {
    @BindView(R.id.session_avatar)
    ImageView sessionAvatar;
    @BindView(R.id.session_status)
    TextView sessionStatus;
    @BindView(R.id.session_nickname)
    TextView sessionNickname;
    @BindView(R.id.session_time)
    TextView sessionTime;
    @BindView(R.id.count_txt)
    TextView countTxt;
    @BindView(R.id.count_layout)
    RelativeLayout countLayout;
    @BindView(R.id.game_video_layout)
    RelativeLayout gameVideoLayout;
    @BindView(R.id.number_txt)
    TextView numberTxt;
    @BindView(R.id.number_layout)
    RelativeLayout numberLayout;
    @BindView(R.id.current_status_txt)
    TextView currentStatusTxt;
    @BindView(R.id.current_status_layout)
    RelativeLayout currentStatusLayout;
    @BindView(R.id.exchange_wawa_coin_txt)
    TextView exchangeWawaCoinTxt;
    @BindView(R.id.exchange_wawa_coin_layout)
    RelativeLayout exchangeWawaCoinLayout;
    @BindView(R.id.game_detail_action_recycler_view)
    RecyclerView gameDetailActionRecyclerView;
    @BindView(R.id.user_nickname)
    TextView userNickname;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.send_goods_status)
    TextView sendGoodsStatus;
    @BindView(R.id.remarks_key_txt)
    TextView remarksKeyTxt;
    @BindView(R.id.remarks_value_txt)
    TextView remarksValueTxt;
    @BindView(R.id.remarks_layout)
    RelativeLayout remarksLayout;
    @BindView(R.id.order_layout)
    LinearLayout orderLayout;
    @BindView(R.id.user_address)
    TextView userAddress;
    @BindView(R.id.order_info_txt)
    TextView orderInfoTxt;
    @BindView(R.id.refund_wawa_coin_layout)
    RelativeLayout refundLayout;
    @BindView(R.id.send_wawa_coin_layout)
    RelativeLayout sendLayout;
    @BindView(R.id.refund_wawa_coin_tv)
    TextView refundWawaTv;
    @BindView(R.id.send_wawa_coin_tv)
    TextView sendWawaTv;

    public final static String GAME_ID = "game_id";
    public final static String TITLE = "title";
    private final static String EXCHANGE_TO_COIN = "exchange-to-coin";//换喵喵币
    private final static String REQUEST_SHIPPING = "request-shipping";//请求发货
    private final static String REFUND = "refund";//申请退币
    private final static String UNDETECTED = "undetected";//还我娃娃
    private final static String SHIPPING = "shipping";//发货
    private final static String APPEAL = "appeal";//申诉


    private final static int REQUEST_SEND_GOODS = 1;
    private final static int REQUEST_APPEAL = 2;


    private UserGameDetailBean userGameDetail;
//    private GameDetailActionAdapter gameDetailActionAdapter;

    private int gameId;
    private int productOrderItemIds;

    @Override
    public int getLayoutId() {
        return R.layout.activity_game_detail;
    }

    @Override
    public void initData() {
        init();
    }


    private void init() {
        gameId = getIntent().getIntExtra(GAME_ID, 0);
        String title = getIntent().getStringExtra(TITLE);
        if (title != null) {
            commTitleBarView.setTitleBarText(title);
        }
        getGameDetail(gameId);
//        gameDetailActionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        gameDetailActionAdapter = new GameDetailActionAdapter(actionsBeanList, this);
//        gameDetailActionRecyclerView.setAdapter(gameDetailActionAdapter);
//        gameDetailActionAdapter.setActionsListener(this);

    }

    /**
     * 根据网络获取的数据  更新页面
     *
     * @param userGameDetail
     */
    private void dealWithData(UserGameDetailBean userGameDetail) {
        if (userGameDetail == null && userGameDetail.getData() == null && userGameDetail.getData().getWawa() == null) {
            return;
        }
        UserGameDetailBean.DataBean gameDetail = userGameDetail.getData();
        Picasso.with(this).load(gameDetail.getWawa().getImgUrl().get(0)).into(sessionAvatar);
        sessionNickname.setText(gameDetail.getWawa().getName());
        sessionTime.setText(gameDetail.getGame().getCreated_at());
        //0 新建 1 游戏中 2 抓到了娃娃 3 没有抓到娃娃 4 游戏失败
        switch (gameDetail.getGame().getStatus()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                sessionStatus.setText("抓取成功");
                sessionStatus.setTextColor(getResources().getColor(R.color.text_green));
                gameVideoLayout.setVisibility(View.GONE);
                countTxt.setText("1");
                if (gameDetail.getStatusText() != null) {
                    currentStatusTxt.setText(gameDetail.getStatusText());
                }
                exchangeWawaCoinTxt.setText(gameDetail.getWawa().getRefund_coin() + "");
                refundWawaTv.setText("选择兑换喵喵币将失去这个娃娃，得到" + userGameDetail.getData().getWawa().getRefund_coin() + "个喵喵币");
                break;
            case 3:
                sessionStatus.setText("抓取失败");
                sessionStatus.setTextColor(getResources().getColor(R.color.gray));
                countLayout.setVisibility(View.GONE);
                currentStatusLayout.setVisibility(View.GONE);
                exchangeWawaCoinLayout.setVisibility(View.GONE);
                break;
            case 4:
                break;

        }
        numberTxt.setText(gameDetail.getGame().getId() + "");
        //发货信息
        if (gameDetail.getOrderInfo() != null && gameDetail.getOrderInfo().getAddress() != null) {
            orderLayout.setVisibility(View.VISIBLE);

            if (gameDetail.getOrderInfo().getAddress() != null) {
                userNickname.setText(gameDetail.getOrderInfo().getName());
                userPhone.setText(gameDetail.getOrderInfo().getPhoneNo());
                userAddress.setText(gameDetail.getOrderInfo().getAddress().replace("|", " "));
            }
            orderInfoTxt.setText(String.format(getString(R.string.send_goods_info), gameDetail.getOrderInfo().getOrderNo()));
            sendGoodsStatus.setText(gameDetail.getStatusText());

            remarksKeyTxt.setText(gameDetail.getOrderInfo().getExpressCompany());
            remarksValueTxt.setText(gameDetail.getOrderInfo().getExpressOrderNo());
//            if (gameDetail.getProduct_order().getExtra() != null) {
//                String shippingComment = gameDetail.getProduct_order().getExtra().getShipping_comment();
//                if (StringUtil.isNotBlank(shippingComment)) {
//                    remarksKeyTxt.setText(gameDetail.getProduct_order().getExtra().getShipping_comment());
//                    remarksValueTxt.setText(gameDetail.getProduct_order().getExtra().getShipping_order_no());
//                    if (StringUtil.isNotBlank(userGameDetail.getData().getProduct_order().getExtra().getShipping_url())) {//  物流信息
//                        remarksValueTxt.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.dp10));
//                        remarksValueTxt.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.comm_icon_arrow), null);
//                    }
//                } else {
//                    if (StringUtil.isNotBlank(gameDetail.getProduct_order().getExtra().getExt_info())) {
//                        remarksValueTxt.setText(gameDetail.getProduct_order().getExtra().getExt_info());
//                    } else {
//                        remarksValueTxt.setText(gameDetail.getProduct_order().getExtra().getRemark());
//                    }
//                }
//            }
        } else {
            orderLayout.setVisibility(View.GONE);
        }
    }


//    @OnClick(R.id.remarks_value_txt)
//    public void shippingOnClick() {
//        if (userGameDetail == null || userGameDetail.getData().getProduct_order() == null || userGameDetail.getData().getProduct_order().getExtra() == null) {
//            return;
//        }
//        String url = userGameDetail.getData().getProduct_order().getExtra().getShipping_url();
//        if (StringUtil.isNotBlank(url)) {
//            Intent intent = new Intent(this, CommWebActivity.class);
//            intent.putExtra(CommWebActivity.COMM_WEB_URL, url);
//            intent.putExtra(CommWebActivity.COMM_WEB_TITLE, getString(R.string.shipping_info));
//            startActivity(intent);
//        }
//
//    }

//    @OnClick(R.id.game_video_layout)
//    public void gameVideoOnClick() {
//        if (userGameDetail == null) {
//            return;
//        }
//        Intent intent = new Intent(this, ScratchVideoActivity.class);
//        intent.putExtra(VIDEO_URL, userGameDetail.getData().getGame().getVideo_url());
//        startActivity(intent);
//    }

    /**
     * 根据 id 获取一个游戏记录详情
     *
     * @param id
     */
    private void getGameDetail(final int id) {
        HttpMethods.getInstance().getUserGameDetail(String.valueOf(id), new Subscriber<UserGameDetailBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                BBLog.e(e.toString());
            }

            @Override
            public void onNext(UserGameDetailBean userGameDetailBean) {
                userGameDetail = userGameDetailBean;
                dealWithData(userGameDetail);
//                gameDetailActionAdapter.setNewData(userGameDetail.getData().getAction());
                if (userGameDetail.getData().getGame() != null) {
                    productOrderItemIds = userGameDetail.getData().getWawa_item().getId();
                    BBLog.d("kkkkkkkk","getGameDetail id --> " + id + " productOrderItemIds --> " + productOrderItemIds);
                }

                if (userGameDetailBean.getData().getStatus() == 0) {
                    refundLayout.setVisibility(View.VISIBLE);
                    sendLayout.setVisibility(View.VISIBLE);
                    orderLayout.setVisibility(View.GONE);
                }else{
                    refundLayout.setVisibility(View.GONE);
                    sendLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick(R.id.refund_wawa_coin_btn)
    public void refundWawaCoin() {
        showExchageToCoinDialog();
    }

    @OnClick(R.id.send_wawa_coin_btn)
    public void sendWawaCoin() {
        Intent intent = new Intent(this, RequestSentOutGoodsActivty.class);
        intent.putExtra("id", userGameDetail.getData().getWawa_item().getId());
        startActivityForResult(intent, REQUEST_SEND_GOODS);
    }

    /**
     * 创建一个兑换喵喵币订单
     */
    private void productOrderCheckOutRefund() {
        HttpMethods.getInstance().getOrderRefund(productOrderItemIds, new Subscriber<OrderRefundBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OrderRefundBean orderRefundBean) {
                if (orderRefundBean != null && orderRefundBean.isSucceed()) {
                    ToastUtil.showToast(orderRefundBean.getData().getOrder().getState());
                    getGameDetail(gameId);
                }
            }
        });
    }

//    @Override
//    public void actionsListener(String action) {
//        Intent intent;
//        switch (action) {
//            case EXCHANGE_TO_COIN://换喵喵币
//                showExchageToCoinDialog();
//                break;
//
//            case REQUEST_SHIPPING://请求发货
//                intent = new Intent(this, RequestSentOutGoodsActivty.class);
//                intent.putExtra("id", userGameDetail.getData().getGame().getId());
//                startActivityForResult(intent, REQUEST_SEND_GOODS);
//                break;
////            case REFUND://申请退币
////
////                break;
////            case UNDETECTED://还我娃娃
////
////                break;
////            case APPEAL://申诉
////                intent = new Intent(this, AppealActivty.class);
////                intent.putExtra("gameDetailData", userGameDetail);
////                startActivityForResult(intent, REQUEST_APPEAL);
////                break;
//        }
//    }

    /**
     * 显示退币dialog
     */
    private void showExchageToCoinDialog() {
        commDialog = new CommDialog(this, getResources().getString(R.string.exchage_wawa_coin_title), R.layout.view_dialog_change_icon,
                false);
        commDialog.setCanceledOnTouchOutside(false);
        commDialog.show();
        commDialog.setLeftButtonText("我再想想");
        commDialog.setRightButtonText("去兑换");
        commDialog.setLeftButtonPositive(false);
        commDialog.setRightButtonPositive(true);
        commDialog.setLeftListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                commDialog.dismiss();
            }
        });
        commDialog.setRightListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                productOrderCheckOutRefund();
                commDialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SEND_GOODS:
                    getGameDetail(gameId);
                    break;

                case REQUEST_APPEAL:
                    getGameDetail(gameId);
                    break;
            }
        }
    }
}
