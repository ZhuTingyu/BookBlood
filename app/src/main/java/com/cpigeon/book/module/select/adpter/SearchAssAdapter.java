package com.cpigeon.book.module.select.adpter;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.base.util.utility.PhoneUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.AssEntity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public class SearchAssAdapter extends BaseQuickAdapter<AssEntity, BaseViewHolder> {

    public SearchAssAdapter() {
        super(R.layout.item_text_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, AssEntity item) {
        TextView text = (TextView) helper.itemView;
        text.setText(item.getISOCName());
    }
}
