package com.cpigeon.book.module.trainpigeon.adpter;

import android.view.View;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.AddBackFlyRecordDialog;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class SearchFootRingAdapter extends BaseQuickAdapter<PigeonEntity, BaseViewHolder>{

    TrainEntity mTrainEntity;

    public SearchFootRingAdapter(TrainEntity trainEntity) {
        super(R.layout.item_new_train_add_pigeon, Lists.newArrayList());
        this.mTrainEntity = trainEntity;
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {
        helper.getView(R.id.imgAdd).setVisibility(View.GONE);
        helper.setText(R.id.tvFootNumber,item.getFootRingNum());
        helper.setText(R.id.tvColor, item.getPigeonPlumeName());
        helper.setText(R.id.tvBlood, item.getPigeonBloodName());

        helper.itemView.setOnClickListener(v -> {
            AddBackFlyRecordDialog.show(getBaseActivity().getSupportFragmentManager(), item,mTrainEntity, StringUtil.emptyString());
        });

    }
}
