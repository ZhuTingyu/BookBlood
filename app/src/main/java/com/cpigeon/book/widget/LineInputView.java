package com.cpigeon.book.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.Utils;
import com.base.util.system.ScreenTool;
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
    float mLeftTextSize;
    float mRightTextSize;
    String mLeftString;
    String mRightString;
    boolean mIsLookState;
    Drawable mDrawableRight;
    boolean mIsNotNull;
    boolean mIsCanEdit;
    boolean mIsLeft;
    int mShowLineDivision;//是否显示分割线
    int mInputType;


    TextView mTextView;
    ClickGetFocusEditText mEditText;
    ImageView mImgRight;
    LinearLayout mLlContent;

    View line_division;//分割线

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

    @SuppressLint({"Recycle", "ResourceAsColor"})
    private void readAttr(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.LineInputView);
        mWeight = array.getInteger(R.styleable.LineInputView_lineInputView_Weight, 4);
        mLeftColor = array.getColor(R.styleable.LineInputView_lineInputView_LeftTextColor, R.color.color_4c4c4c);
        mRightColor = array.getColor(R.styleable.LineInputView_lineInputView_RightTextColor, R.color.color_000000);
        mLeftTextSize = array.getColor(R.styleable.LineInputView_lineInputView_LeftTextSize, LEFT_TEXT_SIZE);
        mRightTextSize = array.getDimension(R.styleable.LineInputView_lineInputView_RightTextSize, RIGHT_TEXT_SIZE);
        mLeftString = array.getString(R.styleable.LineInputView_lineInputView_LeftString);
        mRightString = array.getString(R.styleable.LineInputView_lineInputView_RightString);
        mIsLookState = array.getBoolean(R.styleable.LineInputView_lineInputView_IsLookState, false);
        mDrawableRight = array.getDrawable(R.styleable.LineInputView_lineInputView_DrawableRight);
        mIsNotNull = array.getBoolean(R.styleable.LineInputView_lineInputView_IsNotNull, false);
        mIsCanEdit = array.getBoolean(R.styleable.LineInputView_lineInputView_IsCanEdit, true);
        mInputType = array.getInt(R.styleable.LineInputView_lineInputView_InputType, 0);
        mIsLeft = array.getBoolean(R.styleable.LineInputView_lineInputView_IsLeft, false);


        mShowLineDivision = array.getInt(R.styleable.LineInputView_lineInputView_isShowLineDivisions, View.VISIBLE);


    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_line_input, this, true);
        mTextView = view.findViewById(R.id.tvLeft);
        mEditText = view.findViewById(R.id.etRight);
        mImgRight = view.findViewById(R.id.imgRight);
        mLlContent = view.findViewById(R.id.llContent);
        line_division = view.findViewById(R.id.line_division);

        mTextView.setText(mLeftString);
        mTextView.setTextColor(getResources().getColor(mLeftColor));
        mTextView.setTextSize(mLeftTextSize);

        mEditText.setText(mRightString);
        mEditText.setTextColor(getResources().getColor(mRightColor));
        mEditText.setTextSize(mRightTextSize);
        if (mInputType != 0) {
            mEditText.setInputType(mInputType);
        }
        if (mIsLeft) {
            mEditText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        } else {
            mEditText.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        }

        if (mDrawableRight != null) {
            mImgRight.setVisibility(VISIBLE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (mIsLeft) {
                layoutParams.setMargins(ScreenTool.dip2px(8), 0, 0, 0);
            } else {
                layoutParams.setMargins(ScreenTool.dip2px(16), 0, 0, 0);
            }
            mImgRight.setLayoutParams(layoutParams);
            mImgRight.setImageDrawable(mDrawableRight);
        }

        line_division.setVisibility(mShowLineDivision);


        setCanEdit(mIsCanEdit);
    }

    public void setIsLookState(boolean isLookState) {
        if (isLookState) {
            mEditText.setOnFocusChangeListener((v, hasFocus) -> {
                setNotNullDrawable(hasFocus && mIsNotNull);
            });
        } else {
            mEditText.setOnFocusChangeListener(null);
            mEditText.setBackgroundResource(R.drawable.shape_bg_edit_text_view);
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
        if (mIsCanEdit) {
            mImgRight.setOnClickListener(v -> {
                mOnRightClickListener.click(this);
            });
        } else {
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

    public void setRightText(String rightText) {
        mEditText.setText(rightText);
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
            //mEditText.setBackgroundResource(R.drawable.shape_bg_edit_text_view);
        } else {
            mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            //mEditText.setBackgroundColor(Utils.getColor(R.color.transparent));
        }
    }

    private void setEditTextBorder(boolean isVisible){
        if(isVisible){
            mEditText.setBackgroundResource(R.drawable.shape_bg_edit_text_view);
        }else {
            mEditText.setBackgroundColor(Utils.getColor(R.color.transparent));

        }
    }
}
