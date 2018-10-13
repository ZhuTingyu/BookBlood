package com.cpigeon.book.module.select.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FootEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/13.
 */

public class SelectFootRingAdapter extends BaseQuickAdapter<FootEntity, BaseViewHolder> {
    public SelectFootRingAdapter() {
        super(R.layout.item_select_foot_ring, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootEntity item) {
        helper.setText(R.id.tvTitle, item.getFootRingNum());
        helper.itemView.setOnClickListener(v -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, item)
                    .finishForResult(getBaseActivity());
        });
    }
}
