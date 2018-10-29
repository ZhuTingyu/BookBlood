
package com.cpigeon.book.module.play;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;

import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.LeagueDetailsEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.basepigeon.InputPigeonFragment;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.homingpigeon.MyHomingPigeonFragment;
import com.cpigeon.book.module.play.viewmodel.PlayListViewModel;
import com.cpigeon.book.util.KLineManager;
import com.cpigeon.book.widget.LeagueMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private CardView mCvLine;

    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(BaseFootListFragment.TYPEID, PigeonEntity.ID_MATCH_PIGEON);
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
        initHeadViews();
    }

    private void initHeadViews() {
        mHeadView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_play_foot_list_head, null);

        //全部信鸽
        CardView cv_all_pigeon;
        cv_all_pigeon = mHeadView.findViewById(R.id.cv_all_pigeon);
        cv_all_pigeon.setOnClickListener(v -> {
            MyHomingPigeonFragment.start(getBaseActivity());
        });

        mLine = mHeadView.findViewById(R.id.line);
        mCvLine = mHeadView.findViewById(R.id.cvLine);

        mKLineManager = new KLineManager(mLine);
    }

    @Override
    protected void initData() {
        super.initData();

        setStartSearchActvity(PlayFootListActivity.class);//搜索页面

        mTvOk.setVisibility(View.VISIBLE);
        view_placeholder.setVisibility(View.VISIBLE);

        mTvOk.setText(R.string.text_add_play_pigeon);
        mTvOk.setOnClickListener(v -> {
            //赛鸽录入
            InputPigeonFragment.start(getBaseActivity(), null, null, null, null, null, 0);
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

        mViewModel.getFirstLeague();//获取第一名赛绩
    }


    @Override
    protected void initObserve() {
        super.initObserve();
        mViewModel.mDataFristLeague.observe(this, data -> {


            mAdapter.addHeaderView(mHeadView);

            if (Lists.isEmpty(data)) {
                mCvLine.setVisibility(View.GONE);
                return;
            }
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(PigeonAddEvent event) {
        dataRefresh();
    }

    private void dataRefresh() {
        mAdapter.cleanList();
        mBreedPigeonListModel.pi = 1;
        mBreedPigeonListModel.getPigeonList();
        mViewModel.getFirstLeague();//获取第一名赛绩
    }

}