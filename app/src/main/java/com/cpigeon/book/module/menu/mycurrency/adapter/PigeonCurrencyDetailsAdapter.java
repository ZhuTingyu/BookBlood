package com.cpigeon.book.module.menu.mycurrency.adapter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/24.
 */

public class PigeonCurrencyDetailsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PigeonCurrencyDetailsAdapter() {
        super(R.layout.item_pigeon_currency_details, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvTitle, "足环号码录入");
        helper.setText(R.id.tvTime, "2018-11-11 12:12:12");
        TextView count = helper.getView(R.id.tvCount);
        if(helper.getAdapterPosition() == 0){
            count.setTextColor(Utils.getColor(R.color.color_count_add));
            count.setText("+50");
        }else {
            count.setTextColor(Utils.getColor(R.color.color_text_hint));
            count.setText("-50");
        }
    }
}
