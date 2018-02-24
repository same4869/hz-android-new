package com.renren.wawa.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.interfaces.RoomObserver;
import com.renren.wawa.manager.GameManager;
import com.renren.wawa.model.RoomStatusUpdate;
import com.renren.wawa.model.RoomUserMessage;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.CommLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * fragment基类
 */

public abstract class  BaseFragment extends Fragment implements RoomObserver {
    private boolean isDestroyed = false;
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    protected CommLoadingDialog loadingDialog;

    private final static int BROADCAST_MESSAGE = 1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case BROADCAST_MESSAGE:
                    BBLog.d(Constants.TAG, "BaseTitleBarActivity global broadcast: " + message.obj);
                    RoomUserMessage roomUserMessage = (RoomUserMessage) message.obj;
                    ToastUtil.showToastImageText(roomUserMessage.getNickname(),roomUserMessage.getAvatar());
                    break;
            }
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        isViewPrepared = true;
        View rootView = inflater.inflate(getLayoutId(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViews();
        lazyFetchDataIfPrepared();
        GameManager.getInstance().registerObserver(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        GameManager.getInstance().unregisterObserver(this);
        isViewPrepared = false;
        hasFetchData = false;
    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true; //已加载过数据
            lazyFetchData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {//当当前为显示页面时
            lazyFetchDataIfPrepared();
        }
    }


    public abstract int getLayoutId();

    public abstract void lazyFetchData();

    public abstract void initViews();

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(WawaApplication.getInstance());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(WawaApplication.getInstance());
    }

    @Override
    public void onBroadcastReceived(RoomUserMessage roomUserMessage) {
        BBLog.e(Constants.TAG,"BaseFragment 抓到娃娃通知");
        Message msg = new Message();
        msg.what = BROADCAST_MESSAGE;
        msg.obj = roomUserMessage;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onRoomStateUpdated(RoomStatusUpdate roomStatusUpdate) {

    }

    @Override
    public void onRoomMemberUpdated(boolean isJoin, RoomUserMessage roomUserMessage) {

    }

    public void showLoadingDialog() {
        showLoadingDialog(null);
    }

    public void showLoadingDialog(String message) {
        showLoadingDialog(message, true);
    }

    public void showLoadingDialog(String message, boolean cancelable) {
        if (isActivityDestroyed()) {
            return;
        }

        cancelLoadingDialog();

        loadingDialog = new CommLoadingDialog(getActivity());
        loadingDialog.show();
        if (message != null) {
            loadingDialog.setContentMessage(message);
        }
        loadingDialog.setCancelable(cancelable);
    }

    public void cancelLoadingDialog() {
        if (isActivityDestroyed()) {
            loadingDialog = null;
            return;
        }
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
        loadingDialog = null;
    }

    public boolean isActivityDestroyed() {
        return isDestroyed;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
    }
}
