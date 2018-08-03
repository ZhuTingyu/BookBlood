package com.cpigeon.book.module.select.adpter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseLetterSelectAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.AssEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class SelectAssAdapter extends BaseLetterSelectAdapter<AssEntity, BaseViewHolder> {

    public SelectAssAdapter() {
        super(R.layout.item_text_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, AssEntity item) {
        TextView view = (TextView) helper.itemView;
        view.setTextColor(Utils.getColor(R.color.color_text_color_gray));
        view.setText(item.getContent());
    }
}
