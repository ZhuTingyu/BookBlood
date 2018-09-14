package com.cpigeon.book.module.breeding.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PairingInfoEntity;

/**
 * Created by Administrator on 2018/9/10.
 */

public class PairingInfoListAdapter extends BaseQuickAdapter<PairingInfoEntity, BaseViewHolder> {


    public PairingInfoListAdapter() {
        super(R.layout.item_pairing_info, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PairingInfoEntity item) {
//        helper.setText(R.id.tv_foot, "" + helper.getPosition());
//        helper.setText(R.id.tv_lineage, "神奇的小鸟" + helper.getPosition());
//        helper.setText(R.id.tv_nest_num, helper.getPosition() + "窝");

    }
}
