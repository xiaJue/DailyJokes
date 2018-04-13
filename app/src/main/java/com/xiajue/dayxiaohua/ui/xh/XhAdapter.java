package com.xiajue.dayxiaohua.ui.xh;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiajue.dayxiaohua.R;
import com.xiajue.dayxiaohua.bean.ContentBeans;
import com.xiajue.dayxiaohua.utils.StringUtils;

import java.util.List;

/**
 * xiaJue 2018/4/10创建
 */
public class XhAdapter extends BaseQuickAdapter<ContentBeans.Datum> {

    public XhAdapter(int layoutResId, List<ContentBeans.Datum> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ContentBeans.Datum datum) {
        baseViewHolder.setText(R.id.contents, datum.getContent()).setText(R.id.date, StringUtils
                .formatDate(datum.getUpdatetime()));
    }
}
