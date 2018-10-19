package com.cpigeon.book.module.trainpigeon.adpter;

import android.view.View;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseMultiSelectAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.TrainEntity;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class SelectTrainProjectAdapter extends BaseMultiSelectAdapter<TrainEntity, BaseViewHolder> {

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
    protected void convert(BaseViewHolder holder, TrainEntity trainEntity) {
        holder.setViewVisible(R.id.llCheck, View.VISIBLE);
        super.convert(holder, trainEntity);

        TextView tvStatus = holder.getView(R.id.tvStatus);

        holder.setText(R.id.tvLocation, trainEntity.getFromPlace());
        holder.setText(R.id.tvCount, String.valueOf(trainEntity.getReturnCount()));

        holder.setText(R.id.tvTrainedOrder, String.valueOf(trainEntity.getTrainCount()));
        tvStatus.setText(trainEntity.getTrainStateName());

        holder. setText(R.id.tvTime, trainEntity.getFromFlyTime().split(" ")[0]);

    }
}
