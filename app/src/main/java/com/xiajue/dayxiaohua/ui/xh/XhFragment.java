package com.xiajue.dayxiaohua.ui.xh;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.xiajue.dayxiaohua.R;
import com.xiajue.dayxiaohua.base.BaseFragment;
import com.xiajue.dayxiaohua.bean.ContentBeans;
import com.xiajue.dayxiaohua.ui.custom.MSmartRefreshLayout;
import com.xiajue.dayxiaohua.ui.custom.SimpleItemDecoration;
import com.xiajue.dayxiaohua.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.xiajue.dayxiaohua.R.id.recyclerView;

/**
 * xiaJue 2018/4/8创建
 */
public class XhFragment extends BaseFragment<XhPresenter> implements XhContract.View<ContentBeans
        .RootObject> {

    private int mPageIndex;

    public static BaseFragment newInstance() {
        return new XhFragment();
    }

    private MSmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private XhAdapter mXhAdapter;
    private List<ContentBeans.Datum> mData;
    private boolean isNeedLoad = true;//onResume load?

    @Override
    protected void bindView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(recyclerView);
        mSmartRefreshLayout = (MSmartRefreshLayout) view.findViewById(R.id.smart_refresh_layout);
    }

    @Override
    protected void initialize() {
        mData = new ArrayList();
        mXhAdapter = new XhAdapter(R.layout.item_xh, mData);
        //上拉加载更多
        mRecyclerView.setAdapter(mXhAdapter);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new SimpleItemDecoration());

        mPresenter = new XhPresenter();
        mPresenter.attachView(this);
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
        return R.layout.fragment_xh;
    }

    @Override
    public void setUiData(ContentBeans.RootObject data) {
        mSmartRefreshLayout.stopRefresh();
        mSmartRefreshLayout.stopLoadMore();
        //产生各种错误码时
        if (data.getResult() == null) {
            Toast.makeText(getContext(), StringUtils.getErrorString(data.getErrorCode()), Toast
                    .LENGTH_SHORT).show();
            return;
        }
        ArrayList<ContentBeans.Datum> list = data.getResult().getData();
//        this.mData.addAll(list);
        for (ContentBeans.Datum d :
                list) {
            boolean exist = false;
            for (ContentBeans.Datum d2 : mData) {
                if (d2.getContent().equals(d.getContent())) {
                    exist = true;
                }
            }
            if (!exist) {
                this.mData.add(d);
            }
        }
        mXhAdapter.notifyDataSetChanged();
        isNeedLoad = false;
    }

    @Override
    public void onLoadError(Throwable e) {
        mSmartRefreshLayout.stopRefresh();
        mSmartRefreshLayout.stopLoadMore();
        isNeedLoad = true;
        Toast.makeText(getContext(), "加载数据失败...", Toast.LENGTH_SHORT).show();
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
