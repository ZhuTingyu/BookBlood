package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonTrainDetailsEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class FootNumberTrainDetailsAdapter extends BaseQuickAdapter<PigeonTrainDetailsEntity, BaseViewHolder> {

    public FootNumberTrainDetailsAdapter() {
        super(R.layout.item_foot_number_train_details, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonTrainDetailsEntity item) {
        helper.setText(R.id.tvNumber, String.valueOf(helper.getAdapterPosition() + 1));
        helper.setText(R.id.tvSpeed, Utils.getString(R.string.text_speed_content,item.getFraction()));
        helper.setText(R.id.tvScore, Utils.getString(R.string.text_score, item.getScore()));
        helper.setText(R.id.tvJoinCount, Utils.getString(R.string.text_join_train_count,item.getFlyCount()));
        helper.setText(R.id.tvHomingCount, Utils.getString(R.string.text_homing_count,item.getReturnCount()));
        helper.setText(R.id.tvOrder, Utils.getString(R.string.text_order_content,item.getSortRank()));
    }
}
