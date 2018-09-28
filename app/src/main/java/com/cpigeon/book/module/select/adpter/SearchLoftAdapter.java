package com.cpigeon.book.module.select.adpter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseLetterSelectAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.LoftEntity;

/**
 *  选择公棚
 */

public class SearchLoftAdapter extends BaseLetterSelectAdapter<LoftEntity, BaseViewHolder> {

    public SearchLoftAdapter() {
        super(R.layout.item_text_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, LoftEntity item) {
        TextView text = (TextView) helper.itemView;
        text.setText(item.getGpname());
    }
}
