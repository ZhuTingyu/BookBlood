package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class NewTrainPigeonListAdapter extends BaseQuickAdapter<PigeonEntity, BaseViewHolder> {

    public NewTrainPigeonListAdapter() {
        super(R.layout.item_new_train_pigeon, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {
        helper.setText(R.id.tvFootNumber, item.getFootRingNum());
        if (mOnDeleteListener != null) {
            helper.getView(R.id.imgDel).setOnClickListener(v -> {
                mOnDeleteListener.onDelete(helper.getAdapterPosition());
            });
        }
    }

    public interface OnDeleteListener {
        void onDelete(int position);
    }

    private OnDeleteListener mOnDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }
}
