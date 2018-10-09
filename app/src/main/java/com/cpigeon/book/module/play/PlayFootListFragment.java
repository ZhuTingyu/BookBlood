
package com.cpigeon.book.module.play;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.LeagueDetailsEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.play.adapter.PlayFootListAdapter;
import com.cpigeon.book.module.play.viewmodel.PlayListViewModel;
import com.cpigeon.book.module.racing.RacingPigeonEntryFragment;
import com.cpigeon.book.util.KLineManager;
import com.cpigeon.book.widget.LeagueMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;

import java.util.List;


/**
 * 赛鸽管理   足环列表
 * Created by Administrator on 2018/9/10.
 */

public class PlayFootListFragment extends BaseFootListFragment {

    View mHeadView;
    KLineManager mKLineManager;
    PlayListViewModel mViewModel;
    LeagueMarkerView mLeagueMarkerView;
    LineChart mLine;

    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, PigeonEntity.ID_MATCH_PIGEON);
        SearchFragmentParentActivity.
                start(activity, PlayFootListFragment.class, true, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new PlayListViewModel();
        initViewModel(mViewModel);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecorationLine();
        initHeadView();
        mAdapter.addHeaderView(mHeadView);
    }

    private void initHeadView() {
        mHeadView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_play_foot_list_head, null);
        mLine = mHeadView.findViewById(R.id.line);
        mKLineManager = new KLineManager(mLine);
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

        mSelectTypeViewModel.setSelectType(SelectTypeViewModel.TYPE_SEX, SelectTypeViewModel.STATE_STATE, SelectTypeViewModel.TYPE_PIGEON_BLOOD);
        mSelectTypeViewModel.getSelectTypes();
        mViewModel.getFirstLeague();

    }


    @Override
    protected void initObserve() {
        super.initObserve();
        mViewModel.mDataFristLeague.observe(this, data -> {
            mLeagueMarkerView = new LeagueMarkerView(getBaseActivity(), data);
            mLine.setMarker(mLeagueMarkerView);
            mKLineManager.xAxis.setDrawGridLines(false);

            mKLineManager.xAxis.setValueFormatter((v, axisBase) -> {
                if (v == 0) {
                    return "0";
                } else {
                    if (!data.isEmpty()) {
                        return data.get((int) (v - 1)).getMatchInterval();
                    } else {
                        return "0";
                    }
                }
            });

            mKLineManager.yLeft.setValueFormatter((v, axisBase) -> {
                if (v == 0) {
                    return "0";
                } else {
                    return String.valueOf(-v);
                }
            });

            List<Entry> rank = Lists.newArrayList();
            List<Entry> speed = Lists.newArrayList();
            List<Entry> firstSpeed = Lists.newArrayList();
            rank.add(new Entry(0, 0));
            speed.add(new Entry(0, 0));
            firstSpeed.add(new Entry(0, 0));

            for (int i = 0; i < data.size(); i++) {
                LeagueDetailsEntity leagueDetailsEntity = data.get(i);
                rank.add(new Entry(i + 1, -Integer.valueOf(leagueDetailsEntity.getMatchNumber())));
                speed.add(new Entry(i + 1, 0));
                firstSpeed.add(new Entry(i + 1, 0));
            }

            mKLineManager.addLineData(rank, R.color.color_k_line_rank, "名次", YAxis.AxisDependency.LEFT);
            mKLineManager.addLineData(speed, R.color.color_k_line_speed, "分速", YAxis.AxisDependency.RIGHT);
            mKLineManager.addLineData(firstSpeed, R.color.color_k_line_first_speed, "第一分速", YAxis.AxisDependency.RIGHT);
            mKLineManager.setAnimate();
            mKLineManager.setDataToChart();
        });


    }
}