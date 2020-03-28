package com.jgkj.grb.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * 获取 App 缓存大小、清除缓存
 * Created by brightpoplar@163.com on 2019/8/14.
 */
public class CleanCacheDataUtils {

    /**
     * 缓存大小，内置、外置
     *
     * @param context Context
     * @return String FormatSize
     */
    public static String getAllCacheSize(Context context) {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清空缓存，内置、外置
     *
     * @param context Context
     */
    public static void clearAllCache(Context context) {
        cleanInternalCache(context);
        cleanExternalCache(context);
    }

    /**
     * 目录文件大小
     *
     * @param file File
     * @return long FolderSize
     */
    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File file1 : fileList) {
                // 如果下面还有文件
                if (file1.isDirectory()) {
                    size = size + getFolderSize(file1);
                } else {
                    size = size + file1.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 清除内置 cache 内容
     *
     * @param context Context
     */
    private static void cleanInternalCache(Context context) {
        clearCacheDir(context.getCacheDir());
    }

    /**
     * 清除外置 cache 内容
     *
     * @param context Context
     */
    private static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            clearCacheDir(context.getExternalCacheDir());
        }
    }

    /**
     * 清空缓存，目录文件
     *
     * @param dir File
     * @return boolean clearCacheDir
     */
    private static boolean clearCacheDir(File dir) {
        if (dir != null && dir.exists() && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = clearCacheDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        if (dir != null)
            return dir.delete();
        else
            return false;
    }

    /**
     * 格式化大小，带单位
     *
     * @param size double
     * @return String FormatSize
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "G";
        }

        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "T";
    }

}
