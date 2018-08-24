package com.cpigeon.book.module.menu.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.AccountBalanceListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/8.
 */

public class BalanceDetailsAdapter extends BaseQuickAdapter<AccountBalanceListEntity, BaseViewHolder> {

    public BalanceDetailsAdapter(List<AccountBalanceListEntity> data) {
        super(R.layout.item_logbook, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountBalanceListEntity item) {
        helper.setTextView(R.id.tvTime,item.getUserOperateDatetime());
        helper.setTextView(R.id.tvOperate,item.getRemark());
        helper.setTextView(R.id.tvIp,item.getUserOperateIP());
    }
}
