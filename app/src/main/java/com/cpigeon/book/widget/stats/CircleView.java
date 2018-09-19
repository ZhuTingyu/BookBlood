package com.cpigeon.book.widget.stats;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.base.util.regex.RegexUtils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.util.MathUtil;

/**
 * Created by Zhu TingYu on 2018/9/17.
 */

public class CircleView extends View {
    private Paint mPaint;
    private float mCircleWidth = 10;
    private RectF mRectF;

    private int mDataColor;
    private int mOtherColor;

    private float mDataMax = 0;
    private float mDataCount = 0;

    public CircleView(Context context) {
        super(context);
        initView();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();  //创建画笔
        mPaint.setAntiAlias(true);  //设置绘制时抗锯齿
        mPaint.setStyle(Paint.Style.STROKE); //设置绘画空心（比如画圆时画的是空心圆而不是实心圆）
        mPaint.setStrokeWidth(mCircleWidth);//设置画笔线宽
    }

    public void bindData(int max, int count) {
        mDataMax = max;
        mDataCount = count;
        invalidate();
    }

    public String getScale(){
        float scale = (mDataCount / mDataMax) * 100;
        return MathUtil.doubleformat(scale,2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mRectF = new RectF(mCircleWidth, mCircleWidth,
                getWidth() - mCircleWidth, getHeight() - mCircleWidth);
        if (mDataCount == 0) {
            mPaint.setColor(mOtherColor);
            canvas.drawArc(mRectF, 90, 360, false, mPaint);
            return;
        }

        float scale = mDataCount / mDataMax;
        //数量的比例
        float scaleC = (360 * scale);
        mPaint.setColor(mDataColor);
        canvas.drawArc(mRectF, -90, scaleC, false, mPaint);

        mPaint.setColor(mOtherColor);
        canvas.drawArc(mRectF, scaleC - 90, 360 - (360 * scale), false, mPaint);
    }

    public void setDataColor(int dataColor) {
        mDataColor = dataColor;
    }

    public void setOtherColor(int otherColor) {
        mOtherColor = otherColor;
    }
}
