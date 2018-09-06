package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.module.trainpigeon.HomingRecordFragment;
import com.cpigeon.book.module.trainpigeon.OpenAndCloseTrainFragment;
import com.cpigeon.book.veiwholder.TrainProjectViewModel;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class TrainProjectListAdapter extends BaseQuickAdapter<String, TrainProjectViewModel> {

    public TrainProjectListAdapter() {
        super(R.layout.item_train_project_list, Lists.newArrayList());
    }

    @Override
    protected void convert(TrainProjectViewModel helper, String item) {
       helper.bindData();
    }
}
