package com.cpigeon.book.module.breedpigeon.adpter;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.veiwholder.BreedPigeonListViewHolder;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class BreedPigeonListAdapter extends BaseQuickAdapter<String, BreedPigeonListViewHolder> {

    public BreedPigeonListAdapter() {
        super(R.layout.item_breed_pigeon_list, Lists.newArrayList());
    }

    @Override
    protected void convert(BreedPigeonListViewHolder helper, String item) {
        helper.bindData(item);
    }
}
