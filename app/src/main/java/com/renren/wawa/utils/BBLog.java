package com.renren.wawa.utils;

import android.text.TextUtils;
import android.util.Log;

import com.renren.wawa.config.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BBLog {

    private static final int JSON_INDENT = 2;
    private static String mTagPrefix = "hwwj"; // 自定义Tag的前缀，可以是作者名

    private BBLog() {
    }

    public static boolean isDebug() {
        return Constants.isDebug;
    }

    public static void d(String msg) {
        if (isDebug()) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug()) {
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug()) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug()) {
            Log.i(tag, msg);
        }
    }

    public static void w(String msg) {
        if (isDebug()) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.w(tag, msg);
        }
    }

    public static void w(Throwable throwable) {
        if (isDebug()) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.w(tag, throwable);
        }
    }


    public static void w(String tag, String msg) {
        if (isDebug()) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, Throwable throwable) {
        if (isDebug()) {
            Log.w(tag, throwable);
        }
    }

    public static void e(String msg) {
        if (isDebug()) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.e(tag, msg);
        }
    }

    public static void e(Throwable throwable) {
        if (isDebug()) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.e(tag, " the throwable is ", throwable);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug()) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, Exception msg) {
        if (isDebug()) {
            Log.e(tag, msg.toString());
        }
    }

    public static void e(String tag, Throwable throwable) {
        if (isDebug()) {
            Log.e(tag, " the throwable is ", throwable);
        }
    }

    public static void json(String json) {
        if (isDebug()) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            if (TextUtils.isEmpty(json)) {
                d(tag, "Empty/Null json content");
                return;
            }
            try {
                json = json.trim();
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    json = jsonObject.toString(JSON_INDENT);
                }
                if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    json = jsonArray.toString(JSON_INDENT);
                }
                d(tag, json);
            } catch (JSONException e) {
                e(tag, json);
            }
        }
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s()"; // 占位符
        try {
            String callerClazzName = caller.getClassName(); // 获取到类名
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
            tag = String.format(tag, callerClazzName, caller.getMethodName()); // 替换
        } catch (IndexOutOfBoundsException e) {
            Log.d(mTagPrefix, e.toString());
            return mTagPrefix;
        }
        tag = TextUtils.isEmpty(mTagPrefix) ? tag : mTagPrefix + "," + tag;
        return tag;
    }

    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}
