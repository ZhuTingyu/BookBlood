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
