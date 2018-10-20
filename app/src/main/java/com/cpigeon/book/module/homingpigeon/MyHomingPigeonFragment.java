package com.cpigeon.book.module.homingpigeon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

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

        SearchFragmentParentActivity.
                start(activity, MyHomingPigeonFragment.class, true, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();

        setStartSearchActvity(SearchMyHomingActivity.class);//搜索页面

        mAdapter = new MyHomingPigeonAdapter();
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
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mBreedPigeonListModel.mLivePigeonSexCount.observe(this, pigeonSexCountEntity -> {

        });
    }

}