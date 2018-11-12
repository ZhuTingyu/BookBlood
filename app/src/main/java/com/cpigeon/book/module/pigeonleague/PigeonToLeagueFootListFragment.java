package com.cpigeon.book.module.pigeonleague;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;
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
        bundle.putString(BaseFootListFragment.TYPEID, PigeonEntity.ID_MATCH_PIGEON);
        bundle.putString(BaseFootListFragment.BITMATCH, PigeonEntity.BIT_MATCH_NO);
        bundle.putString(BaseFootListFragment.STATEID, PigeonEntity.IN_THE_SHED);
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
setTitle("信鸽赛绩");
        mAdapter = new MyHomingPigeonAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                mBreedPigeonListModel.id = PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        }, new LinearLayoutListener() {
            @Override
            public void click(int position) {
                try {
                    PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                    PigeonMatchDetailsActivity.start(getBaseActivity(), mBreedPigeonEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        setStartSearchActvity(SearchPigeonToLeagueActivity.class);//搜索页面


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

    @Override
    public void onResume() {
        super.onResume();
    }
}
