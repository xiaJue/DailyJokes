package com.xiajue.dayxiaohua.ui.custom;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiajue.dayxiaohua.app.App;
import com.xiajue.dayxiaohua.utils.DensityUtils;


/**
 * xiaJue 2018/3/15创建
 */
public class SimpleItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * @param outRect 边界
     * @param view    recyclerView ItemView
     * @param parent  recyclerView
     * @param state   recycler 内部数据管理
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        //设定底部边距为1px
        int margin = DensityUtils.dp2px(App.getInstance().getContext(), 10);
        outRect.set(margin, margin, margin, margin * 2);
    }
}
