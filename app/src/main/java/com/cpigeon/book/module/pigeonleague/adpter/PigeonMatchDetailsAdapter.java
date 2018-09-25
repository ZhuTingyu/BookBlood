package com.cpigeon.book.module.pigeonleague.adpter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.base.widget.MarqueeTextView;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.LeagueDetailsEntity;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/14.
 */

public class PigeonMatchDetailsAdapter extends BaseQuickAdapter<LeagueDetailsEntity, BaseViewHolder> {

    public PigeonMatchDetailsAdapter() {
        super(R.layout.item_piegon_match_details, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, LeagueDetailsEntity item) {
        helper.setText(R.id.tvOrder, String.valueOf(helper.getAdapterPosition()));
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

        new CrazyShadow.Builder()
                .setContext(mContext)
                .setBaseShadowColor(Utils.getColor(R.color.gray))
                .setDirection(CrazyShadowDirection.RIGHT_BOTTOM_LEFT)
                .setShadowRadius(ScreenTool.dip2px(5))
                .setCorner(ScreenTool.dip2px(0))
                .setBackground(Utils.getColor(R.color.white))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(helper.getView(R.id.rlContent));

        mTvRank.setText(item.getMatchNumber());
        mTvDistance.setText(item.getMatchInterval());
        mTvCurrentSpeed.setText(Utils.getString(R.string.text_speed_content, "0.00"));
        mTvFirstSpeed.setText(Utils.getString(R.string.text_speed_content, "0.00"));
        mTvTime.setText(item.getMatchTime());
        mTvProjectName.setText(item.getMatchName());
    }
}
