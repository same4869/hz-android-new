package com.renren.wawa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wawaji.vip.R;
import com.renren.wawa.activity.MyDollActivity;
import com.renren.wawa.activity.ScratchRecordActivity;
import com.renren.wawa.activity.SettingNotificationActivity;
import com.renren.wawa.activity.UserAddressListActivity;
import com.renren.wawa.model.UserInfo;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.view.CommRoundAngleImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;

public class UserFragment extends BaseFragment {
    @BindView(R.id.user_img)
    CommRoundAngleImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_scratch_record)
    LinearLayout userScratchRecord;
    @BindView(R.id.user_scratched_wawa)
    LinearLayout userScratchedWawa;
    @BindView(R.id.user_address_manage)
    LinearLayout userAddressManage;
    @BindView(R.id.user_notification)
    LinearLayout userNotification;
//    @BindView(R.id.user_scratch_record)
//    CommFuctionEntryBar userScratchRecord;
//    @BindView(R.id.user_scratched_wawa)
//    CommFuctionEntryBar userScratchedWawa;
//    @BindView(R.id.user_address_manage)
//    CommFuctionEntryBar userAddressManage;
//    @BindView(R.id.user_notification)
//    CommFuctionEntryBar userNotification;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user;
    }


    @OnClick(R.id.user_notification)
    public void userNotificationClick() {
        Intent intent = new Intent(getActivity(), SettingNotificationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.user_scratch_record)
    public void scratchRecordClick() {
        Intent intent = new Intent(getActivity(), ScratchRecordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.user_address_manage)
    public void addressManageClick() {
        Intent intent = new Intent(getActivity(), UserAddressListActivity.class);
        intent.putExtra("from", 1);
        startActivity(intent);
    }

    @OnClick(R.id.user_scratched_wawa)
    public void scratchedWawaClick() {
        Intent intent = new Intent(getActivity(), MyDollActivity.class);
        startActivity(intent);
    }

    @Override
    public void lazyFetchData() {
        getUserInfo(0);
    }

    @Override
    public void initViews() {

    }

    /**
     * 获取当前用户的 profile
     */
    private void getUserInfo(long userId) {
        showLoadingDialog();
        HttpMethods.getInstance().getUserInfo(userId, new Subscriber<UserInfo>() {
            @Override
            public void onCompleted() {
                cancelLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                cancelLoadingDialog();
            }

            @Override
            public void onNext(UserInfo userInfo) {
                if (userInfo != null && userInfo.isSucceed()) {
                    if (StringUtil.isNotBlank(userInfo.getData().getAvatar())) {
                        Picasso.with(getActivity()).load(userInfo.getData().getAvatar()).into(userImg);

                    }
                    userName.setText(userInfo.getData().getNickname() + "(ID:" + userInfo.getData().getId() + ")");
                }
            }
        });
    }

}
