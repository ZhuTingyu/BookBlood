package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.module.trainpigeon.HomingRecordFragment;
import com.cpigeon.book.module.trainpigeon.OpenAndCloseTrainFragment;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class TrainProjectListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TrainProjectListAdapter() {
        super(R.layout.item_train_project_list, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvLocation, "中开");
        helper.setText(R.id.tvCount, "123");
        helper.setText(R.id.tvTime, "2018-11-12");
        helper.setText(R.id.tvTrainedCount, "12");

        if (helper.getAdapterPosition() == 0) {
            helper.setText(R.id.tvStatus, Utils.getString(R.string.text_end_yet));
            helper.itemView.setOnClickListener(v -> {
                HomingRecordFragment.start(getBaseActivity(),true);
            });
        } else if (helper.getAdapterPosition() == 1) {
            helper.setText(R.id.tvStatus, Utils.getString(R.string.text_training));
            helper.itemView.setOnClickListener(v -> {
                HomingRecordFragment.start(getBaseActivity(),false);
            });

        } else if (helper.getAdapterPosition() == 2) {
            helper.setText(R.id.tvStatus, Utils.getString(R.string.text_start_not));
            helper.itemView.setOnClickListener(v -> {
                OpenAndCloseTrainFragment.start(getBaseActivity());
            });
        }


    }
}
