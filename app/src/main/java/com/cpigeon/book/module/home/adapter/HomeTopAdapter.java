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

    int width;
    int height;
    int paddingLeft;
    int paddingBttom;

    public HomeTopAdapter() {
        super(R.layout.item_home_top, Lists.newArrayList());
        width = ScreenTool.getScreenWidth() - ScreenTool.dip2px(40);
        height = ScreenTool.dip2px(156) ;
        paddingLeft = ScreenTool.dip2px(6);
        paddingBttom = ScreenTool.dip2px(10);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        View view = helper.itemView;
        view.setPadding(paddingLeft,0, paddingLeft, paddingBttom);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, height);
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
