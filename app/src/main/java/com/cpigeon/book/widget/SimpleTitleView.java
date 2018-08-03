package com.cpigeon.book.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/7/10.
 */

public class SimpleTitleView extends LinearLayout{
    ImageView titleImg;
    TextView titleText;

    int normalTextColor;
    int pressTextColor;
    int normalImage;
    int pressImage;

    Drawable image;


    public SimpleTitleView(Context context) {
        this(context, null);
    }

    public SimpleTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAtr(attrs);
        initView(context);
    }

    private void initAtr(AttributeSet attrs) {
        if(attrs == null){
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleTitleView);
        normalTextColor = array.getColor(R.styleable.SimpleTitleView_simpleTitleView_DefaultTextColor, Color.BLACK);
        pressTextColor = array.getColor(R.styleable.SimpleTitleView_simpleTitleView_PressTextColor, Color.BLACK);
        image = array.getDrawable(R.styleable.SimpleTitleView_simpleTitleView_image);

    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, this, true);
        titleImg =  view.findViewById(R.id.titleImg);
        titleText =  view.findViewById(R.id.titleText);

        titleText.setTextColor(normalTextColor);
        titleImg.setImageDrawable(image);
    }

    public void setImages(@DrawableRes int normalImage, @DrawableRes int pressImage){
        this.normalImage = normalImage;
        this.pressImage = pressImage;
    }

    public void setPress(boolean isPress){
        if(isPress){
            titleImg.setImageResource(pressImage);
            titleText.setTextColor(pressTextColor);
        }else {
            titleImg.setImageResource(normalImage);
            titleText.setTextColor(normalTextColor);
        }
    }

    public void setTitleText(String content){
        titleText.setText(content);
    }

    public void setTitleText(@StringRes int resId){
        titleText.setText(getResources().getString(resId));
    }


    public void setNormalTextColor(@ColorRes int normalTextColor) {
        this.normalTextColor = normalTextColor;
    }

    public void setPressTextColor(@ColorRes int pressTextColor) {
        this.pressTextColor = pressTextColor;
    }
}
