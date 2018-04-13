package com.xiajue.dayxiaohua.net;


import android.util.Log;

import com.xiajue.dayxiaohua.utils.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * xiaJue 2018/3/6创建
 */
public class InterceptorUtil {
    public static String TAG = "xiajue";

    //日志拦截器
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }

    public static Interceptor HeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request mRequest = chain.request();
                L.e(mRequest.toString() + ">>>>>>>>>>>>>>head intercept");
                return chain.proceed(mRequest);
            }
        };

    }

}
