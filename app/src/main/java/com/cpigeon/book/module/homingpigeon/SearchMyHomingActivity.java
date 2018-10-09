package com.cpigeon.book.module.homingpigeon;

import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.breeding.PairingInfoListFragment;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;


/**
 * 我的全部信鸽  搜索
 * Created by Administrator on 2018/9/15 0015.
 */

public class SearchMyHomingActivity extends BaseSearchPigeonActivity {


    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new MyHomingPigeonAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            PairingInfoListFragment.start(getBaseActivity(), mBreedPigeonEntity);
            getBaseActivity().finish();
        });
        return mAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = "search_history_my_homing";
    }
}
