package com.cpigeon.book.module.menu.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.SignRuleListEntity;

import java.util.List;

/**
 * 签到规则
 * Created by Administrator on 2018/8/9.
 */

public class SignRuleAdapter extends BaseQuickAdapter<SignRuleListEntity, BaseViewHolder> {

    public SignRuleAdapter(List<SignRuleListEntity> data) {
        super(R.layout.item_logbook, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignRuleListEntity item) {

    }
}