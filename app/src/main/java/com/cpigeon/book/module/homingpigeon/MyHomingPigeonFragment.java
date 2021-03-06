package com.cpigeon.book.module.homingpigeon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;

/**
 * 我的信鸽
 * Created by Administrator on 2018/10/8 0008.
 */

public class MyHomingPigeonFragment extends BaseFootListFragment  {


    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(BaseFootListFragment.STATEID, PigeonEntity.IN_THE_SHED);
        SearchFragmentParentActivity.
                start(activity, MyHomingPigeonFragment.class, true, bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        }

    @Override
    protected void initData() {
        super.initData();
        setTitle("我的信鸽");
        setStartSearchActvity(SearchMyHomingActivity.class);//搜索页面
        mAdapter = new MyHomingPigeonAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {

                mBreedPigeonListModel.id = PigeonId;
                setProgressVisible(true);
                mBreedPigeonListModel.deletePigeon();
            }
        }, new LinearLayoutListener() {
            @Override
            public void click(int position) {
                try {
                    PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                    BreedPigeonDetailsFragment.start(getBaseActivity(),
                            mBreedPigeonEntity.getPigeonID(),
                            mBreedPigeonEntity.getFootRingID());
                } catch (Exception e) {

                    e.printStackTrace();
                }
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