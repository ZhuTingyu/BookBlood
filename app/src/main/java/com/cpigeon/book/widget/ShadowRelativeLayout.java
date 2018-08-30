package com.cpigeon.book.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.base.util.Utils;
import com.base.util.system.ScreenTool;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class ShadowRelativeLayout extends RelativeLayout {
    private Paint mPaint;
    private int mShadowColor;
    public ShadowRelativeLayout(Context context) {
        super(context);
    }

    public ShadowRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShadowRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addShadow(@ColorRes int resId) {
        mShadowColor = Utils.getColor(resId);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制阴影，param1：模糊半径；param2：x轴大小：param3：y轴大小；param4：阴影颜色
        mPaint.setShadowLayer(5F, 10F, 10F, mShadowColor);
        RectF rect = new RectF(0 , 0, getWidth() - ScreenTool.dip2px(10)
                , getHeight() - ScreenTool.dip2px(10));
        canvas.drawRoundRect(rect, (float)10, (float)10, mPaint);
    }

}
