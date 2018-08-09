package com.cpigeon.book.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import butterknife.OnClick;

/**
 * Created by Zhu TingYu on 2018/7/31.
 */

public class ClickGetFocusEditText extends AppCompatEditText {

    boolean isCanEdit = true;

    private OnClickAndHaveFocusListener mOnClickAndHaveFocusListener;

    public ClickGetFocusEditText(Context context) {
        this(context, null);
    }

    public ClickGetFocusEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickGetFocusEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isCanEdit){
            if (event.getAction() == MotionEvent.ACTION_UP) {
                setFocusableInTouchMode(true);
                if(mOnClickAndHaveFocusListener != null){
                    mOnClickAndHaveFocusListener.clickAndFocus();
                }
            }
            return super.onTouchEvent(event);
        }else {
            return false;
        }
    }

    public void setCanEdit(boolean canEdit) {
        isCanEdit = canEdit;
    }

    public boolean isCanEdit() {
        return isCanEdit;
    }

    public interface OnClickAndHaveFocusListener{
        void clickAndFocus();
    }

    public void setOnClickAndHaveFocusListener(OnClickAndHaveFocusListener onClickAndHaveFocusListener) {
        mOnClickAndHaveFocusListener = onClickAndHaveFocusListener;
    }
}
