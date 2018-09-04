package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;

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
        helper.setText(R.id.tvLocation,"中开");
        helper.setText(R.id.tvCount,"123");
        helper.setText(R.id.tvTime,"2018-11-12");
        helper.setText(R.id.tvTrainedCount,"12");
        helper.setText(R.id.tvStatus,"已结束");
    }
}
