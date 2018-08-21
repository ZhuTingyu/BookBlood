package com.cpigeon.book.module.foot.adapter;

import com.base.base.BaseActivity;
import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.module.foot.FootAdminSingleFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 */

public class FootAdminListAdapter extends BaseQuickAdapter<FootEntity, BaseViewHolder> {

    BaseActivity mBaseActivity;

    public FootAdminListAdapter(BaseActivity baseActivity) {
        super(R.layout.item_one_foot_admin, Lists.newArrayList());
        mBaseActivity = baseActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, FootEntity item) {

        helper.setTextView(R.id.tvFootNumber, item.getFootRingNum());
        helper.setTextView(R.id.tvStatus, item.getStateName());
        helper.itemView.setOnClickListener(v -> {
            FootAdminSingleFragment.start(getBaseActivity()
                    , String.valueOf(getItem(helper.getAdapterPosition()).getFootRingID()));
        });
    }
}
