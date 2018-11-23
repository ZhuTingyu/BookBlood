package com.cpigeon.book.widget.family;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.glide.GlideUtil;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.widget.ShadowRelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zhu TingYu on 2018/6/21.
 */

public class FamilyMemberView extends FamilyMember {

    RelativeLayout rlMemberInfo;
    ShadowRelativeLayout rlShadow;
    ImageView imgAdd;
    private LinearLayout mLlBlood;
    private LinearLayout mLlName;

    private RelativeLayout mRlInMemberInfo;
    private CircleImageView mImgHead;
    private TextView mTvFootNumber;
    private TextView mTvBlood;
    private TextView mTvName;
    private View mVLeftLine;
    private RelativeLayout mRlRightLine;

    FamilyTreeView mFamilyTreeView;
    int generationPoint;
    int generationsOrder;
    int rootW;
    int rootH;
    int imgSize;
    int shadowColor;
    boolean isMiniModel;
    boolean isHorizontal;
    boolean isShowInfoModel;

    public FamilyMemberView(Context context, FamilyTreeView familyTreeView, int generationPoint
            , int generationsOrder, boolean isMiniModel, boolean isHorizontal, boolean isShowInfoModel) {
        super(context);
        this.generationPoint = generationPoint;
        this.generationsOrder = generationsOrder;
        this.isMiniModel = isMiniModel;
        this.isHorizontal = isHorizontal;
        this.mFamilyTreeView = familyTreeView;
        this.isShowInfoModel = isShowInfoModel;
        initView(context);
    }

