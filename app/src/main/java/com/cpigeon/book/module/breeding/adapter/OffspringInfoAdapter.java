package com.cpigeon.book.module.breeding.adapter;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;

import java.util.List;

/**
 * 配对信息  --》 子代信息
 * Created by Administrator on 2018/9/19 0019.
 */

public class OffspringInfoAdapter extends BaseQuickAdapter<PairingNestInfoEntity.PigeonListBean, BaseViewHolder> {


    public OffspringInfoAdapter() {
        super(R.layout.item_offspring_info, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PairingNestInfoEntity.PigeonListBean item) {

    }
}
