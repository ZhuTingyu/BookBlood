package com.cpigeon.book.module.breedpigeon.adpter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.module.photo.adpter.ImageItemDecoration;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class GrowthReportAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private static final int TAG_IS_ADD_DECORATION = 0;

    ImageItemDecoration mItemDecoration;

    public GrowthReportAdapter() {
        super(R.layout.item_growth_report, Lists.newArrayList());
        mItemDecoration = new ImageItemDecoration(ScreenTool.dip2px(5), 3);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView icon = helper.getView(R.id.imgIcon);
        helper.setText(R.id.tvDay, "30");
        helper.setText(R.id.tvYear, "2018-22");
        helper.setText(R.id.tvTitle, "用药");
        helper.setText(R.id.tv1, "第一窝    晴    27℃   东南风");
        helper.setText(R.id.tv2, "东南风");
        icon.setImageResource(R.mipmap.ic_report_auction);

        RecyclerView list = helper.getView(R.id.list);

        if(list.getTag() == null){
            list.addItemDecoration(mItemDecoration);
            list.setTag(true);
        }

        list.setLayoutManager(new GridLayoutManager(mContext, 4));
        GrowthReportImageAdapter adapter = (GrowthReportImageAdapter) list.getAdapter();
        if(adapter == null){
            adapter = new GrowthReportImageAdapter(list);
            list.setAdapter(adapter);
        }

        adapter.setNewData(Lists.newArrayList("","","",""));
        list.setFocusableInTouchMode(false);

        if(getData().size() == 1){
            helper.getView(R.id.rlArrow).setVisibility(View.GONE);
        }else if(getData().size() >= 2){
            if(helper.getAdapterPosition() == getData().size() - 1){
                helper.getView(R.id.rlArrow).setVisibility(View.GONE);
            }else {
                helper.getView(R.id.rlArrow).setVisibility(View.VISIBLE);
            }
        }

    }
}
