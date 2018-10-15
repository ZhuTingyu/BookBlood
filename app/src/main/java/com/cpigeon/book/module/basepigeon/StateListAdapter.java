package com.cpigeon.book.module.basepigeon;

import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.widget.stats.StatView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/25.
 */

public class StateListAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    int mRootW;
    int mCircleW;
    List<String> mTitles;
    int mMaxCount;
    String mUnitString;

    public StateListAdapter(List<String> titles) {
        super(R.layout.item_stat, null);
        mRootW = (int) ((ScreenTool.getScreenWidth() - ScreenTool.dip2px(80)) / 4.5f);
        mCircleW = mRootW;
        this.mTitles = titles;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        StatView statView = (StatView) helper.itemView;
        statView.setStatW(mCircleW);
        statView.setTitle(mTitles.get(helper.getAdapterPosition()));
        statView.setColor(getDataColor(helper.getAdapterPosition()), getOtherColor(helper.getAdapterPosition()));
        statView.setUnitString(mUnitString);
        statView.bindData(item, mMaxCount);
    }

    public void setUnitString(String unitString) {
        mUnitString = unitString;
    }

    @ColorRes
    public int getDataColor(int position){
        if(position % 2 == 0){
            return R.color.color_scale_blue_data;
        }else {
            return R.color.color_scale_red_data;
        }
    }

    @ColorRes
    public int getOtherColor(int position){
        if(position % 2 == 0){
            return R.color.color_scale_blue_other;
        }else {
            return R.color.color_scale_red_other;
        }
    }

    public void setMaxCount(int maxCount) {
        this.mMaxCount = maxCount;
    }
}
