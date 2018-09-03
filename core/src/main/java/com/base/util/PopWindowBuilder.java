package com.base.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;

/**
 * Created by Zhu TingYu on 2018/7/11.
 */

public class PopWindowBuilder {

    PopupWindow mPopupWindow;
    Context mContext;

    public static PopWindowBuilder builder(Context context) {
        PopWindowBuilder builder = new PopWindowBuilder();
        builder.mContext = context;
        builder.mPopupWindow = new PopupWindow(context);
        builder.mPopupWindow.setOutsideTouchable(false);
        builder.mPopupWindow.setFocusable(true);
        builder.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        return builder;
    }

    public PopWindowBuilder setSize(int w, int h) {
        mPopupWindow.setWidth(w);
        mPopupWindow.setHeight(h);
        return this;
    }

    public PopWindowBuilder setBackgroundDrawable(@DrawableRes int drawable) {
        mPopupWindow.setBackgroundDrawable(Utils.getApp().getResources().getDrawable(drawable));
        return this;
    }

    public PopWindowBuilder setBackgroundColor(@ColorRes int color) {
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Utils.getApp().getResources().getColor(color)));
        return this;
    }

    public PopWindowBuilder setView(View view) {
        mPopupWindow.setContentView(view);
        return this;
    }

    public PopWindowBuilder setAnimationStyle(int animationStyle){
        mPopupWindow.setAnimationStyle(animationStyle);
        return this;
    }

    public PopupWindow showAsDropDown(View view, int xoff, int yoff, int gravity) {
        mPopupWindow.showAsDropDown(view, xoff, yoff, gravity);
        return this.mPopupWindow;
    }

    public void showAsDropDown(View view){
        mPopupWindow.showAsDropDown(view);
    }

    public void showAsDropDown(View view,int xoff, int yoff){
        mPopupWindow.showAsDropDown(view, xoff, yoff);
    }

    public PopupWindow showAtLocation(View parent, int xoff, int yoff, int gravity){
        mPopupWindow.showAtLocation(parent, xoff, yoff, gravity);
        return this.mPopupWindow;
    }

}
