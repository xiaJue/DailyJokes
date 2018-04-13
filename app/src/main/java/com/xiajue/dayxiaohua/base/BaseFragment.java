package com.xiajue.dayxiaohua.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * xiaJue 2018/4/8创建
 */
public abstract class BaseFragment<P extends BaseContract.Presenter> extends Fragment implements
        BaseContract.View {
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        bindView(view);
        initialize();
        return view;
    }

    protected abstract void initialize();

    protected abstract void bindView(View view);

    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        //un bind
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * @param <T>
     * @return
     */
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return getBaseActivityLife();
    }

    private <T> LifecycleTransformer<T> getBaseActivityLife() {
        return ((BaseActivity) getActivity()).bindToLife();
    }
}
