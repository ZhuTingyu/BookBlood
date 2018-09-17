package com.cpigeon.book.widget.family;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.BloodBookEntity;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;


/**
 * Created by Zhu TingYu on 2018/6/21.
 */

public class FamilyTreeView extends LinearLayout {

    public static final int TYPE_IS_CAN_MOVE_H = 0x00000001;
    public static final int TYPE_IS_CAN_MOVE_V = 0x00000002;
    public static final int TYPE_IS_CAN_MOVE_N = 0x00000003;

    private boolean isMiniModel = false;
    private boolean isPrintModel;
    private boolean isShowLine;

    private int mTypeMove = TYPE_IS_CAN_MOVE_N;
    private static final int COUNT_OF_GENERATIONS = 3; //屏幕中显示的辈数
    private static final int MAX_OF_GENERATIONS = 4; //最大的辈数
    private int countOfGeneration = COUNT_OF_GENERATIONS; //一共显示几代
    private int maxOfGeneration = MAX_OF_GENERATIONS; //最高一代

    private float mLineWidthDp;
    private boolean isHorizontal = true;// 是否横向显示
    private boolean isHaveCurrentGeneration = false; //是否包含当前代
    private int mLineColor;
    private boolean isShowInfoModel;

    private static final int SCROLL_WIDTH = 3;//移动超过3dp，响应滑动，否则属于点击
    private static final float MAX_SCALE = 3f;
    private static final float LEAST_SCALE = 1f;
    private static final float LINE_WIDTH_DP = 1f;//连线宽度2dp
    private int mWidth;
    private int mHeight;


    private int mScrollWidth;//移动范围
    private int mCurrentX;//当前X轴偏移量
    private int mCurrentY;//当前Y轴偏移量
    private int mLastTouchX;//最后一次触摸的X坐标
    private int mLastTouchY;//最后一次触摸的Y坐标
    private int mLastInterceptX;

    private int mLastInterceptY;
    private float startDis;//两个手指放下的距离


    private boolean isTwoTouch = false;
    private boolean isDrawView = false;


    private float mCurrentScale = 1f;//当前缩放比例

    private int startGeneration;//当前的代数
    private Paint mPaint;//连线样式
    private Path mPath;//路径
    Canvas mCanvas;
    private int mLineWidthPX;

    List<LinearLayout> generationLinearLayouts = Lists.newArrayList();

    Point mCurrentPoint = new Point();


    public FamilyTreeView(Context context) {
        this(context, null);
    }

    public FamilyTreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FamilyTreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(attrs);

        mScrollWidth = ScreenTool.dip2px(SCROLL_WIDTH);

        mLineWidthPX = ScreenTool.dip2px(mLineWidthDp);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.reset();
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(mLineWidthPX);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        mPath = new Path();

        addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (!isDrawView) {
                initView();
                if(!isShowInfoModel){
                    LinearLayout first = generationLinearLayouts.get(0);
                    for (int i = 0, len = first.getChildCount(); i < len; i++) {
                        FamilyMember member = (FamilyMember) first.getChildAt(i);
                        member.setCanAdd();
                    }
                }
                isDrawView = true;
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.mCanvas = canvas;
        if (isShowLine) {
            drawPointLine(canvas);
        }
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.FamilyTreeView);
        try {
            mLineWidthDp = (int) array.getFloat(R.styleable.FamilyTreeView_TreeView_LineWidth, LINE_WIDTH_DP);
            mLineColor = array.getColor(R.styleable.FamilyTreeView_TreeVie_LineColor, getResources().getColor(R.color.black));
            isHorizontal = array.getBoolean(R.styleable.FamilyTreeView_TreeView_isHorizontal, true);
            isHaveCurrentGeneration = array.getBoolean(R.styleable.FamilyTreeView_TreeView_isHaveCurrent, true); //是否包含当前代
            countOfGeneration = array.getInteger(R.styleable.FamilyTreeView_TreeView_ShowGeneration, COUNT_OF_GENERATIONS);
            maxOfGeneration = array.getInteger(R.styleable.FamilyTreeView_TreeView_MaxGeneration, MAX_OF_GENERATIONS);
            mTypeMove = array.getInteger(R.styleable.FamilyTreeView_TreeView_moveType, TYPE_IS_CAN_MOVE_N);
            isMiniModel = array.getBoolean(R.styleable.FamilyTreeView_TreeView_isMiniModel, false);
            isPrintModel = array.getBoolean(R.styleable.FamilyTreeView_TreeView_isPrintModel, false);
            isShowLine = array.getBoolean(R.styleable.FamilyTreeView_TreeView_isShowLine, true);
            isShowInfoModel = array.getBoolean(R.styleable.FamilyTreeView_TreeView_isShowInfoModel, false);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }


