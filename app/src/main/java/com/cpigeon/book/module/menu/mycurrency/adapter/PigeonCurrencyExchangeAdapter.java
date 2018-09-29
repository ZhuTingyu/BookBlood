package com.cpigeon.book.module.menu.mycurrency.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.CurrencyExchangeEntity;

/**
 * Created by Administrator on 2018/9/13.
 */

public class PigeonCurrencyExchangeAdapter extends BaseQuickAdapter<CurrencyExchangeEntity, BaseViewHolder> {


    public PigeonCurrencyExchangeAdapter() {
        super(R.layout.item_pigeon_currency_exchange, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, CurrencyExchangeEntity item) {
        helper.setText(R.id.tv_title, item.getSname());
        helper.setText(R.id.tv_content, item.getGb() + "鸽币/" + item.getNumber() + item.getDanwei());
    }
}
