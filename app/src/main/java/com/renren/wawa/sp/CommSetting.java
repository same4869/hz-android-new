package com.renren.wawa.sp;


public class CommSetting {
    public static final String SETTING = "setting";
    private static final String PREFERENCE_KEY_NAME_TOKEN = "com.renren.wawaji.preference.token";

    private static final String SETTING_BACKGROUND_MUSIC = "setting_background_music";//背景音乐
    private static final String SETTING_SOUND_EFFECT = "setting_sound_effect";//音效
    private static final String DEVELOPER_MODE = "developer_mode";//开发者模式
    private static final String SCHEME_PAGE = "scheme_page"; // 页面跳转

    public static String getToken() {
        return PrefsMgr.getString(SETTING, PREFERENCE_KEY_NAME_TOKEN, null);
    }

    public static void setToken(String token) {
        PrefsMgr.putString(SETTING, PREFERENCE_KEY_NAME_TOKEN, token);
    }

    /*设置 背景音乐和音效 start*/
    public static void setSettingBackgroundMusic(boolean backgroundMusic) {
        PrefsMgr.putBoolean(SETTING, SETTING_BACKGROUND_MUSIC, backgroundMusic);
    }

    public static boolean getSettingBackgroundMusic() {
        return PrefsMgr.getBoolean(SETTING, SETTING_BACKGROUND_MUSIC, true);
    }

    public static void setSettingSoundEffect(boolean soundEffect) {
        PrefsMgr.putBoolean(SETTING, SETTING_SOUND_EFFECT, soundEffect);
    }

    public static boolean getSettingSoundEffect() {
        return PrefsMgr.getBoolean(SETTING, SETTING_SOUND_EFFECT, true);
    }
    /*设置 背景音乐和音效 end*/

    // 页面跳转
    public static void saveSchemePage(String schemeUrl) {
        PrefsMgr.putString(SETTING, SCHEME_PAGE, schemeUrl);
    }

    public String getSchemePage() {
        return PrefsMgr.getString(SETTING, SCHEME_PAGE, null);
    }
}
