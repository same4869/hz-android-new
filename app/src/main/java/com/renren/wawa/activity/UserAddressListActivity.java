package com.renren.wawa.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.renren.wawa.R;
import com.renren.wawa.adapter.UserAddressAdapter;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.model.BaseObject;
import com.renren.wawa.model.UserAddressBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.CommDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static com.renren.wawa.activity.RequestSentOutGoodsActivty.AddNewAddressRequest;

/**
 * 地址列表
 */

public class UserAddressListActivity extends BaseTitleBarActivity {
    @BindView(R.id.user_address_recycler_view)
    RecyclerView userAddressRecyclerView;
    private UserAddressAdapter userAddressAdapter;
    private List<UserAddressBean.DataBean> addressesBeanList = new ArrayList<>();
    private UserAddressBean.DataBean currentUserAddress;

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    public void initData() {
        init();
        initListener();
    }

    private void init() {
        userAddressAdapter = new UserAddressAdapter(addressesBeanList, this);
        userAddressRecyclerView.setAdapter(userAddressAdapter);
        userAddressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getUserAddressList();
    }

    private void initListener() {
        userAddressAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                showDeleteAddressDialog(position);
                return false;
            }
        });

        Intent intent = getIntent();
        int flag = 0;
        if (intent != null) {
            flag = intent.getIntExtra("from", 0);
        }
        if (flag == 0) {
            userAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    Intent intent = new Intent();
                    intent.putExtra("name", addressesBeanList.get(i).getName());
                    intent.putExtra("mobile", addressesBeanList.get(i).getPhoneNo());
                    intent.putExtra("address", addressesBeanList.get(i).getAddress());
                    intent.putExtra("id", addressesBeanList.get(i).getId());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
        }

        userAddressAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (view.getId() == R.id.modify_address_txt) {
                    currentUserAddress = addressesBeanList.get(i);
                    if (currentUserAddress != null) {
                        Intent intent = new Intent(UserAddressListActivity.this, AddNewAddressActivty.class);
                        intent.putExtra("UserAddressBean", currentUserAddress);
                        intent.putExtra("ModifyAddress", true);
                        startActivityForResult(intent, AddNewAddressRequest);
                    }
                }
            }
        });
    }

    /**
     * 删除邮寄地址 弹出框
     */
    private void showDeleteAddressDialog(final int position) {
        commDialog = new CommDialog(this, "", getString(R.string.delete_address_sure), false);
        commDialog.setCanceledOnTouchOutside(false);
        commDialog.show();
        commDialog.setLeftButtonText(getString(R.string.comm_tips_cancel));
        commDialog.setRightButtonText(getString(R.string.comm_tips_confirm));
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
                commDialog.dismiss();
                getUserAddressDelete(addressesBeanList.get(position).getId());
            }
        });
    }

    @OnClick(R.id.add_user_address_layout)
    public void addUserAddressOnClick() {
        Intent intent = new Intent(this, AddNewAddressActivty.class);
        startActivityForResult(intent, AddNewAddressRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (addressesBeanList != null && addressesBeanList.size() > 0) {
            UserAddressBean.DataBean addressesBean = addressesBeanList.get(0);
            intent.putExtra("name", addressesBean.getName());
            intent.putExtra("mobile", addressesBean.getPhoneNo());
            intent.putExtra("address", addressesBean.getAddress());
            intent.putExtra("id", addressesBean.getId());
        }
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * 删除地址
     *
     * @param addressId
     */
    private void getUserAddressDelete(int addressId) {
        HttpMethods.getInstance().getUserAddressDelete(addressId, new Subscriber<BaseObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseObject baseObject) {
                ToastUtil.showToast(baseObject.getMsg());
                getUserAddressList();
            }
        });
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
                addressesBeanList = userAddressBean.getData();
                userAddressAdapter.setNewData(addressesBeanList);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AddNewAddressRequest://选择地址
                    getUserAddressList();
                    break;
            }
        }
    }
}