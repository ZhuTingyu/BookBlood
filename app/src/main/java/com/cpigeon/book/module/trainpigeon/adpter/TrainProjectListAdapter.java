package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.veiwholder.TrainProjectViewHolder;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class TrainProjectListAdapter extends BaseQuickAdapter<TrainEntity, TrainProjectViewHolder> {

    public TrainProjectListAdapter() {
        super(R.layout.item_train_project_list, Lists.newArrayList());
    }

    @Override
    protected void convert(TrainProjectViewHolder helper, TrainEntity item) {
        helper.bindData(item, false);
    }
}
