package com.cpigeon.book.module.select.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.AssEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public class SearchAssHistoryAdapter extends BaseQuickAdapter<AssEntity, BaseViewHolder> {

    public SearchAssHistoryAdapter() {
        super(R.layout.item_search_ass_history, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, AssEntity item) {
        helper.setText(R.id.tvLeft, item.getISOCName());
        helper.getView(R.id.imgRight).setOnClickListener(v -> {

        });
    }
}
