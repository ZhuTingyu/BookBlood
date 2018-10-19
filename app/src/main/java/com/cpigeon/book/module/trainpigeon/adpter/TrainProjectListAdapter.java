package com.cpigeon.book.module.trainpigeon.adpter;

import android.view.View;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.FlyBackRecordFragment;
import com.cpigeon.book.module.trainpigeon.OpenAndCloseTrainFragment;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class TrainProjectListAdapter extends BaseQuickAdapter<TrainEntity, BaseViewHolder> {

    public TrainProjectListAdapter() {
        super(R.layout.item_train_project_list, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, TrainEntity trainEntity) {
        helper.setViewVisible(R.id.llCheck, View.GONE);
        TextView tvStatus = helper.getView(R.id.tvStatus);
        helper.setText(R.id.tvLocation, trainEntity.getFromPlace());
        helper.setText(R.id.tvCount, String.valueOf(trainEntity.getReturnCount()));

        helper.setText(R.id.tvTrainedOrder, String.valueOf(trainEntity.getTrainCount()));
        tvStatus.setText(trainEntity.getTrainStateName());

        if(Utils.getString(R.string.text_end_yet).equals(trainEntity.getTrainStateName())){
            tvStatus.setTextColor(Utils.getColor(R.color.color_text_title));
            helper.itemView.setOnClickListener(v -> {
                FlyBackRecordFragment.start(getBaseActivity(), trainEntity, true);
            });
        }else if(Utils.getString(R.string.text_training).equals(trainEntity.getTrainStateName())){
            tvStatus.setTextColor(Utils.getColor(R.color.color_text_red));
            helper.itemView.setOnClickListener(v -> {
                FlyBackRecordFragment.start(getBaseActivity(), trainEntity, false);
            });
        }else if(Utils.getString(R.string.text_start_not).equals(trainEntity.getTrainStateName())){
            tvStatus.setTextColor(Utils.getColor(R.color.color_text_title));
            helper.itemView.setOnClickListener(v -> {
                OpenAndCloseTrainFragment.start(getBaseActivity(), true, trainEntity);
            });
        }
        helper.setText(R.id.tvTime, trainEntity.getFromFlyTime());
    }
}
