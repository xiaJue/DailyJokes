package com.xiajue.dayxiaohua.ui.qt;

import com.xiajue.dayxiaohua.base.BaseObserver;
import com.xiajue.dayxiaohua.base.BasePresenter;
import com.xiajue.dayxiaohua.bean.ImageBeans;
import com.xiajue.dayxiaohua.constant.ConstKey;
import com.xiajue.dayxiaohua.net.HttpConfig;
import com.xiajue.dayxiaohua.net.RetrofitFactory;
import com.xiajue.dayxiaohua.net.RxSchedulers;
import com.xiajue.dayxiaohua.net.qt.QtApiServer;
import com.xiajue.dayxiaohua.utils.L;
import com.xiajue.dayxiaohua.utils.StringUtils;

import retrofit2.Response;

/**
 * xiaJue 2018/4/8创建
 */
public class QtPresenter extends BasePresenter<QtContract.View> implements QtContract.Presenter {

//    public QtPresenter(QtContract.View view) {
//        mView = view;
//    }

    @Override
    public void loadData(int page) {
        L.e("load>>>>>>>>>>>>>>>>>>>>");

        if (!HttpConfig.CONNECT_INTERNET) {
            return;
        }
        RetrofitFactory.create(QtApiServer.class).getList("", String.valueOf(page), ConstKey.PAGE_SIZE, StringUtils
                .getTimeMillis()).compose(mView.<Response<ImageBeans.RootObject>>bindToLife())
                .compose(RxSchedulers.<Response<ImageBeans.RootObject>>applySchedulers())
                .subscribe(new BaseObserver<ImageBeans.RootObject>() {

                    @Override
                    public void onFailure(Throwable e, boolean isNetWorkError) {
                        mView.onLoadError(e);
                    }

                    @Override
                    public void onSuccess(Response<ImageBeans.RootObject> result) {
                        mView.setUiData(result.body());
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadData(1);
    }
}
