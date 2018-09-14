package com.cpigeon.book.module.home.home.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.LogbookEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/8.
 */

public class LogbookAdapter extends BaseQuickAdapter<LogbookEntity, BaseViewHolder> {

    public LogbookAdapter(List<LogbookEntity> data) {
        super(R.layout.item_logbook, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LogbookEntity item) {
        helper.setTextView(R.id.tvTime,item.getUserOperateDatetime());
        helper.setTextView(R.id.tvOperate,item.getRemark());
        helper.setTextView(R.id.tvIp,item.getUserOperateIP());
    }
}
