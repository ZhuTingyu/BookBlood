package com.cpigeon.book.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FamilyMemberEntity;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zhu TingYu on 2018/6/21.
 */

public class FamilyMemberView extends LinearLayout {

    RelativeLayout rlMemberInfo;
    ShadowRelativeLayout rlShadow;
    ImageView imgAdd;

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

    public FamilyMemberView(Context context, int generationPoint, int generationsOrder) {
        super(context);
        this.generationPoint = generationPoint;
        this.generationsOrder = generationsOrder;
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
        int size_50 = ScreenTool.dip2px(50);
        int size_31 = ScreenTool.dip2px(31);
        int shadowSize = ScreenTool.dip2px(10);

        rlShadow = findViewById(R.id.rlShadow);
        rlMemberInfo = view.findViewById(R.id.rlMemberInfo);
        imgAdd = view.findViewById(R.id.imgAdd);

        mRlInMemberInfo = findViewById(R.id.rlInMemberInfo);
        mImgHead = findViewById(R.id.imgHead);
        mTvFootNumber = findViewById(R.id.tvFootNumber);
        mTvBlood = findViewById(R.id.tvBlood);
        mTvName = findViewById(R.id.tvName);

        if (generationPoint == 0) {
            rootW = size_80;
            rootH = size_129;
            imgSize = size_50;
            shadowColor = R.color.color_text_hint;
        } else if (generationPoint == 1) {
            rootW = size_80;
            rootH = size_129;
            imgSize = size_50;
            shadowColor = getShadowColor();
        } else if (generationPoint == 2) {
            rootW = size_80;
            rootH = size_80;
            imgSize = size_50;
            shadowColor = getShadowColor();
        } else if (generationPoint == 3) {
            rootW = size_80;
            rootH = size_40;
            imgSize = size_31;
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

    public void bindData(FamilyMemberEntity entity) {
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
}
