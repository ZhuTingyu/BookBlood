package com.cpigeon.book.module.feedpigeon.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class FeedPigeonRecordListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FeedPigeonRecordListAdapter() {
        super(R.layout.item_feed_pigeon_list, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvFootNumber,"2018-22-123456");
        helper.setText(R.id.tvSex,"雄");
        helper.setText(R.id.tvColor,"雨色");

    }
}
