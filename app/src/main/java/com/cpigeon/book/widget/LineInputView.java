package com.cpigeon.book.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;


/**
 * Created by Zhu TingYu on 2018/7/31.
 */

public class LineInputView extends RelativeLayout {

    private static final int LEFT_TEXT_SIZE = 13;//  13 sp
    private static final int RIGHT_TEXT_SIZE = 14;//  14sp

    int mWeight;
    int mLeftColor;
    int mRightColor;
    int mContentColor;
    float mLeftTextSize;
    float mRightTextSize;
    String mLeftString;
    String mRightString;
    String mEtHintStr;
    boolean mIsLookState;
    boolean isLeftBold;//左侧字体是否加粗
    Drawable mDrawableRight;
    Drawable mDrawableLeft;
    boolean mIsNotNull;
    boolean mIsCanEdit;
    boolean mIsEditLeft;
    boolean mIsHaveEditBoard;

//    boolean leftTextBold = false;
//    boolean rightTextBold = false;

    int mShowLineDivision;//是否显示分割线
    int mInputType;
    int mRightImageVisible;

    TextView mTextView;
    ClickGetFocusEditText mEditText;
    TextView mTvRight;
    ImageView mImgRight;
    ImageView imgLeft;
    LinearLayout mLlContent;

    View line_division;//分割线
    LinearLayout ll_line;//分割线
    LinearLayout layout_view;//内容总布局
    RelativeLayout rlZ;//总布局

    int content_paddingLeft;
    int content_paddingRight;

    int line_paddingLeft = 0;
    int line_paddingRight = 0;
    boolean left_gravity = true;

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
        mLeftColor = array.getColor(R.styleable.LineInputView_lineInputView_LeftTextColor, Utils.getColor(R.color.color_4c4c4c));
        mRightColor = array.getColor(R.styleable.LineInputView_lineInputView_RightTextColor, Utils.getColor(R.color.color_000000));
        mContentColor = array.getColor(R.styleable.LineInputView_lineInputView_ContentColor, Color.parseColor("#FFFFFF"));
        mLeftTextSize = array.getDimensionPixelSize(R.styleable.LineInputView_lineInputView_LeftTextSize, ScreenTool.sp2px(LEFT_TEXT_SIZE));
        mRightTextSize = array.getDimensionPixelSize(R.styleable.LineInputView_lineInputView_RightTextSize, ScreenTool.sp2px(RIGHT_TEXT_SIZE));
        mLeftString = array.getString(R.styleable.LineInputView_lineInputView_LeftString);
        mRightString = array.getString(R.styleable.LineInputView_lineInputView_RightString);
        mEtHintStr = array.getString(R.styleable.LineInputView_lineInputView_Hint);
        mIsLookState = array.getBoolean(R.styleable.LineInputView_lineInputView_IsLookState, false);
        isLeftBold = array.getBoolean(R.styleable.LineInputView_lineInputView_isLeftBold, false);
        mDrawableRight = array.getDrawable(R.styleable.LineInputView_lineInputView_DrawableRight);
        mDrawableLeft = array.getDrawable(R.styleable.LineInputView_lineInputView_DrawableLeft);
        mIsNotNull = array.getBoolean(R.styleable.LineInputView_lineInputView_IsNotNull, false);
        mIsCanEdit = array.getBoolean(R.styleable.LineInputView_lineInputView_IsCanEdit, true);
        mInputType = array.getInt(R.styleable.LineInputView_lineInputView_InputType, 0);
        mIsEditLeft = array.getBoolean(R.styleable.LineInputView_lineInputView_IsLeft, false);
        mIsHaveEditBoard = array.getBoolean(R.styleable.LineInputView_lineInputView_isShowEditBoard, false);

        mShowLineDivision = array.getInt(R.styleable.LineInputView_lineInputView_isShowLineDivisions, View.VISIBLE);
        mRightImageVisible = array.getInt(R.styleable.LineInputView_lineInputView_isShowRightImage, View.VISIBLE);


        content_paddingLeft = (int) array.getDimension(R.styleable.LineInputView_lineInputView_content_paddingLeft, 0);
        content_paddingRight = (int) array.getDimension(R.styleable.LineInputView_lineInputView_content_paddingRight, 0);

        line_paddingLeft = (int) array.getDimension(R.styleable.LineInputView_lineInputView_line_paddingLift, 0);
        line_paddingRight = (int) array.getDimension(R.styleable.LineInputView_lineInputView_line_paddingRight, 0);


        left_gravity = array.getBoolean(R.styleable.LineInputView_lineInputView_LeftTextGravity, true);

//        leftTextBold = array.getBoolean(R.styleable.LineInputView_leftTextBold, false);
//        rightTextBold = array.getBoolean(R.styleable.LineInputView_rightTextBold, false);

    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_line_input, this, true);
        mTextView = view.findViewById(R.id.tvLeft);
        mEditText = view.findViewById(R.id.etRight);
        mTvRight = view.findViewById(R.id.tvRight);

        mImgRight = view.findViewById(R.id.imgRight);
        mLlContent = view.findViewById(R.id.llContent);
        line_division = view.findViewById(R.id.line_division);
        ll_line = view.findViewById(R.id.ll_line);

        layout_view = view.findViewById(R.id.layout_view);
        rlZ = view.findViewById(R.id.rlZ);

        imgLeft = view.findViewById(R.id.img_left);

        mTextView.setText(mLeftString);
        mTextView.setTextColor(mLeftColor);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);

        if (left_gravity) {
            mTextView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        } else {
            mTextView.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        }


        if (isLeftBold) {
            Typeface font = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
            mTextView.setTypeface(font);
        } else {

        }

