package com.cpigeon.book.module.play.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PlayInportListEntity;

/**
 * Created by Administrator on 2018/9/3.
 */

public class PlayInportAdapter2 extends BaseQuickAdapter<PlayInportListEntity, BaseViewHolder> {

    public PlayInportAdapter2() {
        super(R.layout.item_play_inport, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayInportListEntity item) {

    }
}
