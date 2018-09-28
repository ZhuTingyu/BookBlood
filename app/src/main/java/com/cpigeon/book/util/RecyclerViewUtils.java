package com.cpigeon.book.util;

import android.support.v7.widget.RecyclerView;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/19.
 */

public class RecyclerViewUtils {
    public static void setLoadMoreCallBack(XRecyclerView xRecyclerView, BaseQuickAdapter adapter, List data) {
        xRecyclerView.setRefreshing(false);
        if (data.isEmpty() || data.size() == 0) {
            if (adapter.getData().size() == 0) {
                adapter.removeAllHeaderView();
                adapter.setNewData(Lists.newArrayList());
            }
            adapter.setLoadMore(true);
        } else {
            adapter.setLoadMore(false);
            adapter.addData(data);
        }
    }

    public static void setLoadMoreCallBack(RecyclerView mRecyclerView, BaseQuickAdapter adapter, List data) {

        if (data.isEmpty() || data.size() == 0) {
            if (adapter.getData().size() == 0) {
                adapter.removeAllHeaderView();
            }
            adapter.setLoadMore(true);
            adapter.setEmptyView();
        } else {
            adapter.setLoadMore(false);
            adapter.addData(data);
        }
    }


}
