package com.cpigeon.book.module.home.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.google.android.exoplayer2.C;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/10.
 */

public class HomeTopAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeTopAdapter() {
        super(R.layout.item_home_top, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        View view = helper.itemView;
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ScreenTool.dip2px(320)
                ,ScreenTool.dip2px(156));
        if(helper.getAdapterPosition() == 0){
            layoutParams.setMargins(ScreenTool.dip2px(20), 0,ScreenTool.dip2px(6)
                    ,ScreenTool.dip2px(8));
            view.setBackgroundResource(R.mipmap.ic_home_top_bg_blue);
        }else {
            layoutParams.setMargins(ScreenTool.dip2px(6), 0,ScreenTool.dip2px(20)
                    ,ScreenTool.dip2px(8));
            view.setBackgroundResource(R.mipmap.ic_home_top_bg_yellow);
        }
        view.setLayoutParams(layoutParams);
    }
}
