package com.renren.wawa.utils;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.WindowManager;

import com.renren.wawa.app.WawaApplication;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AppUtil {
    private static long lastClickTime;

    // 时分秒的值
    private static long secondMillis = 1000;
    private static long minuteMillis = 60 * secondMillis;
    private static long hourMillis = 60 * minuteMillis;

    private static String channel = null;


    public static int dpToPx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dipValue * scale + 0.5f);
    }

    public static int spToPx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(800);
    }

    public static boolean isFastDoubleClick(int minTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < minTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 调起本地应用市场评论应用
     *
     * @param context
     * @param noMarket
     */
    public static void toScore(Context context, String noMarket) {
        try {
            StringBuffer sb = new StringBuffer().append("market://details?id=").append(context.getPackageName());
            Uri uri = Uri.parse(sb.toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.showToast(noMarket);
        }
    }


    /**
     * 获取当前进程名
     *
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String duration(Date from, Date to) {
        long interval = TimeUnit.MILLISECONDS.toSeconds(to.getTime() - from.getTime());
        if (interval < 60) {
            return String.format("刚刚");
        } else if (interval < 60 * 60) {
            return String.format("%d分钟前", interval / 60);
        } else if (interval < (3600 * 24)) {
            return String.format("%d小时前", interval / 3600);
        } else {
            return String.format("%d天前", interval / (3600 * 24));
        }
    }

    /**
     * 格式化一个毫秒值，如果时间大于或等于1小时，则格式化为时分秒，如：01:30:49，否则格式化为分和秒，如：30:49
     *
     * @param duration
     * @return
     */
    public static CharSequence formatMillis(long duration) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.add(Calendar.MILLISECOND, (int) duration);
        // kk代表小时中的1 ~ 24
        String pattern = duration / hourMillis > 0 ? "kk:mm:ss" : "mm:ss";
        return DateFormat.format(pattern, calendar);
    }

    public static int getScreenWidth(Context context) {
        return getScreenSize(context).x;
    }

    public static int getScreenHeight(Context context) {
        return getScreenSize(context).y;
    }

    public static Point getScreenSize(Context context) {
        Point point = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        return point;
    }

    public static int dp2px(int dp) {
        float density = WawaApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) WawaApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }

    /**
     * 获得channel信息
     *
     * @return
     */
    public static String getChannelByMeta() {
        if (channel != null) {
            return channel;
        }
        try {
            channel = getMetaValue(WawaApplication.getContext(), "UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (StringUtil.isBlank(channel)) {
            channel = "nochannel";
        }
        return channel;
    }

    /**
     * 根据meta key获得 value
     *
     * @param context
     * @param metaKey
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getMetaValue(Context context, String metaKey) throws PackageManager.NameNotFoundException {
        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                PackageManager.GET_META_DATA);
        return appInfo.metaData.getString(metaKey);
    }
}
