package com.jfree.util;

/**
 * StringUtil
 */
public class StringUtil {

    /**
     * 判断空字符串
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (str == null || str.trim().length() <= 0) return true;
        return false;
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String toStartLowerCase(String str){
        char[] cs = str.toCharArray();
        cs[0] = cs[0] < 32 ? (char) (cs[0] + 32) : cs[0];
        return String.valueOf(cs);
    }

}