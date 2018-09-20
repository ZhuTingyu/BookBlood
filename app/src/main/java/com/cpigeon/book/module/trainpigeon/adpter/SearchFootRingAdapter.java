package com.cpigeon.book.module.trainpigeon.adpter;

import android.view.View;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class SearchFootRingAdapter extends BaseQuickAdapter<PigeonEntity, BaseViewHolder>{
    public SearchFootRingAdapter() {
        super(R.layout.item_new_train_add_pigeon, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {
        helper.getView(R.id.imgAdd).setVisibility(View.GONE);
        helper.setText(R.id.tvFootNumber,item.getFootRingNum());
        helper.setText(R.id.tvColor, item.getPigeonPlumeName());
        helper.setText(R.id.tvBlood, item.getPigeonBloodName());
    }
}
