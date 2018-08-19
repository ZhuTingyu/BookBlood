package com.cpigeon.book.module.foot.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FootEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 */

public class FootAdminListAdapter extends BaseQuickAdapter<FootEntity, BaseViewHolder> {

    public FootAdminListAdapter(List<FootEntity> data) {
        super(R.layout.item_one_foot_admin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootEntity item) {

        helper.setTextView(R.id.tvFootNumber, item.getFootRingNum());
        helper.setTextView(R.id.tvStatus, item.getStateName());
    }
}
