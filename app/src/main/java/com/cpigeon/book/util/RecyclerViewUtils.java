package com.cpigeon.book.util;

import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.base.widget.recyclerview.XRecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.book.R;
import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/19.
 */

public class RecyclerViewUtils {
    public static void setLoadMoreCallBack(XRecyclerView xRecyclerView, BaseQuickAdapter adapter, List data) {
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
        xRecyclerView.setRefreshing(false);
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

    public static void addItemDecorationLine(RecyclerView recyclerView) {
        addItemDecorationLine(recyclerView, 0);
    }

    public static void addItemDecorationLine(RecyclerView recyclerView, int margin) {
        addItemDecorationLine(recyclerView, R.color.color_line, ScreenTool.dip2px(0.5f), margin);
    }

    public static void addItemDecorationLine(RecyclerView recyclerView, @ColorRes int color, int size, int margin) {
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(recyclerView.getContext())
//                .visibilityProvider(new FlexibleDividerDecoration.VisibilityProvider() {
//                    @Override
//                    public boolean shouldHideDivider(int position, RecyclerView parent) {
//
//                        if (quickAdapter.getHeaderLayoutCount() >= 1 && position == 0) {
//                            return true;
//                        } else
//                            return false;
//                    }
//                })
                .margin(ScreenTool.dip2px(margin))
                .colorResId(color).size(size).build());
    }


}
