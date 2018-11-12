package com.cpigeon.book.module.play.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseMultiSelectAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PlayImportListEntity;

/**
 * Created by Administrator on 2018/9/3.
 */

public class PlayImportAdapter extends BaseMultiSelectAdapter<PlayImportListEntity, BaseViewHolder> {

    public PlayImportAdapter() {
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
    protected void convert(BaseViewHolder holder, PlayImportListEntity item) {
        super.convert(holder, item);
        holder.setTextView(R.id.tv_source, item.getSjly());
        holder.setTextView(R.id.tv_foot, item.getBsrq().split(" ")[0]);
        holder.setTextView(R.id.tv_rank, Utils.getString(R.string.text_rank_content, item.getBsmc()));
    }
}
