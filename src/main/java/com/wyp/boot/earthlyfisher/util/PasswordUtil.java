package com.wyp.boot.earthlyfisher.util;

import java.security.MessageDigest;
import java.util.Random;

public class PasswordUtil {

    private static final String passwordSuffix = "pwd";

    private static final long SALT_LENGTH = 126L;

    /**
     * 16进制.
     */
    private final static String[] HEX_DIGITS =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                    "a", "b", "c", "d", "e", "f"};

    /**
     * 随机数.
     */
    private final static int RANDOM_CONSTATNT = 16;

    /*
         * 把inputString加密
         */
    public static String generatePassword(String inputString) {
        return encodeByMD5(inputString);
    }

    /*
     * 生成不同salt
     */
    public static String randomSalt() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            sb.append(HEX_DIGITS[r.nextInt(RANDOM_CONSTATNT)]);
        }
        return sb.toString();
    }

    /*
     * 加盐加密
     */
    public static String generatePassword(String inputString, String salt) {
        return encodeByMD5(inputString + salt);
    }

    public static boolean validatePassword(String password, String inputString) {
        if (password.equals(encodeByMD5(inputString))) {
            return true;
        } else {
            return false;
        }
    }

    public static String returnEncodeByMde(String originString) {
        return encodeByMD5(originString);
    }

    /**
     * 对字符串进行MD5加密
     */
    private static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(originString.getBytes("UTF-8"));
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest();
                // 将得到的字节数组变成字符串返回
                String resultString = byteArrayToHexString(results);
                String pass = resultString.toUpperCase();
                return pass;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }


    public static String encodePassword(String password) {

        return password + passwordSuffix;
    }

    public static String decodePassword(String password) {
        int endIndex = password.lastIndexOf(passwordSuffix);
        if (endIndex <= 0) {
            endIndex = password.length();
        }
        return password.substring(0, endIndex);
    }

    /**
     * 生成随机的salt seek
     *
     * @return
     */
    public static String getRandomSeek() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < SALT_LENGTH; i++) {
            sb.append(HEX_DIGITS[random.nextInt(RANDOM_CONSTATNT)]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String password = "123456a?";
        String salt = randomSalt();
        System.out.println(salt);
        String enPwd = generatePassword(password, salt);
        System.out.println(enPwd);
    }
}
