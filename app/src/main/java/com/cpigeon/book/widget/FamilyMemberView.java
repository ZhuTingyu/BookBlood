package com.cpigeon.book.widget;

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
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.BreedPigeonEntity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zhu TingYu on 2018/6/21.
 */

public class FamilyMemberView extends LinearLayout {

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

    int generationPoint;
    int generationsOrder;
    int rootW;
    int rootH;
    int imgSize;
    int shadowColor;
    boolean isMiniModel;
    private BreedPigeonEntity mPigeonEntity;

    public FamilyMemberView(Context context, int generationPoint, int generationsOrder, boolean isMiniModel) {
        super(context);
        this.generationPoint = generationPoint;
        this.generationsOrder = generationsOrder;
        this.isMiniModel = isMiniModel;
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
        View view = LayoutInflater.from(context).inflate(R.layout.view_family_member_layout, this);

        int size_80 = ScreenTool.dip2px(80);
        int size_129 = ScreenTool.dip2px(129);
        int size_40 = ScreenTool.dip2px(40);
        int size_20 = ScreenTool.dip2px(20);
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

        if(generationPoint == 0){
            rlMemberInfo.setBackgroundResource(R.drawable.shape_bg_family_member_black);
        }else {
            if (generationsOrder % 2 == 0) {
                rlMemberInfo.setBackgroundResource(R.drawable.shape_bg_family_member_blue);
            } else {
                rlMemberInfo.setBackgroundResource(R.drawable.shape_bg_family_member_red);
            }
        }

        if (generationPoint == 0) {
            imgAdd.setVisibility(VISIBLE);
            if (isMiniModel) {
                rootW = size_80;
                rootH = size_80;
                imgSize = size_40;
            } else {
                rootW = size_80;
                rootH = size_129;
                imgSize = size_50;
            }
            shadowColor = R.color.color_text_hint;
        } else if (generationPoint == 1) {

            if (isMiniModel) {
                rootW = size_80;
                rootH = size_80;
                imgSize = size_40;
            } else {
                rootW = size_80;
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
                rootW = size_80;
                rootH = size_80;
                imgSize = size_50;
            }
            shadowColor = getShadowColor();
        } else if (generationPoint == 3) {

            if (isMiniModel) {
                rootW = size_80;
                rootH = size_20;
                imgSize = size_40;
            } else {
                rootW = size_80;
                rootH = size_40;
                imgSize = size_31;
            }
            shadowColor = getShadowColor();
        }
        rlShadow.addShadow(shadowColor);

        LayoutParams shadowP = new LayoutParams(rootW + shadowSize, rootH + shadowSize);
        RelativeLayout.LayoutParams infoP = new RelativeLayout.LayoutParams(rootW, rootH);
        RelativeLayout.LayoutParams imgP = new RelativeLayout.LayoutParams(imgSize, imgSize);
        imgP.addRule(RelativeLayout.CENTER_IN_PARENT);
        rlMemberInfo.setLayoutParams(infoP);
        imgAdd.setLayoutParams(imgP);
        rlShadow.setLayoutParams(shadowP);

    }

    public void bindData(BreedPigeonEntity entity) {
        mPigeonEntity = entity;
        imgAdd.setVisibility(GONE);
        mRlInMemberInfo.setVisibility(VISIBLE);
        GlideUtil.setGlideImageView(getContext(), UserModel.getInstance().getUserData().touxiangurl, mImgHead);
        mTvFootNumber.setText("2018-22-1234555");
        mTvBlood.setText("詹森");
        mTvName.setText("神奇小鸟");

        if (generationPoint == 0) {
            if (isMiniModel) {
                mLlBlood.setVisibility(GONE);
                mLlName.setVisibility(GONE);
            } else {

            }
        } else if (generationPoint == 1) {

            if (isMiniModel) {
                mLlBlood.setVisibility(GONE);
                mLlName.setVisibility(GONE);
            } else {

            }
        } else if (generationPoint == 2) {
            if (isMiniModel) {
                mImgHead.setVisibility(GONE);
                mLlBlood.setVisibility(GONE);
                mLlName.setVisibility(GONE);
            } else {
                mLlBlood.setVisibility(GONE);
                mLlName.setVisibility(GONE);
            }
        } else if (generationPoint == 3) {
            if (isMiniModel) {
                mImgHead.setVisibility(GONE);
                mLlBlood.setVisibility(GONE);
                mLlName.setVisibility(GONE);
            } else {
                mImgHead.setVisibility(GONE);
                mLlBlood.setVisibility(GONE);
                mLlName.setVisibility(GONE);
            }
        }
    }

    public RelativeLayout getRlMemberInfo() {
        return rlShadow;
    }

    @ColorRes
    public int getShadowColor() {
        if (generationsOrder % 2 == 0) {
            return R.color.color_book_male;
        } else {
            return R.color.color_book_female;
        }
    }

    public interface OnMemberClickListener {
        void add(int x, int y);

        void showInfo(BreedPigeonEntity entity);
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
                mOnMemberClickListener.showInfo(mPigeonEntity);
            }
        });
    }

    public void setCanAdd() {
        imgAdd.setVisibility(VISIBLE);
    }
}
