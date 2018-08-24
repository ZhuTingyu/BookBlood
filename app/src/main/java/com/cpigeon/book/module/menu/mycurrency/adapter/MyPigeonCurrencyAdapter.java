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

public class MyPigeonCurrencyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MyPigeonCurrencyAdapter() {
        super(R.layout.item_my_pigeon_currency, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvTitle,"今日签到");
        helper.setText(R.id.tvRule,"+5/1次");
        TextView status = helper.getView(R.id.tvStatus);
        if(helper.getAdapterPosition() == 0){
            status.setTextColor(Utils.getColor(R.color.white));
            status.setText(Utils.getString(R.string.text_go_finish));
            status.setBackgroundResource(R.mipmap.ic_my_currency_go_finish);
        }else {
            status.setTextColor(Utils.getColor(R.color.color_text_hint));
            status.setText(Utils.getString(R.string.text_finished));
            status.setBackgroundResource(R.mipmap.ic_my_currency_finish);
        }
    }
}
