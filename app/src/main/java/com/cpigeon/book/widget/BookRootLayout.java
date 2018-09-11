package com.cpigeon.book.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class BookRootLayout extends RelativeLayout {
    private boolean mDirtyHierarchy;

    public BookRootLayout(Context context) {
        super(context);
    }

    public BookRootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BookRootLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = 2480;
        int h = 3508;
        super.onMeasure(w, h);
    }
}
