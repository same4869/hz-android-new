package com.renren.wawa.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.wawaji.vip.R;
import com.renren.wawa.config.Constants;
import com.renren.wawa.interfaces.RoomObserver;
import com.renren.wawa.manager.GameManager;
import com.renren.wawa.model.RoomStatusUpdate;
import com.renren.wawa.model.RoomUserMessage;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.CommTitleBarView;

import commlib.xun.com.commlib.handler.CommWeakHandler;

/**
 * 带titlebar的基类，带房间状态回调
 */

public abstract class BaseTitleBarActivity extends BaseActivity implements CommTitleBarView.CommTitleBarListener,RoomObserver{
    protected CommTitleBarView commTitleBarView;
    private final static int BROADCAST_MESSAGE = 1;

    private CommWeakHandler mHandler = new CommWeakHandler(new Handler.Callback() {
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

    @Override
    public void initViews(Bundle savedInstanceState) {
        initTitleBar();
        GameManager.getInstance().registerObserver(this);
    }

    private void initTitleBar() {
        commTitleBarView = (CommTitleBarView) findViewById(R.id.title_bar);
        if (commTitleBarView != null) {
            commTitleBarView.setCommTitleBarListener(this);
            commTitleBarView.setBackIcon(R.drawable.comm_back);
        }
    }

    public CommTitleBarView getTitleBar() {
        return commTitleBarView;
    }

    public void setBarTitle(CharSequence title) {
        if (title != null && commTitleBarView != null) {
            commTitleBarView.setTitleBarText(title.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GameManager.getInstance().unregisterObserver(this);
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBroadcastReceived(RoomUserMessage roomUserMessage) {
        BBLog.e(Constants.TAG,"BaseTitle 抓到娃娃通知");
        Message msg = new Message();
        msg.what = BROADCAST_MESSAGE;
        msg.obj = roomUserMessage;
        mHandler.sendMessage(msg);
    }

    @Override
    public void backListener(View v) {
        onBackPressed();
    }

    @Override
    public void menuListener(View v) {
    }

    @Override
    public void menu2Listener(View v) {
    }

    @Override
    public void onRoomStateUpdated(RoomStatusUpdate roomStatusUpdate) {
    }

    @Override
    public void onRoomMemberUpdated(boolean isJoin, RoomUserMessage roomUserMessage) {
    }
}
