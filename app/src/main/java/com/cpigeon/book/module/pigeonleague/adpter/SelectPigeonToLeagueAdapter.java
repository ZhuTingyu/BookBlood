package com.cpigeon.book.module.pigeonleague.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.module.pigeonleague.PigeonMatchDetailsActivity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/13.
 */

public class SelectPigeonToLeagueAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SelectPigeonToLeagueAdapter() {
        super(R.layout.item_selecte_pigeon_to_league, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvFootNumber, "2018-22-123456");
        helper.setText(R.id.tvColor, "白色");
        helper.setText(R.id.tvJoinCount, "100");

        helper.itemView.setOnClickListener(v -> {
            PigeonMatchDetailsActivity.start(getBaseActivity(),"");
        });
    }
}
