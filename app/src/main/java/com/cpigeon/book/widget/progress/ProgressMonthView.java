package com.cpigeon.book.widget.progress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import com.cpigeon.book.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

import java.util.List;

/**
 * 精美进度风格
 * Created by huanghaibin on 2018/2/8.
 */

public class ProgressMonthView extends MonthView {

    private int mRadius;

    private Paint mPointPaint = new Paint();
    private int mPointRadius;
    protected Paint mCurDayTextPaints = new Paint();
    protected Paint mCurMonthTextPaints = new Paint();
    protected Paint mSchemeTextPaints = new Paint();

    public ProgressMonthView(Context context) {
        super(context);

        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);

        mPointPaint.setColor(Color.RED);

        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mPointPaint);
        //4.0以上硬件加速会导致无效
        mPointPaint.setMaskFilter(new BlurMaskFilter(28, BlurMaskFilter.Blur.SOLID));


        mCurDayTextPaints.reset();
        mCurDayTextPaints.setTypeface(Typeface.createFromAsset(context.getAssets(), "SF-PRO-TEXT_REGULAR.TTF"));
        mCurDayTextPaints.setAntiAlias(true);
        mCurDayTextPaints.setTextAlign(Paint.Align.CENTER);
        mCurDayTextPaints.setFakeBoldText(false);
        mCurDayTextPaints.setColor(Color.parseColor("#B3B3B3"));//当天日期字体颜色
        mCurDayTextPaints.setTextSize(dipToPx(context, 18));


        mCurMonthTextPaints.reset();
        mCurMonthTextPaints.setTypeface(Typeface.createFromAsset(context.getAssets(), "SF-PRO-TEXT_REGULAR.TTF"));
        mCurMonthTextPaints.setAntiAlias(true);
        mCurMonthTextPaints.setTextAlign(Paint.Align.CENTER);
        mCurMonthTextPaints.setFakeBoldText(false);
        mCurMonthTextPaints.setColor(Color.parseColor("#B3B3B3"));//当月日期字体颜色
        mCurMonthTextPaints.setTextSize(dipToPx(context, 18));


        mSchemeTextPaints.reset();
        mSchemeTextPaints.setAntiAlias(true);
        mSchemeTextPaints.setTypeface(Typeface.createFromAsset(context.getAssets(), "SF-PRO-TEXT_REGULAR.TTF"));
        mSchemeTextPaints.setStyle(Paint.Style.FILL);
        mSchemeTextPaints.setTextAlign(Paint.Align.CENTER);
        mSchemeTextPaints.setColor(Color.parseColor("#FFFFFF"));
        mSchemeTextPaints.setFakeBoldText(false);
        mSchemeTextPaints.setTextSize(dipToPx(context, 18));

    }

    @Override
    protected void onPreviewHook() {
//        mRadius = Math.min(mItemWidth, mItemHeight) / 11 * 4;
        mRadius = Math.min(mItemWidth, mItemHeight) / 14 * 4;
    }

    /**
     * 绘制选中的日期
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return 是否绘制 onDrawScheme
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        Log.d("xiaohls", "绘制选中的日期: " + calendar.getDay());
        return true;
    }

    /**
     * 绘制标记的日期
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
//        Log.d("xiaohls", "绘制标记的日期: " + calendar.getDay() + "-->" + calendar.getWeek());

        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);


        List<Calendar.Scheme> schemes = calendar.getSchemes();

//        mPointPaint.setColor(schemes.get(0).getShcemeColor());//You can also use three fixed Paint 你也可以使用三个Paint对象
//        int rightTopX = (int) (cx + mRadius * Math.cos(-10 * Math.PI / 180));
//        int rightTopY = (int) (cy + mRadius * Math.sin(-10 * Math.PI / 180));
//        canvas.drawCircle(rightTopX, rightTopY, mPointRadius, mPointPaint);
//
//        mPointPaint.setColor(schemes.get(1).getShcemeColor());
//        int leftTopX = (int) (cx + mRadius * Math.cos(-140 * Math.PI / 180));
//        int leftTopY = (int) (cy + mRadius * Math.sin(-140 * Math.PI / 180));
//        canvas.drawCircle(leftTopX, leftTopY, mPointRadius, mPointPaint);
//
//        mPointPaint.setColor(schemes.get(2).getShcemeColor());
//        int bottomX = (int) (cx + mRadius * Math.cos(100 * Math.PI / 180));
//        int bottomY = (int) (cy + mRadius * Math.sin(100 * Math.PI / 180));
//        canvas.drawCircle(bottomX, bottomY, mPointRadius, mPointPaint);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sign_l);
        Rect rect1 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect rect2 = new Rect(x, y + mItemHeight / 2 - mRadius, x + mItemWidth / 2 + mRadius, y + mItemHeight / 2 + mRadius);


        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sign_r);
        Rect rect3 = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        Rect rect4 = new Rect(x + mItemWidth / 2 - mRadius, y + mItemHeight / 2 - mRadius, x + mItemWidth, y + mItemHeight / 2 + mRadius);

        Paint mPaint = new Paint();
        Calendar.Scheme mType = schemes.get(0);


        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sign_no);
        Rect rect5 = new Rect(0, 0, bitmap3.getWidth(), bitmap3.getHeight());
        Rect rect6 = new Rect(x + mItemWidth / 2 - mRadius, y + mItemHeight / 2 - mRadius, x + mItemWidth / 2 + mRadius, y + mItemHeight / 2 + mRadius);


        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sign_yes);
        Rect rect7 = new Rect(0, 0, bitmap4.getWidth(), bitmap4.getHeight());
        Rect rect8 = new Rect(x + mItemWidth / 2 - mRadius, y + mItemHeight / 2 - mRadius, x + mItemWidth / 2 + mRadius, y + mItemHeight / 2 + mRadius);


        switch (mType.getType()) {
            case 0:
                //默认无标记

                break;
            case 1:
                //图片向左
                canvas.drawBitmap(bitmap, rect1, rect2, mPaint);
                break;
            case 2:
                //图片向右
                canvas.drawBitmap(bitmap2, rect3, rect4, mPaint);
                break;
            case 3:
                //图片向左向右都有
                switch (calendar.getWeek()) {
                    case 6:
                        //周六
                        canvas.drawBitmap(bitmap, rect1, rect2, mPaint);
                        break;
                    case 0:
                        //周日
                        canvas.drawBitmap(bitmap2, rect3, rect4, mPaint);
                        break;
                    default:
                        canvas.drawBitmap(bitmap, rect1, rect2, mPaint);
                        canvas.drawBitmap(bitmap2, rect3, rect4, mPaint);
                }
                break;
            case 5:
                //未领取的礼包
                canvas.drawBitmap(bitmap3, rect5, rect6, mPaint);
                break;

            case 6:
                //已领取的礼包
                canvas.drawBitmap(bitmap4, rect7, rect8, mPaint);
                break;
        }
    }

    /**
     * 绘制日历文本
     *
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {

        Log.d("xiaohls", "绘制日历文本: " + calendar.getDay());

        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        try {
            List<Calendar.Scheme> schemes = calendar.getSchemes();

            Log.d("xiaohlsf", "onDrawText: " + schemes.size());

            Calendar.Scheme mType = schemes.get(0);

            switch (mType.getType()) {
                case 5:

                    break;
                case 6:

                    break;
                default:
                    if (hasScheme) {
//                           calendar.isCurrentDay() ? mCurDayTextPaints :
                        canvas.drawText(String.valueOf(calendar.getDay()),
                                cx,
                                baselineY,

                                        calendar.isCurrentMonth() ? mSchemeTextPaints : mOtherMonthTextPaint);

                    } else {
//                          calendar.isCurrentDay() ? mCurDayTextPaints :
                        canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,

                                        calendar.isCurrentMonth() ? mCurMonthTextPaints : mOtherMonthTextPaint);
                    }
            }

        } catch (Exception e) {
            if (hasScheme) {
//                     calendar.isCurrentDay() ? mCurDayTextPaints :
                canvas.drawText(String.valueOf(calendar.getDay()),
                        cx,
                        baselineY,

                                calendar.isCurrentMonth() ? mSchemeTextPaints : mOtherMonthTextPaint);

            } else {
//                  calendar.isCurrentDay() ? mCurDayTextPaints :
                canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,

                                calendar.isCurrentMonth() ? mCurMonthTextPaints : mOtherMonthTextPaint);
            }
        }
    }


    /**
     * 获取角度
     *
     * @param progress 进度
     * @return 获取角度
     */
    private static int getAngle(int progress) {
        Log.d("xiaohls", "onDrawSelected: 16");
        return (int) (progress * 3.6);
    }


    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
