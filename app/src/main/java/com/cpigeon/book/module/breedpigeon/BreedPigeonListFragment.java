package com.cpigeon.book.module.breedpigeon;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonSexCountEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.homingpigeon.MyHomingPigeonFragment;
import com.cpigeon.book.widget.stats.StatView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class BreedPigeonListFragment extends BaseFootListFragment {


    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, PigeonEntity.ID_BREED_PIGEON);
        SearchFragmentParentActivity.
                start(activity, BreedPigeonListFragment.class, true, bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecorationLine();

    }

    @Override
    protected void initData() {
        super.initData();
        mTvOk.setText(R.string.text_add_breed_pigeon);
        mTvOk.setOnClickListener(v -> {
            //种鸽录入
            InputBreedInBookFragment.start(getBaseActivity());
        });

        mAdapter = new BreedPigeonListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            BreedPigeonDetailsFragment.start(getBaseActivity(),
                    mBreedPigeonEntity.getPigeonID(),
                    mBreedPigeonEntity.getFootRingID());
        });

        mBreedPigeonListModel.getPigeonCount();

    }


    @Override
    protected void initObserve() {
        super.initObserve();
        mBreedPigeonListModel.mLivePigeonSexCount.observe(this, pigeonSexCountEntity -> {
            mAdapter.addHeaderView(initHeadView(pigeonSexCountEntity));
        });

    }

    private View initHeadView(PigeonSexCountEntity countEntity) {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_stat_list_head, null);
        StatView mStat1;
        StatView mStat2;
        StatView mStat3;
        CardView cv_all_pigeon;

        mStat1 = view.findViewById(R.id.stat1);
        mStat2 = view.findViewById(R.id.stat2);
        mStat3 = view.findViewById(R.id.stat3);
        cv_all_pigeon = view.findViewById(R.id.cv_all_pigeon);

        mStat1.bindData(countEntity.ZCount
                , countEntity.ZCount);
        mStat2.bindData(countEntity.ZxiongCount, countEntity.allCount);
        mStat3.bindData(countEntity.ZciCount, countEntity.ZCount);

        cv_all_pigeon.setOnClickListener(v -> {
            MyHomingPigeonFragment.start(getBaseActivity());
        });

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(PigeonAddEvent event) {
        dataRefresh();
    }

    private void dataRefresh() {
        mBreedPigeonListModel.getPigeonCount();

        mAdapter.cleanList();
        mBreedPigeonListModel.pi = 1;
        mBreedPigeonListModel.getPigeonList();
    }
}