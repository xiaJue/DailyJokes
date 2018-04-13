package com.xiajue.dayxiaohua.app;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.xiajue.dayxiaohua.base.BaseActivity;

/**
 * xiaJue 2018/3/6创建
 */
public class App extends Application {
    private static App mApp;
    private Context mContext;

    public static App getInstance() {
        if (mApp == null) {
            synchronized (App.class) {
                mApp = new App();
            }
        }
        return mApp;
    }

    public Context getContext() {
        return mContext;
    }

    public BaseActivity getActivity() {
        return (BaseActivity) mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public int getItemWidth() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context
                .WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth() / 6 * 5;//
        return width;
    }

    public int getItemMaxHeight() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context
                .WINDOW_SERVICE);
        int height = windowManager.getDefaultDisplay().getHeight() / 3 * 2;
        return height;
    }
}
