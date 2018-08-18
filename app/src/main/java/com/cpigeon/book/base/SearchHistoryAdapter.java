package com.cpigeon.book.base;

import android.view.View;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.SearchHistoryEntity;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public class SearchHistoryAdapter extends BaseQuickAdapter<SearchHistoryEntity, BaseViewHolder> {

    private OnDeleteClickListener mOnDeleteClickListener;

    public SearchHistoryAdapter() {
        super(R.layout.item_search_history, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistoryEntity item) {
        helper.setText(R.id.tvLeft, item.searchTitle);
        helper.getView(R.id.imgRight).setOnClickListener(v -> {
            if(mOnDeleteClickListener != null){
                mOnDeleteClickListener.delete(helper.getAdapterPosition());
                remove(helper.getAdapterPosition());
            }
        });
    }

    public interface OnDeleteClickListener{
        void delete(int position);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        mOnDeleteClickListener = onDeleteClickListener;
    }
}
