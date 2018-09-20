package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.veiwholder.TrainProjectViewModel;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class TrainProjectListAdapter extends BaseQuickAdapter<String, TrainProjectViewModel> {

    public TrainProjectListAdapter() {
        super(R.layout.item_train_project_list, Lists.newArrayList());
    }

    @Override
    protected void convert(TrainProjectViewModel helper, String item) {
       helper.bindData(false);
    }
}
