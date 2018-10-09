
package com.cpigeon.book.module.play;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.play.adapter.PlayFootListAdapter;
import com.cpigeon.book.module.racing.RacingPigeonEntryFragment;



/**
 * 赛鸽管理   足环列表
 * Created by Administrator on 2018/9/10.
 */

public class PlayFootListFragment extends BaseFootListFragment {


    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, PigeonEntity.ID_MATCH_PIGEON);
        SearchFragmentParentActivity.
                start(activity, PlayFootListFragment.class, true, bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecorationLine();

    }

    @Override
    protected void initData() {
        super.initData();

        mTvOk.setText(R.string.text_add_play_pigeon);
        mTvOk.setOnClickListener(v -> {
            //赛鸽录入
            RacingPigeonEntryFragment.start(getBaseActivity());
        });

        mAdapter = new PlayFootListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            BreedPigeonDetailsFragment.start(getBaseActivity(),
                    mBreedPigeonEntity.getPigeonID(),
                    mBreedPigeonEntity.getFootRingID());
        });

    }


    @Override
    protected void initObserve() {
        super.initObserve();
        mBreedPigeonListModel.mLivePigeonSexCount.observe(this, pigeonSexCountEntity -> {

        });

    }
}