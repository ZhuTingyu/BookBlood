package com.cpigeon.book.module.basepigeon;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * 所有鸽子列表  base  适配器
 * Created by Administrator on 2018/9/21 0021.
 */

public class BasePigeonListAdapter extends BaseQuickAdapter<PigeonEntity, BaseViewHolder> {


    public BasePigeonListAdapter(int layoutResId, List<PigeonEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {

    }
}
