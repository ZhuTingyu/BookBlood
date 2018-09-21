package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.adpter.BaseMultiSelectAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.veiwholder.TrainProjectViewHolder;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class SelectTrainProjectAdapter extends BaseMultiSelectAdapter<TrainEntity, TrainProjectViewHolder> {

    public SelectTrainProjectAdapter() {
        super(R.layout.item_train_project_list, Lists.newArrayList());
    }

    @Override
    protected int getChooseDrawable() {
        return R.mipmap.ic_select_click;
    }

    @Override
    protected int getNotChooseDrawable() {
        return R.mipmap.ic_select_no;
    }

    @Override
    protected void convert(TrainProjectViewHolder holder, TrainEntity item) {
        holder.setLlCheckVisibility(true);
        super.convert(holder, item);
        holder.bindData(item,true);
    }
}