    public void initView() {
        removeAllViews();
        generationLinearLayouts.clear();
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        setOrientation(isHorizontal ? HORIZONTAL : VERTICAL);

        if (isHaveCurrentGeneration) {
            startGeneration = 0;
        } else {
            startGeneration = 1;
        }

        for (int generationsPoint = startGeneration; generationsPoint < maxOfGeneration; generationsPoint++) {
            int thisGenerationsCount;
            thisGenerationsCount = (int) Math.pow(2, generationsPoint);
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(isHorizontal ? VERTICAL : HORIZONTAL);
            LinearLayout.LayoutParams params;
            if (isHorizontal) {
                params = new LinearLayout.LayoutParams(mWidth / countOfGeneration, ViewGroup.LayoutParams.MATCH_PARENT);
            } else {
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight / countOfGeneration);
            }
            params.gravity = Gravity.CENTER;
            linearLayout.setLayoutParams(params);
            for (int generationsOrder = 0; generationsOrder < thisGenerationsCount; generationsOrder++) {
                linearLayout.addView(getMemberView(getContext(), generationsPoint, generationsOrder, thisGenerationsCount));
            }
            addView(linearLayout);
            generationLinearLayouts.add(linearLayout);
        }
    }


    private View getMemberView(Context context, int generationsPoint, int generationsOrder, int generationCount) {

        LinearLayout.LayoutParams params;
        if (isHorizontal) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , mHeight / generationCount);
        } else {
            params = new LinearLayout.LayoutParams(mWidth / generationCount
                    , ViewGroup.LayoutParams.MATCH_PARENT);
        }

        if (isPrintModel) {
            FamilyPrintModelMemberView view = new FamilyPrintModelMemberView(context, generationsPoint, generationsOrder, isHorizontal);
            view.setLayoutParams(params);
            return view;
        } else {
            FamilyMemberView view = new FamilyMemberView(context, generationsPoint, generationsOrder, isMiniModel, isHorizontal);
            view.setLayoutParams(params);
            if (mOnFamilyClickListener != null) {
                view.setOnMemberClickListener(new FamilyMemberView.OnMemberClickListener() {
                    @Override
                    public void add(int x, int y) {
                        mCurrentPoint.set(x, y);
                        mOnFamilyClickListener.add(x, y);
                    }

                    @Override
                    public void showInfo(PigeonEntity entity) {
                        mOnFamilyClickListener.showInfo(entity);
                    }
                });
            }
            return view;
        }
    }


    private void drawPointLine(Canvas canvas) {
        for (int i = 0; i < generationLinearLayouts.size() - 1; i++) {
            LinearLayout linearLayout = generationLinearLayouts.get(i);
            LinearLayout nextLinearLayout = generationLinearLayouts.get(i + 1);
            int viewCount = linearLayout.getChildCount();
            for (int i1 = 0; i1 < viewCount; i1++) {
                int currentPosition = i1; // 1
                int fatherPosition = (currentPosition * 2);
                int motherPosition = fatherPosition + 1;
                FamilyMember currentView = (FamilyMember) linearLayout.getChildAt(currentPosition);
                FamilyMember mother = (FamilyMember) nextLinearLayout.getChildAt(motherPosition);
                FamilyMember father = (FamilyMember) nextLinearLayout.getChildAt(fatherPosition);
                drawLine(canvas, currentView, mother, linearLayout, nextLinearLayout);
                drawLine(canvas, currentView, father, linearLayout, nextLinearLayout);
            }
        }
    }

    private void drawLine(Canvas canvas, FamilyMember currentView, FamilyMember toView, LinearLayout currentLl, LinearLayout nextLl) {
        View currentInfo = currentView.getInfoView();
        View toViewInfo = toView.getInfoView();
        mPath.reset();
        float fromX, fromY, toX, toY;
        if (isHorizontal) {
            fromX = currentLl.getX() + currentInfo.getX() + currentInfo.getWidth();
            fromY = currentView.getTop() + currentInfo.getY() + (currentInfo.getHeight() / 2);

            toX = nextLl.getX() + toViewInfo.getX();
            toY = toView.getTop() + toViewInfo.getY() + (toViewInfo.getHeight() / 2);
            mPath.moveTo(fromX, fromY);
            mPath.quadTo(toX - ScreenTool.dip2px(32), toY, toX, toY);

        } else {
            fromX = currentView.getX() + currentInfo.getX() + currentInfo.getWidth() / 2;
            fromY = currentLl.getTop() + currentInfo.getBottom();

            toX = toView.getX() + toViewInfo.getX() + toViewInfo.getWidth() / 2;
            toY = nextLl.getTop() + toViewInfo.getY();
            mPath.moveTo(fromX, fromY);
            mPath.quadTo(toX, toY - ScreenTool.dip2px(32), toX, toY);
        }

        canvas.drawPath(mPath, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTypeMove == TYPE_IS_CAN_MOVE_N) {
            return super.onTouchEvent(event);
        } else {
            int limitW = getMeasuredWidth() / 3;
            int limitH = getMeasuredHeight() / 3;

            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_MOVE:
//                if (isTwoTouch) {
//                    float endDis = distance(event);
//                    float scale = endDis / startDis;
//                    setScale(scale);
//                } else {
                    if (mLastTouchX != 0 && mLastTouchY != 0) {
                        final int currentTouchX = (int) event.getX();
                        final int currentTouchY = (int) event.getY();
                        final int distanceX = currentTouchX - mLastTouchX;
                        final int distanceY = currentTouchY - mLastTouchY;
                        mCurrentX -= distanceX;
                        mCurrentY -= distanceY;
                        if (mTypeMove == TYPE_IS_CAN_MOVE_H) {
                            if (mCurrentX > 0 && mCurrentX < limitW) {
                                this.scrollTo(mCurrentX, 0);
                            }

                        } else if (mTypeMove == TYPE_IS_CAN_MOVE_V) {
                            if (mCurrentY > 0 && mCurrentY < limitH) {
                                this.scrollTo(0, mCurrentY);
                            }

                        }
                        mLastTouchX = currentTouchX;
                        mLastTouchY = currentTouchY;
                    }
//                }

                    break;
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP:
                    isTwoTouch = false;
                    break;
            }
            return true;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (mTypeMove == TYPE_IS_CAN_MOVE_N) {
            return false;
        } else {
            boolean intercept = false;
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mCurrentX = getScrollX();
                    mCurrentY = getScrollY();
                    mLastTouchX = (int) event.getX();
                    mLastTouchY = (int) event.getY();
                    mLastInterceptX = (int) event.getX();
                    mLastInterceptY = (int) event.getY();
                    intercept = false;
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    startDis = distance(event);
                    isTwoTouch = true;
                    mLastTouchX = 0;
                    mLastTouchY = 0;
                    intercept = true;
                    break;

                case MotionEvent.ACTION_MOVE:
                    final int distanceX = Math.abs((int) event.getX() - mLastInterceptX);
                    final int distanceY = Math.abs((int) event.getY() - mLastInterceptY);
                    if (distanceX < mScrollWidth && distanceY < mScrollWidth) {
                        intercept = false;
                    } else {
                        intercept = true;
                    }

                    break;
            }
            return intercept;
        }
    }


    // 计算两个触摸点之间的距离
    private float distance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);

        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    // 设置自己放大缩小
    private void setScale(float scale) {

        float modifyScale = Math.abs(scale - LEAST_SCALE);
        if (scale < LEAST_SCALE) {
            mCurrentScale -= modifyScale;
        } else {
            mCurrentScale += modifyScale;
        }

        if (mCurrentScale > MAX_SCALE) {
            mCurrentScale = MAX_SCALE;
        } else if (mCurrentScale < LEAST_SCALE) {
            mCurrentScale = LEAST_SCALE;
        }

        setScaleX(mCurrentScale);
        setScaleY(mCurrentScale);
    }

    public void setCountOfGeneration(int countOfGeneration) {
        this.countOfGeneration = countOfGeneration;
    }

    public void setMaxOfGeneration(int maxOfGeneration) {
        this.maxOfGeneration = maxOfGeneration;
    }

    public void setmLineWidthDp(int mLineWidthDp) {
        this.mLineWidthDp = mLineWidthDp;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public void setHaveCurrentGeneration(boolean haveCurrentGeneration) {
        isHaveCurrentGeneration = haveCurrentGeneration;
    }

    public void setmLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }

    public void setTypeMove(int typeMove) {
        mTypeMove = typeMove;
    }

    public void setShowLine(boolean showLine) {
        isShowLine = showLine;
    }

    public int getStartGeneration() {
        return startGeneration;
    }

    public static boolean isMale(int y) {
        return (y + 1) % 2 == 1;
    }

    /**
     * @param generation 代数真实位置
     * @param whichOne
     * @return
     */

    public FamilyMember getMemberView(int generation, int whichOne) {
        LinearLayout linearLayout = generationLinearLayouts.get(generation - startGeneration);
        FamilyMember memberView = (FamilyMember) linearLayout.getChildAt(whichOne);
        return memberView;
    }

    public void setData(PigeonEntity entity, int x, int y) {
        getMemberView(x, y).bindData(entity);

        if (!isShowInfoModel) {
            List<FamilyMember> memberViews = getParents(x, y);
            for (FamilyMember memberView : memberViews) {
                memberView.setCanAdd();
            }
        }
    }

    public void setData(BloodBookEntity entity) {
        List<List<PigeonEntity>> data = Lists.newArrayList();
        data.add(getData(entity.getOne()));
        data.add(getData(entity.getTwo()));
        data.add(getData(entity.getThree()));
        data.add(getData(entity.getFour()));

        for (int i = 0, len = generationLinearLayouts.size(); i < len; i++) {
            int dataPosition = i + startGeneration;
            LinearLayout linearLayout = generationLinearLayouts.get(i);
            List<PigeonEntity> breedPigeonEntities = data.get(dataPosition);
            if (Lists.isEmpty(breedPigeonEntities)) {
                return;
            }
            for (int generationOrder = 0, generationLen = linearLayout.getChildCount(); generationOrder < generationLen; generationOrder++) {
                try {
                    PigeonEntity breedPigeonEntity = breedPigeonEntities.get(generationOrder);
                    if (!breedPigeonEntity.isEmpty()) {
                        setData(breedPigeonEntity, dataPosition, generationOrder);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<PigeonEntity> getData(List<PigeonEntity> data) {
        return Lists.isEmpty(data) ? Lists.newArrayList() : data;
    }

    public List<FamilyMember> getParents(int x, int y) {

        int position = x - startGeneration;

        List<FamilyMember> list = Lists.newArrayList();

        if (generationLinearLayouts.size() == position + 1) {
            return list;
        }

        LinearLayout nextLinearLayout = generationLinearLayouts.get(position + 1);

        int fatherPosition = (y * 2);
        int motherPosition = fatherPosition + 1;
        FamilyMember mother = (FamilyMember) nextLinearLayout.getChildAt(motherPosition);
        FamilyMember father = (FamilyMember) nextLinearLayout.getChildAt(fatherPosition);

        list.add(father);
        list.add(mother);

        return list;
    }

    public FamilyMember getSon(int x, int y) {
        if (x == startGeneration) return null;
        return getMemberView(x - 1, y / 2);
    }

    public Point getCurrentPoint() {
        return mCurrentPoint;
    }

    public interface OnFamilyClickListener {
        void add(int x, int y);

        void showInfo(PigeonEntity entity);
    }

    public OnFamilyClickListener mOnFamilyClickListener;

    public void setOnFamilyClickListener(OnFamilyClickListener onFamilyClickListener) {
        mOnFamilyClickListener = onFamilyClickListener;
    }
}
