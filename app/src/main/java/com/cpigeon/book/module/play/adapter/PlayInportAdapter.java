package com.cpigeon.book.module.play.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseMultiSelectAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PlayInportListEntity;

/**
 * Created by Administrator on 2018/9/3.
 */

public class PlayInportAdapter extends BaseMultiSelectAdapter<PlayInportListEntity, BaseViewHolder> {

    public PlayInportAdapter() {
        super(R.layout.item_play_inport, Lists.newArrayList());
    }

    @Override
    protected int getChooseDrawable() {
        return R.mipmap.ic_select_click;
    }

    @Override
    protected int getNotChooseDrawable() {
        return R.mipmap.ic_select_no;
    }

    @Override
    protected void convert(BaseViewHolder holder, PlayInportListEntity item) {
        super.convert(holder, item);


    }
}
