package com.base.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.amap.api.maps.TextureMapView;
import com.luck.picture.lib.widget.PreviewViewPager;

/**
 * Created by Zhu TingYu on 2018/5/18.
 */

public class CustomViewPager extends ViewPager {

    private boolean isCanScroll = true;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置其是否能滑动换页
     * @param isCanScroll false 不能换页， true 可以滑动换页
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return isCanScroll && super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return isCanScroll && super.onTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if(v != this && v instanceof TextureMapView) {
            return true;
        }
        return super.canScroll(v,checkV,dx,x,y);
    }
}
