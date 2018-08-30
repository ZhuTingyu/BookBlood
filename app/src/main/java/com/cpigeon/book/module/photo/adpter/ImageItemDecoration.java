package com.cpigeon.book.module.photo.adpter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Zhu TingYu on 2018/8/30.
 */

public class ImageItemDecoration extends RecyclerView.ItemDecoration {
    private int itemSpace;
    private int itemNum;

    /**
     * @param itemSpace item间隔
     * @param itemNum   每行item的个数
     */
    public ImageItemDecoration(int itemSpace, int itemNum) {
        this.itemSpace = itemSpace;
        this.itemNum = itemNum;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = itemSpace;
        outRect.left = itemSpace;
    }
}
