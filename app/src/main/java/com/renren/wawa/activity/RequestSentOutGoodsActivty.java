package com.renren.wawa.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renren.wawa.R;
import com.renren.wawa.adapter.ProductorOrderAdapter;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.BaseObject;
import com.renren.wawa.model.OrderShippingBean;
import com.renren.wawa.model.OrderShippingFeeBean;
import com.renren.wawa.model.OrderUnboundItemsBean;
import com.renren.wawa.model.UserAddressBean;
import com.renren.wawa.model.UserWalletBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 发货信息
 */

public class RequestSentOutGoodsActivty extends BaseTitleBarActivity implements ProductorOrderAdapter.ProductOrderItemIdsListener {
    @BindView(R.id.productor_order_recycler_view)
    RecyclerView productorOrderRecyclerView;
    @BindView(R.id.user_nickname)
    TextView userNickname;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.user_address)
    TextView userAddress;
    @BindView(R.id.add_remarks_txt)
    TextView addRemarksTxt;
    @BindView(R.id.postage_txt)
    TextView postageTxt;
    @BindView(R.id.current_wawa_coin_txt)
    TextView currentWawaCoinTxt;
    @BindView(R.id.user_address_layout)
    RelativeLayout userAddressLayout;
    @BindView(R.id.pay_and_shipping_txt)
    TextView payAndShippingTxt;
    @BindView(R.id.remarks_tips_txt)
    TextView remarksTipsTxt;
    @BindView(R.id.remarks_context_txt)
    TextView remarksContextTxt;

    public static final int AddNewAddressRequest = 0;
    public static final int AddRemarksRequest = 1;
    public static final int SelectAddressRequest = 2;
    public static final String AddRemarksData = "add_remarks_data";

    private ProductorOrderAdapter productorOrderAdapter;
    private List<OrderUnboundItemsBean.DataBean.ProductBean> productOrderItemsBeanList = new ArrayList<>();
    private List<Integer> productOrderItemIds = new ArrayList<>();
    private String remarksData;

    //    地址信息
    private int addressId = -1;
    private String addressName;
    private String addressMobile;
    private String addressContent;
    private int id = -1;
    private UserAddressBean.DataBean currentUserAddress = new UserAddressBean.DataBean();

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_sent_out_goods;
    }

    @Override
    public void initData() {
        init();
    }

    private void init() {
        id = getIntent().getIntExtra("id", -1);
        productOrderItemIds.add(id);
        productorOrderAdapter = new ProductorOrderAdapter(id, productOrderItemsBeanList, this);
        productorOrderRecyclerView.setAdapter(productorOrderAdapter);
        productorOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productorOrderAdapter.setProductOrderItemIdsListener(this);

        getUserAddressList();
        getProductOrderData();
        getMoney();
    }


    @Override
    public void onResume() {
        super.onResume();
//        getUserAddressList();
    }

    @OnClick(R.id.modify_address_txt)
    public void modifyAddressOnClick() {
        if (currentUserAddress != null) {
            Intent intent = new Intent(this, AddNewAddressActivty.class);
            intent.putExtra("UserAddressBean", currentUserAddress);
            intent.putExtra("ModifyAddress", true);
            startActivityForResult(intent, AddNewAddressRequest);
        }
    }

    @OnClick(R.id.user_address_layout)
    public void selectUserAddressOnClick() {
        Intent intent = new Intent(this, UserAddressListActivity.class);
        startActivityForResult(intent, SelectAddressRequest);
    }

    @OnClick(R.id.add_user_address_txt)
    public void addUserAddressOnClick() {
        Intent intent = new Intent(this, AddNewAddressActivty.class);
        startActivityForResult(intent, AddNewAddressRequest);
    }

    @OnClick(R.id.add_remarks_layout)
    public void addRemarksOnClick() {
        Intent intent = new Intent(this, AddRemarksActivity.class);
        if (StringUtil.isNotBlank(remarksData)) {
            intent.putExtra(AddRemarksData, remarksData);
        }
        startActivityForResult(intent, AddRemarksRequest);
    }

    @OnClick(R.id.pay_and_shipping_txt)//支付并发货
    public void payAndShippingOnClick() {
        if (productOrderItemIds.size() == 0) {
            ToastUtil.showToast("请选择要发货娃娃");
            return;
        }

        if (addressId == -1) {
            ToastUtil.showToast("请创建邮寄地址");
            return;
        }

        productOrderCheckoutShipping(productOrderItemIds, addressId, remarksData);
    }

    /**
     * 获取 用户地址列表
     */
    private void getUserAddressList() {
        HttpMethods.getInstance().getUserAddress(20, 0, new Subscriber<UserAddressBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserAddressBean userAddressBean) {
                if (userAddressBean != null && userAddressBean.getData().size() > 0) {
                    currentUserAddress = userAddressBean.getData().get(0);
                    userAddressLayout.setVisibility(View.VISIBLE);
                    userNickname.setText(currentUserAddress.getName());
                    userPhone.setText(currentUserAddress.getPhoneNo());
                    userAddress.setText(currentUserAddress.getAddress().replace("|", " "));
                    addressId = currentUserAddress.getId();
                    if (addressId != -1 && productOrderItemIds.size() > 0) {
                        getPostage(productOrderItemIds, addressId);
                    }
                } else {
                    userAddressLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 获取 商品订单项
     */
    private void getProductOrderData() {
        HttpMethods.getInstance().getOrderUnboundItems(100, 0, new Subscriber<OrderUnboundItemsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                BBLog.d("kkkkkkkk", "error");
            }

            @Override
            public void onNext(OrderUnboundItemsBean orderUnboundItemsBean) {
                if (orderUnboundItemsBean.getData().getWawaList().size() > 0) {
                    id = orderUnboundItemsBean.getData().getWawaList().get(0).getId();
                }

                for (OrderUnboundItemsBean.DataBean.ProductBean productOrderItemsBean : orderUnboundItemsBean.getData().getWawaList()) {
                    if (id == productOrderItemsBean.getId()) {
                        productOrderItemsBean.setChecked(true);
                        break;
                    }
                }
//                productOrderItemIds.clear();
//                productOrderItemIds.add(id);
                productorOrderAdapter.setNewData(orderUnboundItemsBean.getData().getWawaList());
                if (addressId != -1 && productOrderItemIds.size() > 0) {
                    getPostage(productOrderItemIds, addressId);
                }
            }
        });
    }

    /**
     * 获取当前喵喵币
     */
    private void getMoney() {
        HttpMethods.getInstance().getUserWallet(new Subscriber<UserWalletBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserWalletBean userWalletBean) {
                currentWawaCoinTxt.setText(String.format(getString(R.string.current_wawa_coin), userWalletBean.getData().getBalance()));

            }
        });
    }

    /**
     * 获取邮费
     */
    private void getPostage(List<Integer> productOrderItemIds, int addressId) {
        BBLog.e(Constants.TAG, "productOrderItemIds " + productOrderItemIds.toString());
        HttpMethods.getInstance().getOrderShippingFee(new Subscriber<OrderShippingFeeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OrderShippingFeeBean orderShippingFeeBean) {
                postageTxt.setText(orderShippingFeeBean.getData().getCoin() + "个喵喵币");
                if (orderShippingFeeBean.getData().getCoin() == 0) {
                    payAndShippingTxt.setText(getResources().getString(R.string.request_shipping));
                } else {
                    payAndShippingTxt.setText(getResources().getString(R.string.pay_and_shipping));
                }
            }
        }, productOrderItemIds.toString().replace("[", "").replace("]", ""), addressId);
    }

    /**
     * 创建一个发货订单
     */
    private void productOrderCheckoutShipping(List<Integer> productOrderItemIds, int addressId, String remarks) {
        BBLog.d("kkkkkkkk","productOrderItemIds.toString() --> " + productOrderItemIds.toString());
        HttpMethods.getInstance().getOrderShipping(new Subscriber<BaseObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseObject orderShippingBean) {
                setResult(RESULT_OK);
                finish();
            }
        }, productOrderItemIds.toString().replace("[", "").replace("]", ""), addressId, remarks);
    }


    @Override
    public void productOrderItemIdsListener(List<Integer> productOrderItemIds) {
        if (addressId != -1 && productOrderItemIds.size() > 0) {
            getPostage(productOrderItemIds, addressId);
        }
        this.productOrderItemIds = productOrderItemIds;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AddNewAddressRequest://添加新地址
                    userAddressLayout.setVisibility(View.VISIBLE);

                    addressName = data.getStringExtra("name");
                    addressMobile = data.getStringExtra("mobile");
                    addressContent = data.getStringExtra("address");
                    addressId = data.getIntExtra("id", -1);

                    currentUserAddress.setName(addressName);
                    currentUserAddress.setPhoneNo(addressMobile);
                    currentUserAddress.setAddress(addressContent);
                    currentUserAddress.setId(addressId);

                    userNickname.setText(addressName);
                    userPhone.setText(addressMobile);
                    if (StringUtil.isNotBlank(addressContent)) {
                        userAddress.setText(addressContent.replace("|", " "));
                    } else {
                        userAddress.setText("");
                        userAddressLayout.setVisibility(View.GONE);
                    }
                    if (addressId != -1 && productOrderItemIds.size() > 0) {
                        getPostage(productOrderItemIds, addressId);
                    }
                    break;
                case AddRemarksRequest://添加标注
                    remarksData = data.getStringExtra(AddRemarksData);
                    if (StringUtil.isNotBlank(remarksData)) {
                        addRemarksTxt.setVisibility(View.GONE);
                        remarksTipsTxt.setVisibility(View.VISIBLE);
                        remarksContextTxt.setVisibility(View.VISIBLE);
                        remarksContextTxt.setText(remarksData);
                    } else {
                        addRemarksTxt.setVisibility(View.VISIBLE);
                        remarksTipsTxt.setVisibility(View.GONE);
                        remarksContextTxt.setVisibility(View.GONE);
                        remarksData = "";
                        remarksContextTxt.setText(remarksData);
                    }
                    break;
                case SelectAddressRequest://选择地址
                    addressName = data.getStringExtra("name");
                    addressMobile = data.getStringExtra("mobile");
                    addressContent = data.getStringExtra("address");
                    addressId = data.getIntExtra("id", -1);

                    currentUserAddress.setName(addressName);
                    currentUserAddress.setPhoneNo(addressMobile);
                    currentUserAddress.setAddress(addressContent);
                    currentUserAddress.setId(addressId);

                    userNickname.setText(addressName);
                    userPhone.setText(addressMobile);
                    if (StringUtil.isNotBlank(addressContent)) {
                        userAddress.setText(addressContent.replace("|", " "));
                        userAddressLayout.setVisibility(View.VISIBLE);
                    } else {
                        userAddress.setText("");
                        userAddressLayout.setVisibility(View.GONE);
                    }
                    if (addressId != -1 && productOrderItemIds.size() > 0) {
                        getPostage(productOrderItemIds, addressId);
                    }
                    break;
            }
        }
    }
}
