package com.cpigeon.book.module.feedpigeon;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breeding.PairingInfoListFragment;
import com.cpigeon.book.module.breedpigeon.SearchBreedPigeonActivity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.feedpigeon.adapter.FeedPigeonRecordListAdapter;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class SearchFeedPigeonRecordActivity extends SearchBreedPigeonActivity {

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new FeedPigeonRecordListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            FeedPigeonDetailsFragment.start(getBaseActivity(), mBreedPigeonEntity);

        });
        return mAdapter;
    }
}
