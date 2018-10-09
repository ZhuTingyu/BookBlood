package com.cpigeon.book.module.homingpigeon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;

/**
 * 我的信鸽
 * Created by Administrator on 2018/10/8 0008.
 */

public class MyHomingPigeonFragment extends BaseFootListFragment {

    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, "");
        SearchFragmentParentActivity.
                start(activity, MyHomingPigeonFragment.class, true, bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecorationLine();

    }

    @Override
    protected void initData() {
        super.initData();

        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        mAdapter = new MyHomingPigeonAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            BreedPigeonDetailsFragment.start(getBaseActivity(),
                    mBreedPigeonEntity.getPigeonID(),
                    mBreedPigeonEntity.getFootRingID());
        });

        mActivity.setSearchClickListener(v -> {
            //搜索
            Bundle bundle = new Bundle();
            bundle.putString(IntentBuilder.KEY_TYPE, mBreedPigeonListModel.typeid);
            BaseSearchActivity.start(getBaseActivity(), SearchMyHomingActivity.class, bundle);
        });

    }


    @Override
    protected void initObserve() {
        super.initObserve();

        mBreedPigeonListModel.mLivePigeonSexCount.observe(this, pigeonSexCountEntity -> {

        });

    }
}