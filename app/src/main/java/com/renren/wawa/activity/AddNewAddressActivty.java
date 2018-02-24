package com.renren.wawa.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.renren.wawa.R;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.model.BaseObject;
import com.renren.wawa.model.UserAddressBean;
import com.renren.wawa.model.UserAddressCreate;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.AddressPickTask;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import rx.Subscriber;

/**
 * 新建地址
 */

public class AddNewAddressActivty extends BaseTitleBarActivity {
    @BindView(R.id.user_name_edit)
    EditText userNameEdit;
    @BindView(R.id.user_phone_edit)
    EditText userPhoneEdit;
    @BindView(R.id.user_province)
    TextView userProvince;
    @BindView(R.id.user_city)
    TextView userCity;
    @BindView(R.id.user_detailed_address_edit)
    EditText userDetailedAddressEdit;
    @BindView(R.id.user_count)
    TextView userCount;

    private String provinceData;
    private String cityData;
    private String countyData;
    private boolean modifyAddress = false;
    private UserAddressBean.DataBean currentUserAddress;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_new_address;
    }

    @Override
    public void initData() {
        init();
    }


    private void init() {
        modifyAddress = getIntent().getBooleanExtra("ModifyAddress", false);
        if (modifyAddress) {
            getTitleBar().setTitleBarText(getString(R.string.modify_address));
            currentUserAddress = (UserAddressBean.DataBean) getIntent().getSerializableExtra("UserAddressBean");
            if (currentUserAddress != null) {
                String addressData = currentUserAddress.getAddress();
                if (StringUtil.isBlank(addressData)) {
                    {
                        return;
                    }
                }
                String[] addressArray = addressData.split("\\|");
                if (addressArray.length == 4) { //老的
                    for (int i = 0; i < addressArray.length; i++) {
                        switch (i) {
                            case 0:
                                userProvince.setText(addressArray[0]);
                                provinceData = addressArray[0];
                                break;
                            case 1:
                                userCity.setText(addressArray[1]);
                                break;
                            case 2:
                                userCount.setText(addressArray[2]);
                                break;
                            case 3:
                                userDetailedAddressEdit.setText(addressArray[3]);
                                break;
                            default:
                                break;

                        }
                    }
                } else if (addressArray.length == 2) { // 新格式
                    for (int i = 0; i < addressArray.length; i++) {
                        switch (i) {
                            case 0:
                                String[] provinceCityCountArray = addressArray[i].split(" ");
                                for (int j = 0; j < provinceCityCountArray.length; j++) {
                                    switch (j) {
                                        case 0:
                                            userProvince.setText(provinceCityCountArray[j]);
                                            provinceData = provinceCityCountArray[j];
                                            break;
                                        case 1:
                                            userCity.setText(provinceCityCountArray[j]);
                                            break;
                                        case 2:
                                            userCount.setText(provinceCityCountArray[j]);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                break;
                            case 1:
                                userDetailedAddressEdit.setText(addressArray[i]);
                                break;
                            default:
                                break;
                        }
                    }
                }

                userProvince.setTextColor(getResources().getColor(R.color.black));
                userCity.setTextColor(getResources().getColor(R.color.black));
                userCount.setTextColor(getResources().getColor(R.color.black));
                userNameEdit.setText(currentUserAddress.getName());
                userPhoneEdit.setText(currentUserAddress.getPhoneNo());
            }


        }

    }

    @OnClick(R.id.user_district_layout)
    public void userDistrictOnClick() {
        onAddressPicker();
    }


    /**
     * 地址选择器
     */
    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                provinceData = province.getAreaName();
                cityData = city.getAreaName();
                if (county != null) {
                    countyData = county.getAreaName();
                } else {
                    countyData = "";
                }
                userProvince.setText(provinceData);
                userCity.setText(cityData);
                userCount.setText(countyData);
                userProvince.setTextColor(getResources().getColor(R.color.black));
                userCity.setTextColor(getResources().getColor(R.color.black));
                userCount.setTextColor(getResources().getColor(R.color.black));
            }
        });
        task.execute("");
    }


    @Override
    public void menuListener(View v) {
        super.menuListener(v);
        String userNameData = userNameEdit.getText().toString();
        String userPhoneData = userPhoneEdit.getText().toString();
        String userDetailedAddressData = userDetailedAddressEdit.getText().toString();
        if (StringUtil.isBlank(userNameData)) {
            ToastUtil.showToast("联系人不能为空");
            return;
        }
        if (StringUtil.isBlank(userPhoneData)) {
            ToastUtil.showToast("联系电话不能为空");
            return;
        } else if (!StringUtil.checkMobile(userPhoneData)) {
            ToastUtil.showToast("联系电话格式不对");
        }

        if (StringUtil.isBlank(provinceData)) {
            ToastUtil.showToast("所在地区不能为空");
            return;
        }

        if (StringUtil.isBlank(userDetailedAddressData)) {
            ToastUtil.showToast("详细地址不能为空");
            return;
        }

        if (modifyAddress) {
            modifyAddress();
        } else {
            creatUserAddress();
        }
    }

    /**
     * 创建一个新的地址
     */
    private void creatUserAddress() {
        final StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(provinceData).append(" ").append(cityData).append(" ").append(countyData).append("|").append(userDetailedAddressEdit.getText().toString());
        HttpMethods.getInstance().getUserAddressCreate(new Subscriber<UserAddressCreate>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                BBLog.e(e.toString());
            }

            @Override
            public void onNext(UserAddressCreate userAddressCreate) {
                if (userAddressCreate != null && userAddressCreate.isSucceed()) {
                    Intent intent = new Intent();
                    intent.putExtra("name", userNameEdit.getText().toString());
                    intent.putExtra("mobile", userPhoneEdit.getText().toString());
                    intent.putExtra("address", addressBuilder.toString());
                    intent.putExtra("id", Integer.valueOf(userAddressCreate.getData().getId()));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        }, addressBuilder.toString(), userNameEdit.getText().toString(), userPhoneEdit.getText().toString());
    }

    /**
     * 修改地址
     */
    private void modifyAddress() {
        final StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(userProvince.getText().toString()).append(" ").append(userCity.getText().toString()).append(" ").append(userCount.getText().toString()).append("|").append(userDetailedAddressEdit.getText().toString());
        HttpMethods.getInstance().getUserAddressModify(addressBuilder.toString(), userNameEdit.getText().toString(), userPhoneEdit.getText().toString(), currentUserAddress.getId(), new Subscriber<BaseObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseObject baseObject) {
                ToastUtil.showToast("修改地址成功");
                Intent intent = new Intent();
                intent.putExtra("name", userNameEdit.getText().toString());
                intent.putExtra("mobile", userPhoneEdit.getText().toString());
                intent.putExtra("address", addressBuilder.toString());
                intent.putExtra("id", currentUserAddress.getId());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
