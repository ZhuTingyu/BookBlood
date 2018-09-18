package com.cpigeon.book.module.trainpigeon.adpter;

import android.view.View;
import android.widget.ImageView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class NewTrainAddPigeonAdapter extends BaseQuickAdapter<PigeonEntity, BaseViewHolder> {

    public NewTrainAddPigeonAdapter() {
        super(R.layout.item_new_train_add_pigeon, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {
        helper.setText(R.id.tvFootNumber, item.getFootRingNum());
        helper.setText(R.id.tvColor, item.getPigeonPlumeName());
        helper.setText(R.id.tvBlood, item.getPigeonBloodName());

        ImageView imgAdd = helper.getView(R.id.imgAdd);

        if(item.isSelect()){
            imgAdd.setVisibility(View.GONE);
        }else {
            imgAdd.setVisibility(View.VISIBLE);
        }

        helper.getView(R.id.imgAdd).setOnClickListener(v -> {
            if (mOnAddPigeonListener != null) {
                mOnAddPigeonListener.add(v, helper.getAdapterPosition());
            }
        });
    }

    public void setSelect(int position, boolean isSelect){
        getData().get(position).setSelecte(isSelect);
        notifyItemChanged(position);
    }

    public List<PigeonEntity> getNotSelectAll(){
        List<PigeonEntity> pigeonEntities = Lists.newArrayList();
        for (PigeonEntity entity : getData()) {
            if (!entity.isSelect()) {
                pigeonEntities.add(entity);
                entity.setSelecte(true);
            }
        }
        return pigeonEntities;
    }

    public interface OnAddPigeonListener {
        void add(View view, int position);
    }

    private OnAddPigeonListener mOnAddPigeonListener;

    public void setOnAddPigeonListener(OnAddPigeonListener onAddPigeonListener) {
        mOnAddPigeonListener = onAddPigeonListener;
    }
}
