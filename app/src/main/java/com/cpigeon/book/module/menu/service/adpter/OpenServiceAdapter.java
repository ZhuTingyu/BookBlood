package com.cpigeon.book.module.menu.service.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class OpenServiceAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public OpenServiceAdapter() {
        super(R.layout.item_open_service_report, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvTitle,"天下鸽谱");
        helper.setText(R.id.tvOpenTime,"2018-22-12");
        helper.setText(R.id.tvTime,"1年");
    }
}
