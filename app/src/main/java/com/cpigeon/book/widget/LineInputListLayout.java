package com.cpigeon.book.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.base.util.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/7/31.
 */

public class LineInputListLayout extends LinearLayout {

    List<LineInputView> mLineInputViews = Lists.newArrayList();

    private OnInputViewGetFocusListener mOnInputViewGetFocusListener;

    public interface OnInputViewGetFocusListener{
        void getFocus();
    }

    public LineInputListLayout(Context context) {
        this(context, null);
    }

    public LineInputListLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineInputListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void getChildViews(){
        for (int i = 0, len = getChildCount(); i < len; i++) {
            View view = getChildAt(i);
            if (view instanceof LineInputView) {
                LineInputView lineInputView = (LineInputView) view;
                mLineInputViews.add(lineInputView);
                lineInputView.getEditText().setOnClickAndHaveFocusListener(() -> {
                    if(mOnInputViewGetFocusListener != null){
                        mOnInputViewGetFocusListener.getFocus();
                    }
                });

            }
        }
    }

    public void setLineInputViewState(boolean isLookStats) {
        for (LineInputView lineInputView : mLineInputViews) {
            lineInputView.setNotNullState(isLookStats);
        }
    }

    public void setOnInputViewGetFocusListener(OnInputViewGetFocusListener onInputViewGetFocusListener) {
        mOnInputViewGetFocusListener = onInputViewGetFocusListener;
    }
}
