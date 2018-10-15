package com.cpigeon.book.module.pigeonleague;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.pigeonleague.adpter.SelectPigeonToLeagueAdapter;
import com.cpigeon.book.module.pigeonleague.viewmodel.PigeonToLeagueFootListViewModel;
import com.cpigeon.book.widget.SimpleTitleView;
import com.cpigeon.book.widget.stats.StatView;

/**
 * 信鸽赛绩  足环列表
 * Created by Zhu TingYu on 2018/9/12.
 */

public class PigeonToLeagueFootListFragment extends BaseFootListFragment {

    PigeonToLeagueFootListViewModel mViewModel;

    SimpleTitleView mSTvMatchSecond;
    SimpleTitleView mSTvMatchFirst;
    SimpleTitleView mSTvMatchThird;
    StatView mStat1;
    StatView mStat2;
    StatView mStat3;

    View mHeadView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new PigeonToLeagueFootListViewModel();
        initViewModel(mViewModel);
    }

    public static void start(Activity activity) {

        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, PigeonEntity.ID_MATCH_PIGEON);
        bundle.putString(IntentBuilder.KEY_TYPE_2, PigeonEntity.BIT_MATCH_NO);
        SearchFragmentParentActivity.
                start(activity, PigeonToLeagueFootListFragment.class, false, bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getLeagueEntity();
        mRecyclerView.setListPadding(0, 0, 0, 0);
        mRecyclerView.setBackgroundColor(Utils.getColor(R.color.white));
        mRecyclerView.addItemDecorationLine(20);
        initHead();
        mAdapter.addHeaderView(mHeadView);
    }

    @Override
    protected void initData() {
        super.initData();

        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);


        mAdapter = new SelectPigeonToLeagueAdapter();

        mActivity.setSearchClickListener(v -> {
            //搜索
            Bundle bundle = new Bundle();
            bundle.putString(IntentBuilder.KEY_TYPE, PigeonEntity.ID_MATCH_PIGEON);
            bundle.putString(IntentBuilder.KEY_TYPE_2, PigeonEntity.BIT_MATCH_NO);
            BaseSearchActivity.start(getBaseActivity(), SearchPigeonToLeagueActivity.class, bundle);
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                PigeonMatchDetailsActivity.start(getBaseActivity(), mBreedPigeonEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    protected void initObserve() {
        super.initObserve();
        mViewModel.mDataLeague.observe(this, leagueStatEntity -> {
            mSTvMatchFirst.setTitleText(Utils.getString(R.string.text_match_first_count, leagueStatEntity.getOneCount()));
            mSTvMatchSecond.setTitleText(Utils.getString(R.string.text_match_second_count, leagueStatEntity.getTwoCount()));
            mSTvMatchThird.setTitleText(Utils.getString(R.string.text_match_third_count, leagueStatEntity.getThreeCount()));
            mStat1.bindData(leagueStatEntity.getTenCount(), leagueStatEntity.getAllCount());
            mStat2.bindData(leagueStatEntity.getTwentyCount(), leagueStatEntity.getAllCount());
            mStat3.bindData(leagueStatEntity.getFiftyCount(), leagueStatEntity.getAllCount());
        });
    }

    private void initHead() {
        mHeadView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_select_pigeon_to_league_head, null);
        mSTvMatchSecond = mHeadView.findViewById(R.id.sTvMatchSecond);
        mSTvMatchFirst = mHeadView.findViewById(R.id.sTvMatchFirst);
        mSTvMatchThird = mHeadView.findViewById(R.id.sTvMatchThird);
        mStat1 = mHeadView.findViewById(R.id.stat1);
        mStat2 = mHeadView.findViewById(R.id.stat2);
        mStat3 = mHeadView.findViewById(R.id.stat3);
    }
}
