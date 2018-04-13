package com.xiajue.dayxiaohua.ui.qt;

import com.xiajue.dayxiaohua.base.BaseContract;

/**
 * xiaJue 2018/4/8创建
 */
public class QtContract {
    interface View<T> extends BaseContract.View {
        void setUiData(T data);

        void onLoadError(Throwable e);
    }

    interface Presenter extends BaseContract.Presenter<QtContract.View> {
        void loadData(int page);

        void onRefresh();
    }
}
