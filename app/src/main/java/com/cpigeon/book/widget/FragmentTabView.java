package com.cpigeon.book.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/27.
 */

public class FragmentTabView extends LinearLayout {

    private int mCPosition = -1;
    private int mNormalTextColor;
    private int mSelectTextColor;
    private float mNormalTextSize;
    private float mSelectTextSize;
    private float mTextScale;

    List<TextView> mTextViews = Lists.newArrayList();
    ValueAnimator bigAnim;
    ValueAnimator smallAnim;

    public FragmentTabView(Context context) {
        super(context);
        initView(context);
    }

    public FragmentTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        readAttrs(attrs);
        initView(context);
    }

    public FragmentTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs);
        initView(context);
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.FragmentTabView);
        mNormalTextColor = array.getColor(R.styleable.FragmentTabView_normalTextColor, Utils.getColor(R.color.color_text_title));
        mSelectTextColor = array.getColor(R.styleable.FragmentTabView_selectTextColor, Color.BLACK);
        mNormalTextSize = array.getDimension(R.styleable.FragmentTabView_normalTextSize, 14f);
        mSelectTextSize = array.getDimension(R.styleable.FragmentTabView_selectTextSize, 18f);
    }

    private void initView(Context context) {
        mTextScale = mSelectTextSize / mNormalTextSize;
        /*bigAnim = ValueAnimator.ofFloat(mSelectTextSize / mNormalTextSize, 1);
        smallAnim = ValueAnimator.ofFloat(1, mSelectTextSize / mNormalTextSize);
        bigAnim.setDuration(500);
        smallAnim.setDuration(500);*/
    }

    public void setTitles(List<String> titles) {
        for (int i = 0; i < titles.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setTextColor(mNormalTextColor);
            textView.setTextSize(mNormalTextSize);
            textView.setGravity(Gravity.CENTER);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            textView.setLayoutParams(params);
            textView.setText(titles.get(i));
            int finalI = i;
            textView.setOnClickListener(v -> {
                setSelect(finalI);
            });
            addView(textView);
            mTextViews.add(textView);
        }
    }

    public void setSelect(int position) {
        TextView s = mTextViews.get(position);
        if(mCPosition != -1){
            TextView c = mTextViews.get(mCPosition);
            c.setScaleX(1);
            c.setScaleY(1);
            c.setTextColor(mNormalTextColor);
            /*smallAnim.addUpdateListener(animation -> {
                float scale = (float) animation.getAnimatedValue();
                c.setTextScaleX(scale);
            });
            smallAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    s.setTextColor(mNormalTextColor);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            smallAnim.start();*/
        }
        s.setScaleX(mTextScale);
        s.setScaleY(mTextScale);
        s.setTextColor(mSelectTextColor);

        /*bigAnim.addUpdateListener(animation -> {
            float scale = (float) animation.getAnimatedValue();
            s.setTextScaleX(scale);
        });

        bigAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                s.setTextColor(mSelectTextColor);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        bigAnim.start();*/
        mCPosition = position;

        if(mOnSelectListener != null){
            mOnSelectListener.select(position);
        }
    }

    public interface OnSelectListener{
        void select(int position);
    }

    private OnSelectListener mOnSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }
}
