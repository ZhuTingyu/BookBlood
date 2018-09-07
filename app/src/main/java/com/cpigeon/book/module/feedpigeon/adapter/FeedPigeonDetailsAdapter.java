package com.cpigeon.book.module.feedpigeon.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class FeedPigeonDetailsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public FeedPigeonDetailsAdapter() {
        super(R.layout.item_feed_pigeon_details, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
