package com.xiajue.dayxiaohua.ui.home;

import com.xiajue.dayxiaohua.base.BasePresenter;

/**
 * xiaJue 2018/4/8创建
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract
        .Presenter {
    private HomeContract.View mView;

    public HomePresenter(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void loadData() {

    }
}
