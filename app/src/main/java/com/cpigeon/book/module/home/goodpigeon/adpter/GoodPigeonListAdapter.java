package com.cpigeon.book.module.home.goodpigeon.adpter;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/9/14.
 */

public class GoodPigeonListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    int margin_5;
    int margin_10;
    int margin_30;

    public GoodPigeonListAdapter() {
        super(R.layout.item_good_pigeon_list, null);
        margin_5 = ScreenTool.dip2px(5);
        margin_10 = ScreenTool.dip2px(10);
        margin_30 = ScreenTool.dip2px(30);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        int i = (int) (Math.random() * 10);
        int h = 0;
        int imgH = 0;
        int margin = 0;
        if (i < 5) {
            h = ScreenTool.dip2px(288);
            imgH = ScreenTool.dip2px(155);
            margin = margin_30;
        } else {
            h = ScreenTool.dip2px(216);
            imgH = ScreenTool.dip2px(124);
            margin = margin_10;
        }
        ImageView img = helper.getView(R.id.img);
        RelativeLayout.LayoutParams imgP = new RelativeLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, imgH);
        imgP.setMargins(margin_10, margin, margin_10, margin);
        img.setLayoutParams(imgP);

        RecyclerView.LayoutParams itemP = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
        itemP.setMargins(margin_5, margin_5, margin_5, margin_5);
        helper.itemView.setLayoutParams(itemP);
    }
}
