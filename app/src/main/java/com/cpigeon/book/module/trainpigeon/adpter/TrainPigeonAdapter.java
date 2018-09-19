package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.veiwholder.TrainPigeonViewHolder;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class TrainPigeonAdapter extends BaseQuickAdapter<TrainEntity,TrainPigeonViewHolder> {

    public TrainPigeonAdapter() {
        super(R.layout.item_train_pigeon_list, Lists.newArrayList());
    }

    @Override
    protected void convert(TrainPigeonViewHolder helper, TrainEntity item) {
        helper.bindData(item);
    }
}
