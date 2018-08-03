package com.base.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.base.application.BaseApplication;

/**
 * Created by Zhu TingYu on 2018/6/1.
 */

public final class Utils {
    public static Context getApp(){
        return BaseApplication.getAppContext();
    }

    public static String getString(@StringRes int resId){
        return getApp().getResources().getString(resId);
    }
    public static int getColor(@ColorRes int resId){
        return getApp().getResources().getColor(resId);
    }

    public static Drawable getDrawable(@DrawableRes int resId){
        return getApp().getResources().getDrawable(resId);
    }

}
