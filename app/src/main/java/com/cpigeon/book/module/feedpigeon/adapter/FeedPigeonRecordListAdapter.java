package com.cpigeon.book.module.feedpigeon.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class FeedPigeonRecordListAdapter extends BreedPigeonListAdapter {

    public FeedPigeonRecordListAdapter() {
        super(R.layout.item_feed_pigeon_list, Lists.newArrayList());
    }


    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {

        helper.setText(R.id.tvFootNumber, item.getFootRingNum());
        helper.setText(R.id.tvSex, item.getPigeonSexName());
        helper.setText(R.id.tvColor, item.getPigeonPlumeName());

    }
}
