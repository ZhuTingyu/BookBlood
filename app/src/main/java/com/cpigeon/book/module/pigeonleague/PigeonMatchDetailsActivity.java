package com.cpigeon.book.module.pigeonleague;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookActivity;
import com.cpigeon.book.module.pigeonleague.adpter.PigeonMatchDetailsAdapter;
import com.cpigeon.book.util.KLineManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/14.
 */

public class PigeonMatchDetailsActivity extends BaseBookActivity {

    RecyclerView mRecyclerView;
    PigeonMatchDetailsAdapter mAdapter;

    public static void start(Activity activity, String pigeonId) {
        IntentBuilder.Builder(activity, PigeonMatchDetailsActivity.class)
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .startActivity();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pigeon_match_details;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()
                , LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new PigeonMatchDetailsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());

        mAdapter.addHeaderView(initHeadView(Lists.newTestArrayList()));
    }

    private View initHeadView(List<String> data) {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_pigeon_match_details_head, null);
        LineChart mKLine;
        TextView mTvUserName;

        mKLine = view.findViewById(R.id.kLine);
        mTvUserName = view.findViewById(R.id.tvUserName);

        KLineManager kLineManager = new KLineManager(mKLine);
        kLineManager.xAxis.setDrawGridLines(false);

        kLineManager.xAxis.setValueFormatter((v, axisBase) -> {
            if (v == 0) {
                return "0";
            } else {
                if (!data.isEmpty()) {
                    return data.get((int) (v - 1));
                } else {
                    return "0";
                }
            }
        });

        kLineManager.yLeft.setValueFormatter((v, axisBase) -> {
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

        /*kLineManager.addLineData(rank, R.color.color_k_line_rank, "名次", YAxis.AxisDependency.LEFT);
        kLineManager.addLineData(speed, R.color.color_k_line_speed, "分速", YAxis.AxisDependency.RIGHT);
        kLineManager.addLineData(speed, R.color.color_k_line_first_speed, "第一分速", YAxis.AxisDependency.RIGHT);*/
        kLineManager.setAnimate();
        kLineManager.setDataToChart();

        return view;
    }
}
