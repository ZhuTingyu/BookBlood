package com.cpigeon.book.module.feedpigeon;


import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.feedpigeon.adapter.FeedPigeonRecordListAdapter;

/**
 * 养鸽记录 足环搜索
 * Created by Zhu TingYu on 2018/9/7.
 */

public class SearchFeedPigeonRecordActivity extends BaseSearchPigeonActivity {

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new FeedPigeonRecordListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            FeedPigeonDetailsFragment.start(getBaseActivity(), mBreedPigeonEntity);

        });
        return mAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = "search_history_feed_pigeon_record";
    }

}
