package com.cpigeon.book.module.feedpigeon;


import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;

/**
 * 养鸽记录 足环搜索
 * Created by Zhu TingYu on 2018/9/7.
 */

public class SearchFeedPigeonRecordActivity extends BaseSearchPigeonActivity {

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new MyHomingPigeonAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                mBreedPigeonListModel.id = PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        }, new LinearLayoutListener() {
            @Override
            public void click(int position) {
                try {
                    PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                    FeedPigeonDetailsFragment.start(getBaseActivity(), mBreedPigeonEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = "search_history_feed_pigeon_record";
    }

}
