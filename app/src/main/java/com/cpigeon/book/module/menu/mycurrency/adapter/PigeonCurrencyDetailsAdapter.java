package com.cpigeon.book.module.menu.mycurrency.adapter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonCurrencyEntity;

/**
 * Created by Zhu TingYu on 2018/8/24.
 */

public class PigeonCurrencyDetailsAdapter extends BaseQuickAdapter<PigeonCurrencyEntity, BaseViewHolder> {

    public PigeonCurrencyDetailsAdapter() {
        super(R.layout.item_pigeon_currency_details, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonCurrencyEntity item) {
        helper.setText(R.id.tvTitle, item.getItem());
        helper.setText(R.id.tvTime, item.getDatetime());
        TextView count = helper.getView(R.id.tvCount);
        if (Integer.valueOf(item.getGb()) >= 0) {
            count.setTextColor(Utils.getColor(R.color.color_count_add));
            count.setText("+" + item.getGb());
        } else {
            count.setTextColor(Utils.getColor(R.color.color_text_hint));
            count.setText(item.getGb());
        }
    }
}
