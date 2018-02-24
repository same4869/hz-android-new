package com.renren.wawa.config;

public class Constants {
    //是否是Debug 模式
    public static final boolean isDebug = false;
    public static final boolean isScreenRecord = false;

    public static final String TAG = "hwawaji-debug";
    public final static String BROADCAST_LOGOUT = "login_out";

    public static final String BASE_URL = "https://renrenzww.com/renren/";//"http://182.254.221.82/renren/";

    //微信开放平台 AppID
    public static final String WE_CHAT_APP_ID = "wx6f3ca1f2508dbca9";

    public static final int TENCENT_APP_ID = 1400059084 ;//1400045378;
    public static final int TENCENT_ACCOUNT_TYPE = 21129;//18060;

    public static final String MIPUSH_APPID = "2882303761517678491";
    public static final String MIPUSH_APPKEY = "5611767894491";
    public static final String MIPUSH_APPSECRET = " T5xYXU/zjKXmoChHSIpiFA==";

    //用户协议 URL
    public static final String USER_PROTOCOL_URL = "http://owsm9593l.bkt.clouddn.com/rules.html";
    public static final String USER_ABOUT_URL = "http://owsm9593l.bkt.clouddn.com/about.html";


    public static final int RESPONSE_STATUS_CODE_SUCCEED = 0;
    public static final int RESPONSE_STATUS_CODE_NOT_AUTH = 2;

    public final static int WE_CHAT_LOGIN_SUCCESS = 0x01;
    public final static int WE_CHAT_LOGIN_FAILED = 0x02;
    public final static int START_GAME = 0x03;
    public final static int END_GAME = 0x04;

    public static final int LIVE_CMD_UP_MACHINE = 2049;
    public static final int LIVE_CMD_SUCCEED = 2065;
    public static final int LIVE_CMD_FAILED = 2066;

}
