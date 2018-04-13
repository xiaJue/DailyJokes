package com.xiajue.dayxiaohua.ui.xh;

import com.xiajue.dayxiaohua.base.BaseObserver;
import com.xiajue.dayxiaohua.base.BasePresenter;
import com.xiajue.dayxiaohua.bean.ContentBeans;
import com.xiajue.dayxiaohua.constant.ConstKey;
import com.xiajue.dayxiaohua.net.HttpConfig;
import com.xiajue.dayxiaohua.net.RetrofitFactory;
import com.xiajue.dayxiaohua.net.RxSchedulers;
import com.xiajue.dayxiaohua.net.xh.XhApiServer;
import com.xiajue.dayxiaohua.utils.StringUtils;

import retrofit2.Response;

/**
 * xiaJue 2018/4/8创建
 */
public class XhPresenter extends BasePresenter<XhContract.View> implements XhContract.Presenter {


    @Override
    public void loadData(int page) {
        //调试用开关
        if (!HttpConfig.CONNECT_INTERNET) {
            return;
        }
        RetrofitFactory.create(XhApiServer.class).getList("", String.valueOf(page), ConstKey
                        .PAGE_SIZE,
                StringUtils
                        .getTimeMillis()).compose(mView.<Response<ContentBeans
                .RootObject>>bindToLife())
                .compose(RxSchedulers.<Response<ContentBeans.RootObject>>applySchedulers())
                .subscribe(new BaseObserver<ContentBeans.RootObject>() {

                    @Override
                    public void onFailure(Throwable e, boolean isNetWorkError) {
                        mView.onLoadError(e);
                    }

                    @Override
                    public void onSuccess(Response<ContentBeans.RootObject> result) {
                        mView.setUiData(result.body());
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadData(1);
    }
}
