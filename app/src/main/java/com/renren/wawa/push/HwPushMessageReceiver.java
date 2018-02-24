package com.renren.wawa.push;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.huawei.android.pushagent.api.PushEventReceiver;

import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 华为推送接收
 */
public class HwPushMessageReceiver extends PushEventReceiver {
    private final String TAG = "HwPushMessageReceiver";

    private long mBussId = 3058;


    @Override
    public void onToken(Context context, String token, Bundle extras){
        String belongId = extras.getString("belongId");
        String content = "获取token和belongId成功，token = " + token + ",belongId = " + belongId;
        BBLog.e(TAG, content);
        TIMOfflinePushToken param = new TIMOfflinePushToken();
        param.setToken(token);
        param.setBussid(mBussId);
        TIMManager.getInstance().setOfflinePushToken(param);
    }


    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            String content = "收到一条Push消息： " + new String(msg, "UTF-8");
            BBLog.e(TAG, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
            String content = "收到通知附加消息： " + extras.getString(BOUND_KEY.pushMsgKey);
            BBLog.e(TAG, content);


            String url = null,msg;

            msg = extras.getString(BOUND_KEY.pushMsgKey);
            BBLog.e(TAG,"ext "+msg);


            JSONObject jsonObject = null;
            try {
                JSONArray jsonArray = new JSONArray(msg);
                for(int i = 0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    BBLog.e(TAG,"jsonObject "+jsonObject);
                    String ext = jsonObject.getString("ext");
                    JSONObject object = new JSONObject(ext);
                    url  = object.getString("url");
                    if(StringUtil.isNotBlank(url)){
                        BBLog.e(TAG,"url "+url);
                        break;
                    }
                }

            } catch (JSONException e) {
                BBLog.e(TAG,"e "+e.toString());
                e.printStackTrace();
            }
            if(StringUtil.isNotBlank(url)){
                Uri uri = Uri.parse(url);
                Intent notificationIntent = new Intent(Intent.ACTION_VIEW,
                        uri);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(notificationIntent);
            }

        } else if (Event.PLUGINRSP.equals(event)) {
            final int TYPE_LBS = 1;
            final int TYPE_TAG = 2;
            int reportType = extras.getInt(BOUND_KEY.PLUGINREPORTTYPE, -1);
            boolean isSuccess = extras.getBoolean(BOUND_KEY.PLUGINREPORTRESULT, false);
            String message = "";
            if (TYPE_LBS == reportType) {
                message = "LBS report result :";
            } else if(TYPE_TAG == reportType) {
                message = "TAG report result :";
            }
            BBLog.e(TAG, message + isSuccess);
        }
        super.onEvent(context, event, extras);
    }
}
