package com.xiajue.dayxiaohua.net.qt;

import com.xiajue.dayxiaohua.net.RetrofitFactory;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Request;

/**
 * xiaJue 2018/4/11创建
 */
public class ImageStreamManager {
    public static InputStream openStream(String url) {
        Request request = new Request.Builder().url(url)
                .build();
        try {
            return RetrofitFactory.getOkHttpClient().newCall(request).execute().body().byteStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
