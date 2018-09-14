package com.cpigeon.book.util;

import com.base.base.adpter.BaseQuickAdapter;
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
            adapter.setLoadMore(true);
            adapter.setEmptyView();
        } else {
            adapter.setLoadMore(false);

            adapter.addData(data);
        }
    }


    public static void setLoadMoreCallBack2(XRecyclerView xRecyclerView, BaseQuickAdapter adapter, List data) {
        xRecyclerView.setRefreshing(false);
        if (data.isEmpty() || data.size() == 0) {
            adapter.setLoadMore(true);
            adapter.setEmptyView();
        } else {
            adapter.setLoadMore(false);
            if (adapter.getHeaderLayoutCount() != 0) {
                adapter.addData(adapter.getHeaderLayoutCount()+1, data);
            } else {
                adapter.addData(data);
            }

        }
    }
}
