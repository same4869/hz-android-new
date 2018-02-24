package com.renren.wawa.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


import com.huawei.android.pushagent.api.PushManager;
import com.wawaji.vip.R;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMMessage;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import static com.renren.wawa.config.Constants.MIPUSH_APPID;
import static com.renren.wawa.config.Constants.MIPUSH_APPKEY;


/**
 * 在线消息通知展示
 */
public class PushUtil implements Observer {


    private static int pushNum = 0;

    private static final int pushId = 1;

    private static PushUtil instance = new PushUtil();

    private PushUtil() {
        MessageEvent.getInstance().addObserver(this);
    }

    public static PushUtil getInstance() {
        return instance;
    }


    private void PushNotify(TIMMessage msg) {
        BBLog.e(Constants.TAG, "msg " + msg.getCustomStr() + " " + msg.getSender().toString());
        if (msg == null || Foreground.get().isForeground() ||
                (msg.getConversation().getType() != TIMConversationType.Group &&
                        msg.getConversation().getType() != TIMConversationType.C2C) ||
                msg.isSelf() ||
                msg.getRecvFlag() == TIMGroupReceiveMessageOpt.ReceiveNotNotify) {
            return;
        }


        String senderStr, contentStr ;
        TextMessage message = new TextMessage(msg);

        if (message == null) {
            return;
        }

        String ext ;
        JSONObject jsonObject = null;
        String url = "";
        if(msg.getOfflinePushSettings()!=null&&msg.getOfflinePushSettings().getExt()!=null){
            ext = new String(msg.getOfflinePushSettings().getExt());
            try {
                jsonObject = new JSONObject(ext);
                url = jsonObject.getString("url");
                BBLog.e(Constants.TAG,"url "+url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        senderStr = message.getSender();
        contentStr = message.getSummary();
        if(StringUtil.isNotBlank(senderStr)){
//            if("nitifyer".equals(senderStr)){//入库
//                BBLog.e(Constants.TAG," nitifyer ");
//               // url = "wawaji://"+SCHEME_NOTIFICATION_CENTER;
//            }else if("notifyer".equals(senderStr)){// 不入库
//                BBLog.e(Constants.TAG," notifyer ");
//            }else{
//                return;
//            }
            senderStr = WawaApplication.getContext().getResources().getString(R.string.app_name);
        }else{
            BBLog.e(Constants.TAG," 发送者不正确  senderStr "+senderStr);
            return;
        }
        pushNotification(senderStr,contentStr,url);
    }

    public static void resetPushNum() {
        pushNum = 0;
    }

    public void reset() {
        NotificationManager notificationManager = (NotificationManager) WawaApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(pushId);
    }

    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link Observable} object.
     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
     */
    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent) {
            TIMMessage msg = (TIMMessage) data;
            if (msg != null) {
                PushNotify(msg);
            }
        }
    }

    /**
     *
     */
    public static void pushNotification(String senderStr,String contentStr,String url){
        if(StringUtil.isBlank(url)){
            url = "wwj://app.renrenzww.com";
        }
        BBLog.e(Constants.TAG," url "+url);
        NotificationManager mNotificationManager = (NotificationManager) WawaApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(WawaApplication.getContext());
         Uri uri = Uri.parse(url);
        BBLog.e(Constants.TAG," uri "+uri.toString());

        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, uri);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(WawaApplication.getContext(), 0,
                notificationIntent, 0);
        mBuilder.setContentTitle(senderStr)//设置通知栏标题
                .setContentText(contentStr)
                .setContentIntent(intent) //设置通知栏点击意图
//                .setNumber(++pushNum) //设置通知集合的数量
                .setTicker(senderStr+":"+contentStr) //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //.setFullScreenIntent(intent, false) // 横幅
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON

//        String vendor = Build.MANUFACTURER;
//        if(!vendor.toLowerCase(Locale.ENGLISH).contains("xiaomi")&&!vendor.toLowerCase(Locale.ENGLISH).contains("huawei")) {
//            mBuilder.setFullScreenIntent(intent, false)
//        }


        Notification notify = mBuilder.build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(pushId, notify);
    }



    /**
     * 推送注册
     */
    public static void registerPush(){
        String vendor = Build.MANUFACTURER;
        if(vendor.toLowerCase(Locale.ENGLISH).contains("xiaomi")) {
            //注册小米推送服务
            MiPushClient.registerPush(WawaApplication.getInstance(), MIPUSH_APPID, MIPUSH_APPKEY);
        }else if(vendor.toLowerCase(Locale.ENGLISH).contains("huawei")) {
            //请求华为推送设备token
            PushManager.requestToken(WawaApplication.getInstance());
        }
    }
}
