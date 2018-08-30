package com.cpigeon.book.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/8/30.
 */

public class OrderTextView extends LinearLayout {

    private TextView mTvTitle;
    private ImageView mImgOrder;

    private String mTitle;
    boolean mIsP;

    public OrderTextView(Context context) {
        super(context);
        initView(context);
    }

    public OrderTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView(context);
    }

    public OrderTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView(context);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.OrderTextView);
        mTitle = array.getString(R.styleable.OrderTextView_orderTextView_title);
        mIsP= array.getBoolean(R.styleable.OrderTextView_orderTextView_title, true);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_order_text_view, this, true);
        mTvTitle = view.findViewById(R.id.tvTitle);
        mImgOrder = view.findViewById(R.id.imgOrder);

        mTvTitle.setText(mTitle);
        if(mIsP){
            mImgOrder.setImageResource(R.mipmap.ic_order_p);
        }else {
            mImgOrder.setImageResource(R.mipmap.ic_order_n);
        }

        this.setOnClickListener(v -> {
            switchStatus();
        });

    }

    private void switchStatus(){
        if(mIsP){
            mImgOrder.setImageResource(R.mipmap.ic_order_n);
            mIsP = false;
        }else {
            mImgOrder.setImageResource(R.mipmap.ic_order_p);
            mIsP = true;
        }
        if(mOnStatusListener != null){
            mOnStatusListener.status(mIsP);
        }

    }

    public interface OnStatusListener{
        void status(boolean isP);
    }
    private OnStatusListener mOnStatusListener;

    public void setOnStatusListener(OnStatusListener onStatusListener) {
        mOnStatusListener = onStatusListener;
    }
}
