package com.cpigeon.book.module.home.home.adapter;

import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.HomeTopEntity;

/**
 * Created by Zhu TingYu on 2018/8/10.
 */

public class HomeTopAdapter extends BaseQuickAdapter<HomeTopEntity, BaseViewHolder> {

    int width;
    int height;

    public HomeTopAdapter() {
        super(R.layout.item_home_top, Lists.newArrayList());
        width = ScreenTool.getScreenWidth() - ScreenTool.dip2px(40);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeTopEntity item) {
        View view = helper.itemView;
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (helper.getAdapterPosition() == 0) {
            layoutParams.setMargins(ScreenTool.dip2px(20), 0, ScreenTool.dip2px(6)
                    , ScreenTool.dip2px(6));
            view.setBackgroundResource(R.mipmap.ic_home_top_blue);
        } else {
            layoutParams.setMargins(ScreenTool.dip2px(20), 0, ScreenTool.dip2px(20)
                    , ScreenTool.dip2px(6));
            view.setBackgroundResource(R.mipmap.ic_home_top_yellow);
        }
        view.setLayoutParams(layoutParams);
        helper.setText(R.id.tvTitle, item.CountName);
        helper.setText(R.id.tvLeftTitle, item.LeftName);
        helper.setText(R.id.tvRightTitle, item.RightName);


        TextView tVCount = helper.getView(R.id.tVCount);
        tVCount.setText(item.Count);
        tVCount.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "SF-PRO-TEXT_REGULAR.TTF"));

        TextView tvLeftCount = helper.getView(R.id.tvLeftCount);
        tvLeftCount.setText(item.Left);
        tvLeftCount.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "SF-PRO-TEXT_REGULAR.TTF"));

        TextView tvRightCount = helper.getView(R.id.tvRightCount);
        tvRightCount.setText(item.Right);
        tvRightCount.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "SF-PRO-TEXT_REGULAR.TTF"));
    }
}
