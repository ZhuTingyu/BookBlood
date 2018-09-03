package com.cpigeon.book.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.widget.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView;
import com.cpigeon.book.R;

/**
 * Created by Administrator on 2018/9/3.
 */

public class CustomTabView extends LinearLayout implements IMeasurablePagerTitleView {

    private TextView tv_content;
    private TextView tv_sum;

    public CustomTabView(Context context) {
        super(context);
        initView();
    }

    public CustomTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public CustomTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_custom_tab, this, true);
        tv_content = view.findViewById(R.id.tv_content);
        tv_sum = view.findViewById(R.id.tv_sum);

    }

    public void setContext(String context) {
        tv_content.setText(context);
    }

    public void setSum(String context) {
        tv_sum.setText(context);
    }

    @Override
    public int getContentLeft() {
        return 0;
    }

    @Override
    public int getContentTop() {
        return 0;
    }

    @Override
    public int getContentRight() {
        return 0;
    }

    @Override
    public int getContentBottom() {
        return 0;
    }

    @Override
    public void onSelected(int index, int totalCount) {
//        tv_content.setTextSize(R.dimen.large_text_size);
        tv_content.setTextSize(18);
        tv_content.setTextColor(getContext().getResources().getColor(R.color.color_white));
        tv_sum.setTextColor(getContext().getResources().getColor(R.color.color_white));
    }

    @Override
    public void onDeselected(int index, int totalCount) {
//        tv_content.setTextSize(R.dimen.medium_text_size);
        tv_content.setTextSize(14);
        tv_content.setTextColor(getContext().getResources().getColor(R.color.color_bg_dcedff));
        tv_sum.setTextColor(getContext().getResources().getColor(R.color.color_bg_dcedff));
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

    }
}
