package com.cpigeon.book.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.idst.nls.internal.utils.L;
import com.base.util.Utils;
import com.cpigeon.book.R;


/**
 * Created by Zhu TingYu on 2018/7/31.
 */

public class LineInputView extends RelativeLayout {

    private static final int LEFT_TEXT_SIZE = 14;
    private static final int RIGHT_TEXT_SIZE = 14;

    int mWeight;
    int mLeftColor;
    int mRightColor;
    int mLeftTextSize;
    int mRightTextSize;
    String mLeftString;
    String mRightString;
    boolean mIsLookState;
    Drawable mDrawableRight;
    boolean mIsNotNull;
    boolean mIsCanEdit;


    TextView mTextView;
    ClickGetFocusEditText mEditText;
    ImageView mImgRight;
    LinearLayout mLlContent;

    public interface OnRightClickListener {
        void click(LineInputView lineInputView);
    }

    private OnRightClickListener mOnRightClickListener;

    public LineInputView(Context context) {
        this(context, null);
    }

    public LineInputView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttr(attrs);
        initView();
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getWidth() / 3, ViewGroup.LayoutParams.MATCH_PARENT);
        mTextView.setLayoutParams(params);
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    }

    @SuppressLint("Recycle")
    private void readAttr(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.LineInputView);
        mWeight = array.getInteger(R.styleable.LineInputView_lineInputView_Weight, 4);
        mLeftColor = array.getColor(R.styleable.LineInputView_lineInputView_LeftTextColor, Color.BLACK);
        mRightColor = array.getColor(R.styleable.LineInputView_lineInputView_RightTextColor, Color.BLACK);
        mLeftTextSize = array.getColor(R.styleable.LineInputView_lineInputView_LeftTextSize, LEFT_TEXT_SIZE);
        mRightTextSize = array.getColor(R.styleable.LineInputView_lineInputView_RightTextSize, RIGHT_TEXT_SIZE);
        mLeftString = array.getString(R.styleable.LineInputView_lineInputView_LeftString);
        mRightString = array.getString(R.styleable.LineInputView_lineInputView_RightString);
        mIsLookState = array.getBoolean(R.styleable.LineInputView_lineInputView_IsLookState, false);
        mDrawableRight = array.getDrawable(R.styleable.LineInputView_lineInputView_DrawableRight);
        mIsNotNull = array.getBoolean(R.styleable.LineInputView_lineInputView_IsNotNull, false);
        mIsCanEdit = array.getBoolean(R.styleable.LineInputView_lineInputView_IsCanEdit, true);
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_line_input, this, true);
        mTextView = view.findViewById(R.id.tvLeft);
        mEditText = view.findViewById(R.id.etRight);
        mImgRight = view.findViewById(R.id.imgRight);
        mLlContent = view.findViewById(R.id.llContent);

        mTextView.setText(mLeftString);
        mTextView.setTextColor(mLeftColor);
        mTextView.setTextSize(mLeftTextSize);

        mEditText.setText(mRightString);
        mEditText.setTextColor(mRightColor);
        mEditText.setTextSize(mRightTextSize);

        if (mDrawableRight != null) {
            mImgRight.setVisibility(VISIBLE);
            mImgRight.setImageDrawable(mDrawableRight);
        }

        setCanEdit(mIsCanEdit);

    }

    public void setNotNullState(boolean isLookState){
        if (isLookState) {
            mEditText.setOnFocusChangeListener((v, hasFocus) -> {
                setNotNullDrawable(hasFocus && mIsNotNull);
            });
        } else {
            mEditText.setOnFocusChangeListener(null);
            setNotNullDrawable(mIsNotNull);
        }
    }

    public void setTitle(@StringRes int resId) {
        mTextView.setText(Utils.getString(resId));
    }

    public void setTitle(String title) {
        mTextView.setText(title);
    }

    public void setContent(@StringRes int resId) {
        mEditText.setText(Utils.getString(resId));
    }

    public void setContent(String content) {
        mEditText.setText(content);
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        mOnRightClickListener = onRightClickListener;
        if(mIsCanEdit){
            mImgRight.setOnClickListener(v -> {
                mOnRightClickListener.click(this);
            });
        }else {
            mLlContent.setOnClickListener(v -> {
                mOnRightClickListener.click(this);
            });
        }
    }

    public String getContent() {
        return mEditText.getText().toString();
    }

    public ClickGetFocusEditText getEditText() {
        return mEditText;
    }

    public void setLeftColor(@ColorRes int leftColor) {
        mTextView.setTextColor(Utils.getColor(leftColor));
    }

    public void setRightColor(int rightColor) {
        mEditText.setTextColor(Utils.getColor(rightColor));
    }

    public void setLeftTextSize(int leftTextSize) {
        mTextView.setTextSize(leftTextSize);
    }

    public void setRightTextSize(int rightTextSize) {
        mEditText.setTextSize(rightTextSize);
    }

    public void setEditState(boolean isCanEdit) {
        mEditText.setCanEdit(isCanEdit);
    }

    public void setCanEdit(boolean canEdit) {
        mIsCanEdit = canEdit;
        setEditState(canEdit);
    }

    private void setNotNullDrawable(boolean isNotNull) {
        if (isNotNull) {
            mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , Utils.getDrawable(R.drawable.svg_not_null), null);
        } else {
            mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }
}
