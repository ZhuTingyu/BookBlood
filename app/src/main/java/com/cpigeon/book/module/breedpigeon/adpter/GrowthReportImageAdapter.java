package com.cpigeon.book.module.breedpigeon.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class GrowthReportImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    RecyclerView mRecyclerView;

    public GrowthReportImageAdapter(RecyclerView recyclerView) {
        super(R.layout.item_pigeon_photo_home, Lists.newArrayList());
        mRecyclerView = recyclerView;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        int rootW = (mRecyclerView.getMeasuredWidth() - ScreenTool.dip2px(20)) / 4;
        RelativeLayout rootR = helper.getView(R.id.rlRoot);
        RecyclerView.LayoutParams rootP = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rootW);
        rootR.setLayoutParams(rootP);
        helper.setGlideImageView(mContext, R.id.img, UserModel.getInstance().getUserData().touxiangurl);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
