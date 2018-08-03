package com.cpigeon.book.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;


/**
 * Created by Zhu TingYu on 2018/2/26.
 */

public class DemoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DemoAdapter() {
        super(R.layout.item_text_layout, Lists.newArrayList());
    }

    @Override
    protected String getEmptyViewText() {
        return "为空";
    }

    @Override
    protected int getEmptyViewImage() {
        return 0;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setTextView(R.id.text, item);
    }
}
