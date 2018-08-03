package com.cpigeon.book.test;

import android.support.v7.widget.RecyclerView;

import com.base.base.adpter.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/3/7.
 */

public class VideoListFragment extends BaseQuickAdapter {

    public VideoListFragment(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected String getEmptyViewText() {
        return null;
    }

    @Override
    protected int getEmptyViewImage() {
        return 0;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
