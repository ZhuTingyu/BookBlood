package com.cpigeon.book.module.foot.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.CountyAreaEntity;
/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class SelectCountyAreaAdapter extends BaseQuickAdapter<CountyAreaEntity, BaseViewHolder> {

    public SelectCountyAreaAdapter() {
        super(R.layout.item_line_2_text, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, CountyAreaEntity item) {
        helper.setText(R.id.tvLeft, item.getFootCodeName());
        helper.setText(R.id.tvRight, item.getCode());
    }
}
