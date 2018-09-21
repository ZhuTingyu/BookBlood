package com.cpigeon.book.module.makebloodbook;


import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.db.AppDatabase;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;


/**
 * Created by Zhu TingYu on 2018/9/10.
 */

public class SearchBreedPigeonToMakeBookActivity extends BaseSearchPigeonActivity {

    BreedPigeonListAdapter mAdapter;

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new BreedPigeonListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PreviewsBookFragment.start(getBaseActivity(),"");
        });
        return mAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY =  AppDatabase.TYPE_SEARCH_BREED_PIGEON;
    }
}
