package com.cpigeon.book.module.play;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.play.adapter.PlayFootListAdapter;

/**
 * Created by Administrator on 2018/10/20 0020.
 */

public class PlayFootListActivity  extends BaseSearchPigeonActivity {

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new PlayFootListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                BreedPigeonDetailsFragment.start(getBaseActivity(),
                        mBreedPigeonEntity.getPigeonID(),
                        mBreedPigeonEntity.getFootRingID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView.setListPadding(0, 0, 0, 0);
    }

    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = "search_history_play_foot_list";
    }

}
