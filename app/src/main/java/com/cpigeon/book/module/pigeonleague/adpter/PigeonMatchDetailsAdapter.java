package com.cpigeon.book.module.pigeonleague.adpter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.widget.MarqueeTextView;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/14.
 */

public class PigeonMatchDetailsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PigeonMatchDetailsAdapter() {
        super(R.layout.item_piegon_match_details, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvOrder, String.valueOf(helper.getAdapterPosition() + 1));
        TextView mTvRank;
        TextView mTvDistance;
        TextView mTvCurrentSpeed;
        TextView mTvPigeonCount;
        TextView mTvFirstSpeed;
        TextView mTvTime;
        MarqueeTextView mTvOrganizeName;
        MarqueeTextView mTvProjectName;

        mTvRank = helper.getView(R.id.tvRank);
        mTvDistance = helper.getView(R.id.tvDistance);
        mTvCurrentSpeed = helper.getView(R.id.tvCurrentSpeed);
        mTvPigeonCount = helper.getView(R.id.tvPigeonCount);
        mTvFirstSpeed = helper.getView(R.id.tvFirstSpeed);
        mTvTime = helper.getView(R.id.tvTime);
        mTvOrganizeName = helper.getView(R.id.tvOrganizeName);
        mTvProjectName = helper.getView(R.id.tvProjectName);


    }
}
