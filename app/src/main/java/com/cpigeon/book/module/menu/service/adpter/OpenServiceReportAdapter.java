package com.cpigeon.book.module.menu.service.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.ServerReportEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class OpenServiceReportAdapter extends BaseQuickAdapter<ServerReportEntity, BaseViewHolder> {

    public OpenServiceReportAdapter() {
        super(R.layout.item_open_service_report, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, ServerReportEntity item) {
        helper.setText(R.id.tvTitle, item.getSname());
        helper.setText(R.id.tvOpenTime, item.getOther());
        helper.setText(R.id.tvTime, item.getNum() + item.getDanwei());
    }
}
