package com.cpigeon.book.module.home.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/10.
 */

public class HomeTopAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeTopAdapter() {
        super(R.layout.item_home_top, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
