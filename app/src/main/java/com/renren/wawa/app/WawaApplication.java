package com.renren.wawa.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.LocalBroadcastManager;

import com.renren.wawa.R;
import com.renren.wawa.activity.SigninActivity;
import com.renren.wawa.config.Constants;
import com.renren.wawa.manager.BuglyUpgrade;
import com.renren.wawa.manager.GameManager;
import com.renren.wawa.manager.UserManager;
import com.renren.wawa.push.PushUtil;
import com.renren.wawa.sp.CommSetting;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.MessageObservable;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.view.CommClassicsFooter;
import com.renren.wawa.view.CommRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMLogLevel;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.TIMOfflinePushSettings;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.renren.wawa.config.Constants.WE_CHAT_APP_ID;

public class WawaApplication extends MultiDexApplication {
    private static WawaApplication instance;
    private static Context mContext;
    private static IWXAPI mWxApi;
    protected ArrayList<Activity> mAcitivitylist;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new CommRefreshHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new CommClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBroadReceiver();
        instance = this;
        mContext = getApplicationContext();

        GameManager.initialize(this);

        if (shouldInit()) {
            mAcitivitylist = new ArrayList<>();
            WawaApplication.initApp(this);
            //初始化程序后台后消息推送
            PushUtil.getInstance();
        }

        //bugly update
        BuglyUpgrade.buglyUpgradeInit();

        registToWX();
    }

    public static WawaApplication getInstance() {
        return instance;
    }

    public SharedPreferences getCommSharedPreferences(String tbl) {
        return getSharedPreferences(tbl, Context.MODE_PRIVATE);
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 判断是否是主进程，防止重复初始化
     *
     * @return
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();

        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 微信开放平台 注册
     */
    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, WE_CHAT_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(WE_CHAT_APP_ID);
    }

    /**
     * 获取微信 IWXAPI
     *
     * @return
     */
    public IWXAPI getmWxApi() {
        return mWxApi;
    }

    public static void initApp(final Context context) {
        TIMManager.getInstance().setLogLevel(TIMLogLevel.OFF);
        ILiveSDK.getInstance().initSdk(context, Constants.TENCENT_APP_ID, Constants.TENCENT_ACCOUNT_TYPE);
        ILVLiveManager.getInstance().init(new ILVLiveConfig().setLiveMsgListener(MessageObservable.getInstance()));

        TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
        //开启离线推送
        settings.setEnabled(true);
        TIMManager.getInstance().configOfflinePushSettings(settings);
        TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
            @Override
            public void handleNotification(TIMOfflinePushNotification notification) {
                BBLog.e(Constants.TAG, "notification " + notification.toString());
                if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                    //消息被设置为需要提醒
                    //notification.doNotify(getApplication(), R.mipmap.ic_launcher);
                    String senderStr, contentStr, url = null, ext;
                    senderStr = notification.getSenderIdentifier();
                    contentStr = notification.getContent();
                    ext = new String(notification.getExt());
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(ext);
                        url = jsonObject.getString("url");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (StringUtil.isBlank(senderStr)) {
                        senderStr = getContext().getResources().getString(R.string.app_name);
                    }

                    BBLog.e(Constants.TAG, "senderStr " + senderStr + " contentStr " + contentStr + " url " + url + " Title " + notification.getTitle());
                    PushUtil.pushNotification(senderStr, contentStr, url);
                }
            }
        });
    }

    /**
     * 初始化广播
     */
    private void initBroadReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BROADCAST_LOGOUT);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    public void popActivityFromCustomStack(Activity activity) {
        mAcitivitylist.remove(activity);
    }

    public void finishAllActivity() {
        if (mAcitivitylist != null) {
            for (Activity activity : mAcitivitylist) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
            mAcitivitylist.clear();
        }
    }

    public void pushActivityToCustomStack(Activity activity) {
        mAcitivitylist.add(activity);
    }

    /**
     * 登出的接收器
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.BROADCAST_LOGOUT.equals(intent.getAction())) {//登出
                // 需要在清理用户数据之前调用, 内部会获取用户的部分数据
                BBLog.e(Constants.TAG, "接收登出广播");
                //appLogout();
                UserManager.deleteCurUserInfo();
                CommSetting.setToken("");
                finishAllActivity();
                Intent loginIntent = new Intent(getInstance(), SigninActivity.class);
                loginIntent.putExtra("logout", true);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getInstance().startActivity(loginIntent);
            }
        }
    };
}
