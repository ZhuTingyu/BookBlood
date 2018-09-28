package com.cpigeon.book.module.order.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.AccountBalanceListEntity;

import java.util.List;

/**
 * 充值明细  列表
 * Created by Administrator on 2018/8/8.
 */

public class BalanceDetailsAdapter extends BaseQuickAdapter<AccountBalanceListEntity, BaseViewHolder> {

    public BalanceDetailsAdapter(List<AccountBalanceListEntity> data) {
        super(R.layout.item_balance_details, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountBalanceListEntity item) {
        helper.setTextView(R.id.tv_time, item.getShijian());
        helper.setTextView(R.id.tv_content, "+" + item.getMoney());

    }
}
