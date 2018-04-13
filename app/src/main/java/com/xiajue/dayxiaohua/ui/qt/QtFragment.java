package com.xiajue.dayxiaohua.ui.qt;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.xiajue.dayxiaohua.R;
import com.xiajue.dayxiaohua.base.BaseFragment;
import com.xiajue.dayxiaohua.bean.ImageBeans;
import com.xiajue.dayxiaohua.ui.custom.MSmartRefreshLayout;
import com.xiajue.dayxiaohua.ui.custom.SimpleItemDecoration;
import com.xiajue.dayxiaohua.utils.DiskManager;
import com.xiajue.dayxiaohua.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * xiaJue 2018/4/8创建
 */
public class QtFragment extends BaseFragment<QtPresenter> implements QtContract.View<ImageBeans
        .RootObject>, QtAdapter.OnImageClickListener {

    public static BaseFragment newInstance() {
        return new QtFragment();
    }

    private int mPageIndex;
    private MSmartRefreshLayout mSmartRefreshLayout;

    private RecyclerView mRecyclerView;
    private QtAdapter mQtAdapter;
    private List<ImageBeans.Datum> mData;
    private boolean isNeedLoad = true;

    @Override
    protected void bindView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mSmartRefreshLayout = (MSmartRefreshLayout) view.findViewById(R.id.smart_refresh_layout);
    }

    @Override
    protected void initialize() {
        mData = new ArrayList();
        mQtAdapter = new QtAdapter(R.layout.item_qt, mData);
        mQtAdapter.setOnImageClickListener(this);
        mRecyclerView.setAdapter(mQtAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SimpleItemDecoration());
        mPresenter = new QtPresenter();
        //bind
        mPresenter.attachView(this);
        //to load mData
        mSmartRefreshLayout.setOnRefreshListener(new MSmartRefreshLayout.onRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onRefresh();
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadData(mPageIndex++);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_qt;
    }

    @Override
    public void setUiData(ImageBeans.RootObject data) {
//        mPtrFrameLayout.refreshComplete();
        mSmartRefreshLayout.stopRefresh();
        mSmartRefreshLayout.stopLoadMore();
        if (data.getResult() == null) {
            Toast.makeText(getContext(), StringUtils.getErrorString(data.getErrorCode()), Toast
                    .LENGTH_SHORT).show();
            return;
        }
//        this.mData.addAll(data.getResult().getData());
        ArrayList<ImageBeans.Datum> list = data.getResult().getData();
//        this.mData.addAll(list);

        for (ImageBeans.Datum d :
                list) {
            boolean exist = false;
            for (ImageBeans.Datum d2 : mData) {
                if (d2.getContent().equals(d.getContent())) {
                    exist = true;
                }
            }
            if (!exist) {
                this.mData.add(d);
            }
        }
        mQtAdapter.notifyDataSetChanged();
        isNeedLoad = false;
    }

    @Override
    public void onLoadError(Throwable e) {
//        mPtrFrameLayout.refreshComplete();
        mSmartRefreshLayout.stopRefresh();
        mSmartRefreshLayout.stopLoadMore();
        Toast.makeText(getContext(), R.string.load_error, Toast.LENGTH_SHORT).show();
        isNeedLoad = true;
    }

    @Override
    public void onImageClick(View view, ImageBeans.Datum datum, int position) {
        //item image click open image~
        Intent intent = new Intent(getContext(), ImageActivity.class);
        intent.putExtra("contents", datum.getContent());
        if (!datum.getUrl().contains("gif")) {
            intent.putExtra("image", datum.getUrl());
        } else {
            intent.putExtra("gif", true);
            intent.putExtra("image", DiskManager.getInstance(getContext()).get(datum.getUrl())
                    .getAbsolutePath());
        }
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNeedLoad) {
            mPresenter.loadData(1);
            mPageIndex = 2;
        }
    }
}
