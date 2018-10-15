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
        super(R.layout.item_sign_rule, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignRuleListEntity item) {
        helper.setText(R.id.tv_content, (helper.getPosition() + 1) + "." + item.getGbgz());
    }
}