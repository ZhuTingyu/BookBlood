package com.cpigeon.book.adpter;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/7/25.
 */

public class HomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeAdapter() {
        super(R.layout.item_home_list, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        RelativeLayout.LayoutParams layoutParams;
        if(helper.getAdapterPosition() == 0){
            layoutParams  = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ScreenTool.getScreenHeight() / 3);
            layoutParams.setMargins(ScreenTool.dip2px(2.5f)
                    ,ScreenTool.dip2px(2.5f),ScreenTool.dip2px(2.5f),ScreenTool.dip2px(2.5f));
            helper.itemView.setLayoutParams(layoutParams);
        }else {

            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ScreenTool.getScreenHeight() / 2);
            layoutParams.setMargins(ScreenTool.dip2px(2.5f)
                    ,ScreenTool.dip2px(2.5f),ScreenTool.dip2px(2.5f),ScreenTool.dip2px(2.5f));
            helper.itemView.setLayoutParams(layoutParams);
        }
    }
}
