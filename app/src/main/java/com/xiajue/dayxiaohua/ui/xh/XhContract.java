package com.xiajue.dayxiaohua.ui.xh;

import com.xiajue.dayxiaohua.base.BaseContract;

/**
 * xiaJue 2018/4/8创建
 */
public class XhContract {
    interface View<T> extends BaseContract.View {
        void setUiData(T data);

        void onLoadError(Throwable e);
    }

    interface Presenter extends BaseContract.Presenter<XhContract.View> {
        void loadData(int page);

        void onRefresh();
    }

}