//        if (leftTextBold){
//            mTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
//        }
//
//        if (rightTextBold){
//            mEditText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
//        }

        mEditText.setText(mRightString);
        mEditText.setHint(mEtHintStr);
        mEditText.setTextColor(mRightColor);
//        mEditText.setTextSize(ScreenTool.px2sp(mRightTextSize));
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);


        mTvRight.setText(mRightString);
        mTvRight.setHint(mEtHintStr);
        mTvRight.setTextColor(mRightColor);
        mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);

        if (mInputType != 0) {
            mEditText.setInputType(mInputType);
        }
        if (mIsEditLeft) {
            mEditText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            mTvRight.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        } else {
            mEditText.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            mTvRight.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        }


        if (mDrawableLeft != null) {
            imgLeft.setVisibility(VISIBLE);
            imgLeft.setImageDrawable(mDrawableLeft);
        } else {
            imgLeft.setVisibility(GONE);
        }

        if (mDrawableRight != null) {
            mImgRight.setVisibility(VISIBLE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (mIsEditLeft) {
                layoutParams.setMargins(ScreenTool.dip2px(8), 0, 0, 0);
            } else {
                layoutParams.setMargins(ScreenTool.dip2px(16), 0, 0, 0);
            }
            mImgRight.setLayoutParams(layoutParams);
            mImgRight.setImageDrawable(mDrawableRight);
        }

        line_division.setVisibility(mShowLineDivision);
        ll_line.setPadding(line_paddingLeft, 0, line_paddingRight, 0);

//        ScreenTool.dip2px(
        layout_view.setPadding(content_paddingLeft, 0, content_paddingRight, 0);
        rlZ.setBackgroundColor(mContentColor);

        mImgRight.setVisibility(mRightImageVisible);

        setCanEdit(mIsCanEdit);
    }

    public LinearLayout getLlContent() {
        return mLlContent;
    }

    public void setIsLookState(boolean isLookState) {
        if (isLookState) {
            mEditText.setOnFocusChangeListener((v, hasFocus) -> {
                setNotNullDrawable(hasFocus && mIsNotNull);
                if (mIsHaveEditBoard && hasFocus) {
                    mEditText.setBackgroundResource(R.drawable.shape_bg_edit_text_view);
                } else {
                    mEditText.setBackgroundColor(Utils.getColor(R.color.transparent));
                }
            });


        } else {
            if (mIsHaveEditBoard) {
                mEditText.setBackgroundResource(R.drawable.shape_bg_edit_text_view);
            }
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
        setRightText(Utils.getString(resId));
    }

    public void setContent(String content) {
        setRightText(content);
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        mOnRightClickListener = onRightClickListener;
        if (mIsCanEdit) {
            mImgRight.setOnClickListener(v -> {
                mOnRightClickListener.click(this);
                if(mOnClickAndHaveFocusListener != null){
                    mOnClickAndHaveFocusListener.clickAndFocus();
                }
            });
        } else {
            this.setOnClickListener(v -> {
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

    public TextView getTvRight() {
        return mTvRight;
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
        mEditText.setText(StringUtil.isStringValid(rightText) ? rightText : StringUtil.emptyString());
        mTvRight.setText(StringUtil.isStringValid(rightText) ? rightText : StringUtil.emptyString());
    }

    public void setEditState(boolean isCanEdit) {
        mEditText.setCanEdit(isCanEdit);
    }

    public void setCanEdit(boolean canEdit) {
        mIsCanEdit = canEdit;
        if(mIsCanEdit){
            mEditText.setVisibility(VISIBLE);
            mTvRight.setVisibility(GONE);
        }else {
            mEditText.setVisibility(GONE);
            mTvRight.setVisibility(VISIBLE);
        }
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

    private void setEditTextBorder(boolean isVisible) {
        if (isVisible) {
            mEditText.setBackgroundResource(R.drawable.shape_bg_edit_text_view);
        } else {
            mEditText.setBackgroundColor(Utils.getColor(R.color.transparent));
        }
    }

    public void setRightImageVisible(boolean isVisible) {
        mImgRight.setVisibility(isVisible ? VISIBLE : INVISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mOnClickAndHaveFocusListener != null) {
                mOnClickAndHaveFocusListener.clickAndFocus();
            }
        }

        return super.onTouchEvent(event);
    }

    public ImageView getImgRight() {
        return mImgRight;
    }

    private OnClickAndHaveFocusListener mOnClickAndHaveFocusListener;

    public interface OnClickAndHaveFocusListener {
        void clickAndFocus();
    }

    public void setOnClickAndHaveFocusListener(OnClickAndHaveFocusListener onClickAndHaveFocusListener) {
        mOnClickAndHaveFocusListener = onClickAndHaveFocusListener;
        mEditText.setOnClickAndHaveFocusListener(onClickAndHaveFocusListener);
    }
}
