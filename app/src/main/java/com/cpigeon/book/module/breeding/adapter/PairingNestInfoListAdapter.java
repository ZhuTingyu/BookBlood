package com.cpigeon.book.module.breeding.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;

/**
 * Created by Administrator on 2018/9/10.
 */

public class PairingNestInfoListAdapter extends BaseQuickAdapter<PairingNestInfoEntity, BaseViewHolder> {


    public PairingNestInfoListAdapter() {
        super(R.layout.item_pairing_nest_info, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PairingNestInfoEntity item) {

    }

}
