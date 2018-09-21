package com.cpigeon.book.module.pigeonleague.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonListAdapter;
import com.cpigeon.book.module.pigeonleague.PigeonMatchDetailsActivity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/13.
 */

public class SelectPigeonToLeagueAdapter extends BasePigeonListAdapter {

    public SelectPigeonToLeagueAdapter() {
        super(R.layout.item_selecte_pigeon_to_league, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {

        helper.setText(R.id.tvFootNumber, item.getFootRingNum());
        helper.setText(R.id.tvColor, item.getPigeonPlumeName());
        helper.setText(R.id.tvJoinCount, item.getMatchCount());
    }
}
