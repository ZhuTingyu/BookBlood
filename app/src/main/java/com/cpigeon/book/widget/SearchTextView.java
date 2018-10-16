package com.cpigeon.book.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.Utils;
import com.base.util.utility.KeyboardUtils;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class SearchTextView extends RelativeLayout {

    private EditText mEdSearch;
    private TextView mTvCancel;
    private OnSearchTextClickListener listener;

    private boolean mIsInTop;


    public SearchTextView(Context context) {
        super(context);
        initView(context);
    }

    public SearchTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttr(attrs);
        initView(context);
    }

    public SearchTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(attrs);
        initView(context);
    }

    @SuppressLint("Recycle")
    private void getAttr(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs,R.styleable.SearchTextView);
        mIsInTop = array.getBoolean(R.styleable.SearchTextView_searchTextView_is_in_top, true);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_search_text, this);

        mEdSearch = view.findViewById(R.id.edSearch);
        mTvCancel = view.findViewById(R.id.tvCancel);

        mEdSearch.setOnKeyListener((View v, int keyCode, KeyEvent event) -> {
            if ((keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER)
                    && event.getAction() == KeyEvent.ACTION_UP) {
                v.clearFocus();
                if(listener != null){
                    listener.search(mEdSearch.getText().toString());
                    KeyboardUtils.hideSoftInput(this);
                }
            }
            return false;
        });

        mTvCancel.setOnClickListener(v -> {
            if(listener != null){
                listener.cancel();
            }
        });

        if(mIsInTop){
            setBackgroundColor(Utils.getColor(R.color.colorPrimary));
            mTvCancel.setTextColor(Color.WHITE);
            mEdSearch.setBackgroundResource(R.drawable.shape_bg_search_edit);
        }else {
            setBackgroundColor(Utils.getColor(R.color.white));
            mTvCancel.setTextColor(Color.BLACK);
            mEdSearch.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_b_color);
        }
    }

    public void setHint(@StringRes int resId){
        mEdSearch.setHint(resId);
    }

    public EditText getEdSearch() {
        return mEdSearch;
    }

    public interface OnSearchTextClickListener{
        void search(String key);
        void cancel();
    }

    public void setOnSearchTextClickListener(OnSearchTextClickListener listener) {
        this.listener = listener;
    }
}
