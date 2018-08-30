package com.cpigeon.book.module.breed.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class GrowthReporAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public GrowthReporAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
