package com.cpigeon.book.module.findblood;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/11/22.
 */

public class FindBloodHeadAdapter extends BaseQuickAdapter<BloodFindRecordEntity, BaseViewHolder> {

    private int mMarginT;
    private int mSize;
    private int mCSelectPosition = 0;

    public FindBloodHeadAdapter() {
        super(R.layout.item_find_blood, null);
        mMarginT = ScreenTool.dip2px(20);
        mSize = ScreenTool.getScreenWidth() - ScreenTool.dip2px(80);

    }

    @Override
    protected void convert(BaseViewHolder helper, BloodFindRecordEntity item) {

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(mSize, ViewGroup.LayoutParams.MATCH_PARENT);
        if (getData().size() == 1) {
            layoutParams.setMargins(mMarginT, 0, 0, 0);
        } else {
            if (helper.getAdapterPosition() - getHeaderLayoutCount() == 0) {
                layoutParams.setMargins(mMarginT, 0, 0, 0);
            } else if (helper.getAdapterPosition() - getHeaderLayoutCount() == getData().size() - 1) {
                layoutParams.setMargins(0, 0, mMarginT, 0);
            } else {
                layoutParams.setMargins(0, 0, 0, 0);
            }
        }

        helper.itemView.setLayoutParams(layoutParams);

        helper.setGlideImageView(getBaseActivity(), R.id.imgHead, item.getHeadUrl());
        FrameLayout mFlCircle;
        TextView mTvCount;
        View mVBLine;

        mFlCircle = helper.getView(R.id.flCircle);
        mTvCount = helper.getView(R.id.tvCount);
        mVBLine = helper.getView(R.id.vBLine);

        if(helper.getAdapterPosition() == 0){
            mTvCount.setText(item.getCount());
        }else {
            mTvCount.setText(Utils.getString(R.string.text_pigeon_generation, String.valueOf(item.getCount())));
        }

        if(item.isSelect()){
            mFlCircle.setVisibility(View.VISIBLE);
            mVBLine.setVisibility(View.VISIBLE);
        }else {
            mFlCircle.setVisibility(View.GONE);
            mVBLine.setVisibility(View.GONE);
        }
    }

}


