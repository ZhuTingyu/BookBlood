package com.cpigeon.book.module.trainpigeon.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class NewTrainAddPigeonAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public NewTrainAddPigeonAdapter() {
        super(R.layout.item_new_train_add_pigeon, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvFootNumber, "2018-22-123456");
        helper.setText(R.id.tvColor, "雨点");
        helper.setText(R.id.tvBlood, "詹森");
        helper.getView(R.id.imgAdd).setOnClickListener(v -> {
            if(mOnAddPigeonListener != null){
                mOnAddPigeonListener.add(helper.getAdapterPosition());
            }
        });
    }

    public interface OnAddPigeonListener{
        void add(int position);
    }

    private OnAddPigeonListener mOnAddPigeonListener;

    public void setOnAddPigeonListener(OnAddPigeonListener onAddPigeonListener) {
        mOnAddPigeonListener = onAddPigeonListener;
    }
}
