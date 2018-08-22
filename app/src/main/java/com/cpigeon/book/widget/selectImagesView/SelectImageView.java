package com.cpigeon.book.widget.selectImagesView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Zhu TingYu on 2018/8/22.
 */

public class SelectImageView extends LinearLayout {

    RecyclerView mRecyclerView;

    public SelectImageView(Context context) {
        super(context);
        initView(context);
    }

    public SelectImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SelectImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,6));
    }

}
