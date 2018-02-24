package com.renren.wawa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        if ("null".equalsIgnoreCase(str))
            return true;
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtil.isEmpty(str);
    }

    public static boolean isNotBlank(String str) {
        return !StringUtil.isBlank(str);
    }

    /**
     * 验证email的合法性
     *
     * @param emailStr
     * @return
     */
    public static boolean checkEmail(String emailStr) {
        String check = "^([a-z0-9A-Z]+[-|._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(emailStr.trim());
        boolean isMatched = matcher.matches();
        if (isMatched) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkMobile(String str) {
        Pattern p = Pattern.compile("[1-9]{1}[0-9]{10}");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    // 字符串截断
    public static String getLimitString(String source, int length){
        if (null == source){
            return "";
        }else if (source.length()>length){
            return source.substring(0, length).replace('\n', ' ')+"...";
        }else {
            return source.replace('\n', ' ');
        }
    }
}
