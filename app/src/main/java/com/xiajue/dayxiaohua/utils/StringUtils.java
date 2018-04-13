package com.xiajue.dayxiaohua.utils;

import com.xiajue.dayxiaohua.net.HttpConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * xiaJue 2018/3/12创建
 */
public class StringUtils {

    /**
     * 2017-08-25 11:43:50
     *
     * @return 2017-08-25
     */
    public static String formatDate(String date) {
        return date.substring(0, 10);
    }


    /**
     * 长度为10的时间戳
     */
    public static String getTimeMillis() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static String getErrorString(int errorCode) {
        for (int i = 0; i < HttpConfig.ERROR_CODE.length; i++) {
            if (errorCode == HttpConfig.ERROR_CODE[i]) {
                return HttpConfig.ERROR_STRING[i];
            }
        }
        return "未知错误";
    }

    public static String hashGifKeyFromUrl(String url) {
        int i = url.lastIndexOf('.');
        String end = url.substring(i, url.length());
        String ur = url.substring(0, i - 1);
        return hashKeyFromUrl(ur) + end;
    }

    /**
     * 转换为MD5
     *
     * @param url
     * @return
     */
    public static String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = byteToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private static String byteToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);//得到十六进制字符串
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
