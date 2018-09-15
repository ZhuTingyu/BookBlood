package com.cpigeon.book.module.breeding;

import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.SearchBreedPigeonActivity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;

/**
 * Created by Administrator on 2018/9/15 0015.
 */

public class SearchBreedingFootActivity extends SearchBreedPigeonActivity {

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new BreedPigeonListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            BreedPigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            PairingInfoListFragment.start(getBaseActivity(), mBreedPigeonEntity);
        });
        return mAdapter;
    }

}
