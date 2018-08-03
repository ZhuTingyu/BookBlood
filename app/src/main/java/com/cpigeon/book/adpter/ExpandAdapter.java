package com.cpigeon.book.adpter;

import com.base.base.BaseExpandAdapter;
import com.base.base.BaseViewHolder;
import com.base.util.Lists;
import com.base.util.utility.ToastUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.test.TestExpand2Entity;
import com.cpigeon.book.test.TestExpandEntity;

/**
 * Created by Zhu TingYu on 2018/3/5.
 */

public class ExpandAdapter extends BaseExpandAdapter {


    public ExpandAdapter() {
        super(Lists.newArrayList());
        addItemType(TYPE_ORG, R.layout.item_level_1_layout);
        addItemType(TYPE_RACE, R.layout.item_level_2_layout);
    }

    @Override
    public void initRace(BaseViewHolder helper, MultiItemEntity item) {
        RaceItem raceItem = (RaceItem) item;
        TestExpand2Entity user = (TestExpand2Entity) raceItem.getRace();
        helper.setTextView(R.id.text1, user.name);
        helper.itemView.setOnClickListener(v -> {
            ToastUtils.showShort(mContext,user.name);
        });
    }

    @Override
    public void initOrg(BaseViewHolder helper, MultiItemEntity item) {
        OrgItem orgItem = (OrgItem) item;
        TestExpandEntity entity = (TestExpandEntity) orgItem.getOrgInfo();
        helper.setTextView(R.id.text1, entity.a);
    }


}