    public FamilyMemberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FamilyMemberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        if(isHorizontal){
            View view = LayoutInflater.from(context).inflate(R.layout.view_family_member_layout, this);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.view_family_member_v_layout, this);
        }

        int size_80 = ScreenTool.dip2px(80);
        int size_100 = ScreenTool.dip2px(100);
        int size_129 = ScreenTool.dip2px(129);
        int size_40 = ScreenTool.dip2px(40);
        int size_28 = ScreenTool.dip2px(28);
        int size_50 = ScreenTool.dip2px(50);
        int size_31 = ScreenTool.dip2px(31);
        int shadowSize = ScreenTool.dip2px(10);

        rlShadow = findViewById(R.id.rlShadow);
        rlMemberInfo = findViewById(R.id.rlMemberInfo);
        imgAdd = findViewById(R.id.imgAdd);
        mLlBlood = findViewById(R.id.llBlood);
        mLlName = findViewById(R.id.llName);

        mRlInMemberInfo = findViewById(R.id.rlInMemberInfo);
        mImgHead = findViewById(R.id.imgHead);
        mTvFootNumber = findViewById(R.id.tvFootNumber);
        mTvBlood = findViewById(R.id.tvBlood);
        mTvName = findViewById(R.id.tvName);

        mVLeftLine = findViewById(R.id.vLeftLine);
        mRlRightLine = findViewById(R.id.rlRightLine);

        if (generationPoint == 0) {
            rlMemberInfo.setBackgroundResource(R.drawable.shape_bg_family_member_black);
        } else {
            if (generationsOrder % 2 == 0) {
                rlMemberInfo.setBackgroundResource(R.drawable.shape_bg_family_member_blue);
            } else {
                rlMemberInfo.setBackgroundResource(R.drawable.shape_bg_family_member_red);
            }
        }

        if (generationPoint == 0) {
            if (isMiniModel) {
                rootW = size_80;
                rootH = size_80;
                imgSize = size_40;
            } else {
                rootW = size_100;
                rootH = size_129;
                imgSize = size_50;
            }
            shadowColor = R.color.color_text_hint;
        } else if (generationPoint == 1) {
            if (isMiniModel) {
                rootW = size_100;
                rootH = size_80;
                imgSize = size_40;
            } else {
                rootW = size_100;
                rootH = size_129;
                imgSize = size_50;
            }
            shadowColor = getShadowColor();
        } else if (generationPoint == 2) {
            if (isMiniModel) {
                rootW = size_80;
                rootH = size_40;
                imgSize = size_40;
            } else {
                rootW = size_100;
                rootH = size_80;
                imgSize = size_50;
            }
            shadowColor = getShadowColor();
        } else if (generationPoint == 3) {

            if (isMiniModel) {
                rootW = size_80;
                rootH = size_28;
                imgSize = size_40;
            } else {
                rootW = size_100;
                rootH = size_40;
                imgSize = size_31;
            }
            shadowColor = getShadowColor();
        }
        rlShadow.addShadow(shadowColor);

        LayoutParams shadowP;
        RelativeLayout.LayoutParams infoP;
        RelativeLayout.LayoutParams imgP = new RelativeLayout.LayoutParams(imgSize, imgSize);

        shadowP = new LayoutParams(rootW + shadowSize, rootH + shadowSize);
        infoP = new RelativeLayout.LayoutParams(rootW, rootH);
        if (isHorizontal) {
            shadowP = new LayoutParams(rootW + shadowSize, rootH + shadowSize);
            infoP = new RelativeLayout.LayoutParams(rootW, rootH);
        } else {
            shadowP = new LayoutParams(rootH + shadowSize, rootW + shadowSize);
            infoP = new RelativeLayout.LayoutParams(rootH, rootW);
        }
        imgP.addRule(RelativeLayout.CENTER_IN_PARENT);
        rlMemberInfo.setLayoutParams(infoP);
        imgAdd.setLayoutParams(imgP);
        rlShadow.setLayoutParams(shadowP);

        if (generationPoint == mFamilyTreeView.getStartGeneration()) {
            mVLeftLine.setVisibility(GONE);
        }

        if (generationPoint == mFamilyTreeView.getMaxOfGeneration() - 1) {
            mRlRightLine.setVisibility(GONE);
        }

    }

    public void bindData(PigeonEntity entity) {

        if (!StringUtil.isStringValid(entity.getPigeonID())) {
            if(!isShowInfoModel){
                imgAdd.setVisibility(VISIBLE);
            }
            mRlInMemberInfo.setVisibility(GONE);
            return;
        }

        mPigeonEntity = entity;
        imgAdd.setVisibility(GONE);
        mRlInMemberInfo.setVisibility(VISIBLE);
        GlideUtil.setGlideImageView(getContext(), entity.getCoverPhotoUrl(), mImgHead);
        mTvFootNumber.setText(entity.getFootRingNum());

        if (generationPoint == 0) {
            if (isMiniModel) {
                mLlBlood.setVisibility(GONE);
                mLlName.setVisibility(GONE);
            }else {
                bindInData(entity);
            }
        } else if (generationPoint == 1) {

            if (isMiniModel) {
                mLlBlood.setVisibility(GONE);
                mLlName.setVisibility(GONE);
            }else {
                bindInData(entity);
            }
        } else if (generationPoint == 2) {
            if (isMiniModel) {
                mImgHead.setVisibility(GONE);
                mLlBlood.setVisibility(GONE);
                mLlName.setVisibility(GONE);
            } else {
                if(!isHorizontal){
                    mImgHead.setVisibility(GONE);
                    bindInData(entity);
                }else {
                    mLlBlood.setVisibility(GONE);
                    mLlName.setVisibility(GONE);
                }
            }
        } else if (generationPoint == 3) {
                mImgHead.setVisibility(GONE);
                mLlBlood.setVisibility(GONE);
                mLlName.setVisibility(GONE);
        }

    }

    private void bindInData(PigeonEntity entity){
        setTextContent(mLlBlood, mTvBlood, entity.getPigeonBloodName());
        setTextContent(mLlName, mTvName, entity.getPigeonName());
    }


    @ColorRes
    public int getShadowColor() {
        if (generationsOrder % 2 == 0) {
            return R.color.color_book_male;
        } else {
            return R.color.color_book_female;
        }
    }

    @Override
    public View getInfoView() {
        return rlShadow;
    }

    public interface OnMemberClickListener {
        void add(int x, int y);

        void showInfo(int x, int y, PigeonEntity entity);
    }

    private OnMemberClickListener mOnMemberClickListener;

    public void setOnMemberClickListener(OnMemberClickListener onMemberClickListener) {
        mOnMemberClickListener = onMemberClickListener;

        imgAdd.setOnClickListener(v -> {
            if (mOnMemberClickListener != null) {
                mOnMemberClickListener.add(generationPoint, generationsOrder);
            }
        });

        mRlInMemberInfo.setOnClickListener(v -> {
            if (mOnMemberClickListener != null) {
                mOnMemberClickListener.showInfo(generationPoint, generationsOrder, mPigeonEntity);
            }
        });
    }

    public void setCanAdd() {
        imgAdd.setVisibility(VISIBLE);
    }
}
