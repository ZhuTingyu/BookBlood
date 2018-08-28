package com.cpigeon.book.module.order.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/27.
 */

public class OrderListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public OrderListAdapter() {
        super(R.layout.item_order_list, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
