package com.cpigeon.book.module.breedpigeon;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonSexCountEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.basepigeon.StateListAdapter;
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
            try {
                PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                BreedPigeonDetailsFragment.start(getBaseActivity(),
                        mBreedPigeonEntity.getPigeonID(),
                        mBreedPigeonEntity.getFootRingID());
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        RecyclerView recyclerView = view.findViewById(R.id.statList);
        CardView cv_all_pigeon = view.findViewById(R.id.cv_all_pigeon);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        StateListAdapter stateListAdapter = new StateListAdapter(Lists.newArrayList(getResources()
                .getStringArray(R.array.array_breed_pigeon_type)));
        recyclerView.setAdapter(stateListAdapter);
        stateListAdapter.setUnitString(Utils.getString(R.string.text_pigeon_unit));
        stateListAdapter.setMaxCount(countEntity.ZCount);
        stateListAdapter.setNewData(countEntity.getBreedPigeonStat());
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