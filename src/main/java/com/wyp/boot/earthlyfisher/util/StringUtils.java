package com.wyp.boot.earthlyfisher.util;

/**
 * Created by earthlyfisher on 2017/3/16.
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String string) {
        if (string == null || string.trim().equals("")) {
            return true;
        }
        return false;
    }
}
