package com.cpigeon.book.widget.stats;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.util.MathUtil;


/**
 * Created by Zhu TingYu on 2018/9/17.
 */

public class StatView extends LinearLayout {

    private CircleView mCircle;
    private TextView mTvTitle;
    private TextView mTvCount;
    private TextView mTvScale;
    private int mStatW;


    private boolean isDrawView = false;
    String mTitleString;
    int mDataColor;
    int mOtherColor;

    public StatView(Context context) {
        super(context);
        initView(context);
    }

    public StatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView(context);
    }

    public StatView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView(context);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.StatView);
        mTitleString = array.getString(R.styleable.StatView_statView_title);
        mDataColor = array.getColor(R.styleable.StatView_statView_data_color, Color.BLACK);
        mOtherColor = array.getColor(R.styleable.StatView_statView_other_color, Color.BLACK);
        mStatW = array.getDimensionPixelSize(R.styleable.StatView_statView_stat_w, 100);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_stat, this);
        mCircle = view.findViewById(R.id.circle);
        mTvTitle = view.findViewById(R.id.tvTitle);
        mTvCount = view.findViewById(R.id.tvCount);
        mTvTitle.setText(mTitleString);
        mTvScale = findViewById(R.id.tvScale);
        mCircle.setDataColor(mDataColor);
        mCircle.setOtherColor(mOtherColor);
        RelativeLayout rlScale = view.findViewById(R.id.rlScale);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mStatW, mStatW);
        rlScale.setLayoutParams(params);

    }

    public void bindData(int count, int max) {
        mCircle.bindData(max, count);
        mTvCount.setText(String.valueOf(count));
        mTvScale.setText(mCircle.getScale()+"%");

    }
}
