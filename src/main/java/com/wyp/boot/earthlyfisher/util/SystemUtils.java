package com.wyp.boot.earthlyfisher.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by earthlyfisher on 2017/3/8.
 */
public class SystemUtils {

    private static final String USER_DIR = "user.dir";

    private static final AtomicLong IG_GENERATOR = new AtomicLong();

    /**
     * 获取当前用户空间跟目录.
     *
     * @return
     */
    public static String getCurrentRootDir() {
        return System.getProperty(USER_DIR);
    }


    /**
     * 获取类字节码文件根目录
     *
     * @return
     */
    public static String getClsFileRootDir() {
        //SystemUtils.class.getClassLoader().getResource("").getFile();
        return SystemUtils.class.getResource("/").getFile();
    }


    /**
     * 格式化时间
     *
     * @param date
     * @param format
     * @return
     */
    public String convertDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 格式化打印
     *
     * @param format
     * @param params
     */
    public synchronized static void printInfoLog(String format, Object... params) {
        System.out.printf(format, params);
        System.out.println();
    }

    /**
     * 格式化打印错误
     *
     * @param e
     * @param format
     * @param params
     */
    public synchronized static void printErrorLog(Throwable e, String format, Object... params) {
        System.err.printf(format, params);
        System.out.println();
        e.printStackTrace();
    }

    /**
     * 全局id long 生成器
     *
     * @return
     */
    public static Long generateId() {
        return IG_GENERATOR.incrementAndGet();
    }
}
