package com.renren.wawa.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.renren.wawa.activity.CommWebActivity;
import com.renren.wawa.activity.GameDetailActivity;
import com.renren.wawa.activity.GameRoomActivity;
import com.renren.wawa.activity.MyDollActivity;
import com.renren.wawa.activity.SettingMoneyActivity;
import com.renren.wawa.activity.SplashActivity;
import com.renren.wawa.activity.WawaMainActivity;
import com.renren.wawa.config.Constants;
import com.renren.wawa.fragment.RoomFragment;
import com.renren.wawa.sp.CommSetting;
import com.renren.wawa.utils.BBLog;

/**
 * 外部跳转页面
 */

public class CommonInvokerActivity extends BaseActivity {


    public static final String SCHEME_APPLY_INVITE_CODE = "wawaji://claw.same.com/apply_invite_code/0";//邀请码界面：
    public static final String SCHEME_INPUT_APPLY_INVITE_CODE = "wawaji://claw.same.com/apply_invite_code/1";// 输入邀请码界面：

    public static final String SCHEME_USER = "wawaji://claw.same.com/user/";// 用户界面：uid 用户uid
    public static final String SCHEME_WALLET = "wwj://app.renrenzww.com/wallet";// 喵喵币界面：

    public static final String SCHEME_FEEDBACK = "wawaji://claw.same.com/feedback";//  问题反馈二维码界面：
    public static final String SCHEME_NOTIFICATIONCENTER = "wawaji://claw.same.com/notificationCenter";// 通知中心界面：

    public static final String SCHEME_SESSION = "wawaji://claw.same.com/session/";//  游戏记录详情界面：
    public static final String SCHEME_PRODUCT_ORDER_ITEM_EXCHANGE_TO_COIN = "wawaji://claw.same.com/product-order-item?action=exchange-to-coin";// 兑换喵喵币弹窗：

    public static final String SCHEME_PRODUCT_ORDER_ITEM_REQUEST_SHIPPING = "wawaji://claw.same.com/product-order-item?action=request-shipping";//  请求发货界面：
    public static final String SCHEME_PRODUCT_ORDER_ITEM = "wawaji://claw.same.com/product-order-item";// 兑换喵喵币弹窗：

    //public static final String SCHEME_RECHARGE = "wawaji://claw.same.com/recharge";//跳转支付 弹窗
    public static final String SCHEME_RECHARGE = "renren://claw.com/recharge";//跳转支付 弹窗

    //renren://claw.com/recharge
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        BBLog.e(Constants.TAG, "CommonInvokerActivity ");

        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri == null) {
            finish();
            return;
        } else {
            BBLog.e(Constants.TAG, "uri " + uri.toString());
        }
        if (RoomFragment.isLoginFlag()) {
            boolean flag = handleScheme(uri.toString());
            if (flag) {
                finish();
            }
        } else {
            CommSetting.saveSchemePage(uri.toString());
            startActivity(new Intent(CommonInvokerActivity.this, SplashActivity.class));
            finish();
        }
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    /**
     * 处理跳转
     *
     * @param url
     * @return
     */
    public boolean handleScheme(String url) {
        Intent intent = new Intent(this, WawaMainActivity.class);
        startActivity(intent);

//        if (url.contains(SCHEME_APPLY_INVITE_CODE)) {
//            Intent intent = new Intent(this, SettingInvitationAwardActivity.class);
//            startActivity(intent);
//            return true;
//        } else if (url.contains(SCHEME_INPUT_APPLY_INVITE_CODE)) {
//            Intent intent = new Intent(this, SettingInputInvitationCodeActivity.class);
//            startActivity(intent);
//            return true;
//        } else if (url.contains(SCHEME_USER)) {
//            Intent intent = new Intent(this, MyDollActivity.class);
//            startActivity(intent);
//            return true;
//        }else
//        if (url.contains(SCHEME_WALLET)) {
//            Intent intent = new Intent(this, SettingMoneyActivity.class);
//            startActivity(intent);
//            return true;
//        }
// else if (url.contains(SCHEME_FEEDBACK)) {
//            Intent intent = new Intent(this, FeedBackActivity.class);
//            startActivity(intent);
//            return true;
//        }else if (url.contains(SCHEME_SESSION)) {
//            Intent intent = new Intent(this, GameDetailActivity.class);
//            int gameId = Integer.parseInt(url.replace(SCHEME_SESSION,""));
//            intent.putExtra(GAME_ID,gameId);
//            startActivity(intent);
//            return true;
//        }
//        else if (url.contains(SCHEME_RECHARGE)) {
//            //loadChargeList();
//            //return false;
//        }
//        else if (url.contains(SCHEME_NOTIFICATION_CENTER)) {
//            Intent intent = new Intent(this, SettingNotificationActivity.class);
//            startActivity(intent);
//            return true;
//        }
//        else if (url.contains(SCHEME_COUPON)) {
//            Intent intent = new Intent(this, SettingCoupousActivity.class);
//            startActivity(intent);
//            return true;
//        }else if(url.contains("https://")||url.contains("http://")){
//            Intent intent = new Intent(this, CommWebActivity.class);
//            intent.putExtra(CommWebActivity.COMM_WEB_URL, url);
//            startActivity(intent);
//        }else if (url.contains(SCHEME_ROOM)) {
//            Uri uri = Uri.parse(url);
//            String roomId = uri.getQueryParameter("roomId");
//            Intent intent = new Intent(this, GameRoomActivity.class);
//            intent.putExtra("room_id", roomId);
//            intent.putExtra("is_jump", true);
//            startActivity(intent);
//            return true;
//        }else if (url.contains(SCHEME_KEFU)) {
//
//            return true;
//        }

        return true;
    }
}
