package com.cpigeon.book.module.menu.mycurrency.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonCurrencyEntity;

/**
 * Created by Administrator on 2018/9/13.
 */

public class PigeonCurrencyExchangeAdapter extends BaseQuickAdapter<PigeonCurrencyEntity, BaseViewHolder> {


    public PigeonCurrencyExchangeAdapter() {
        super(R.layout.item_pigeon_currency_exchange,  Lists.newArrayList(new PigeonCurrencyEntity(),
                new PigeonCurrencyEntity(),
                new PigeonCurrencyEntity(),
                new PigeonCurrencyEntity(),
                new PigeonCurrencyEntity(),
                new PigeonCurrencyEntity(),
                new PigeonCurrencyEntity()));
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonCurrencyEntity item) {

    }
}
