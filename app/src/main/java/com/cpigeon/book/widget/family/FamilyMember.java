package com.cpigeon.book.widget.family;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * Created by Zhu TingYu on 2018/9/10.
 */

public abstract class FamilyMember extends LinearLayout{

    protected PigeonEntity mPigeonEntity;

    public FamilyMember(Context context) {
        super(context);
    }

    public FamilyMember(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FamilyMember(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   public abstract View getInfoView();
   public abstract void setCanAdd();
   public abstract void bindData(PigeonEntity entity);
   public PigeonEntity getData(){
       return mPigeonEntity;
   }
}
