package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class FootNumberTrainDetailsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FootNumberTrainDetailsAdapter() {
        super(R.layout.item_foot_number_train_details, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvNumber, String.valueOf(helper.getAdapterPosition() + 1));
        helper.setText(R.id.tvSpeed, Utils.getString(R.string.text_speed_content,1000));
        helper.setText(R.id.tvScore, Utils.getString(R.string.text_score, 0.08));
        helper.setText(R.id.tvJoinCount, Utils.getString(R.string.text_join_train_count,100));
        helper.setText(R.id.tvHomingCount, Utils.getString(R.string.text_homing_count,100));
        helper.setText(R.id.tvOrder, Utils.getString(R.string.text_order_content,100));
    }
}
