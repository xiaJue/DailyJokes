package com.xiajue.dayxiaohua.ui.home;

import com.xiajue.dayxiaohua.base.BaseContract;

/**
 * xiaJue 2018/4/8创建
 */
public class HomeContract {
    interface View<T> extends BaseContract.View {
        void setUiData(T data);

        void onLoadError(Throwable e);
    }

    interface Presenter extends BaseContract.Presenter<HomeContract.View> {
        void loadData();
    }
}
