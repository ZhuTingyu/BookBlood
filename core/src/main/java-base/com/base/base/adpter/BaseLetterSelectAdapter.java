package com.base.base.adpter;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.base.base.BaseViewHolder;
import com.base.base.pinyin.LetterSortModel;
import com.base.http.R;
import com.jiang.android.lib.adapter.expand.StickyRecyclerHeadersAdapter;
import com.jiang.android.lib.adapter.expand.StickyRecyclerHeadersDecoration;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public abstract class BaseLetterSelectAdapter<K extends LetterSortModel.LetterSortEntity, B extends BaseViewHolder> extends BaseQuickAdapter<K, B>
        implements StickyRecyclerHeadersAdapter<BaseViewHolder>{

    public BaseLetterSelectAdapter(@LayoutRes int id, List<K> data) {
        super(id, data);
    }

    @Override
    public long getHeaderId(int position) {
        try {
            return getItem(position).getLetter().charAt(0);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public BaseViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_letter_header, viewGroup, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        String showValue = String.valueOf(getItem(position).getLetter().charAt(0));
        if ("$".equals(showValue)) {
            textView.setText("");
        } else if ("%".equals(showValue)) {
            textView.setText("");
        } else {
            textView.setText(showValue);
        }
    }

    public void initHead(){
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(this);
        getRecyclerView().addItemDecoration(headersDecor);
        getRecyclerView().addItemDecoration(new DividerDecoration(mContext));
        this.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });
    }
    public void initHead(RecyclerView recyclerView){
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(this);
        recyclerView.addItemDecoration(headersDecor);
        recyclerView.addItemDecoration(new DividerDecoration(mContext));
        this.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });
    }

}
