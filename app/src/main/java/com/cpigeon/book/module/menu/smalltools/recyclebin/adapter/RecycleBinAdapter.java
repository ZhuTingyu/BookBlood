package com.cpigeon.book.module.menu.smalltools.recyclebin.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.RecycleBinEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */

public class RecycleBinAdapter extends BaseQuickAdapter<RecycleBinEntity, BaseViewHolder> {

    public RecycleBinAdapter(List<RecycleBinEntity> data) {
        super(R.layout.item_recycle_bin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecycleBinEntity item) {

        helper.setText(R.id.tv_title, item.getRecycleName());

    }
}
