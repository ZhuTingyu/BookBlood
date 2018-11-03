package com.cpigeon.book.module.breeding.viewmodel;

import android.app.Activity;
import android.os.Bundle;

import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breeding.PairingInfoAddFragment;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.cpigeon.book.module.homingpigeon.SearchMyHomingActivity;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;

/**
 * Created by Administrator on 2018/11/3 0003.
 */

public class AddBreedingFragment extends BaseFootListFragment{
    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(BaseFootListFragment.STATEID, PigeonEntity.ID_ALL);
        SearchFragmentParentActivity.
                start(activity, AddBreedingFragment.class, true, bundle);
    }
    @Override
    protected void initData() {
        super.initData();
        setStartSearchActvity(SearchMyHomingActivity.class);//搜索页面
        mAdapter = new MyHomingPigeonAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                mBreedPigeonListModel.id=PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        });
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {

                PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);

                PairingInfoAddFragment.start(getBaseActivity(), mBreedPigeonEntity, null);
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
