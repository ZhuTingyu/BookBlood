package com.cpigeon.book.module.foot.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FootAdminListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 */

public class FootAdminListAdapter extends BaseQuickAdapter<FootAdminListEntity, BaseViewHolder> {

    public FootAdminListAdapter(List<FootAdminListEntity> data) {
        super(R.layout.item_foot_admin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootAdminListEntity item) {
        helper.setTextView(R.id.tv_title, item.getFootRingNum());
        helper.setTextView(R.id.tv_time, item.getStateName());
    }
}
