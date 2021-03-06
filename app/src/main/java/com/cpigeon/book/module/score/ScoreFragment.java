package com.cpigeon.book.module.score;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.utility.ToastUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.score.viewmodel.ScoreViewModel;
import com.cpigeon.book.util.MathUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 评分
 * Created by Administrator on 2018/10/15 0015.
 */

public class ScoreFragment extends BaseBookFragment {

    private ScoreViewModel mScoreViewModel;
    XRecyclerView mRecyclerView;
    ScoreAdapter mAdapter;
    private TextView mTvAllScore;


    public static void start(Activity activity, PigeonEntity mPigeonEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                .startParentActivity(activity, ScoreFragment.class);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mScoreViewModel = new ScoreViewModel();
        initViewModels(mScoreViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("评分");

        mScoreViewModel.mPigeonEntity = (PigeonEntity) getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
        mRecyclerView = findViewById(R.id.list);
        mTvAllScore = findViewById(R.id.tvAllScore);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new ScoreAdapter();
        mAdapter.setOnAllScoreGetListener(allScore -> {
            mScoreViewModel.allScore = MathUtil.doubleformat(allScore,2);
            mTvAllScore.setText(MathUtil.doubleformat(allScore, 2));
        });

        mAdapter.setOnItemScoreGetListener((position, pigeonScoreItemEntity) -> {
            setProgressVisible(true);
            mAdapter.mCurrentOperatePosition = position;
            mScoreViewModel.scoreId = pigeonScoreItemEntity.getItemid();
            mScoreViewModel.score = String.valueOf(pigeonScoreItemEntity.getPscore());
            mScoreViewModel.setPigeonItemScore();
        });
        mRecyclerView.setAdapter(mAdapter);


        setProgressVisible(true);
        mScoreViewModel.getPigeonScoreItem();
    }

    @Override
    protected void initObserve() {
        mScoreViewModel.mDataScoreItem.observe(this, pigeonScoreItemEntities -> {
            setProgressVisible(false);
            mRecyclerView.setRefreshing(false);
            mAdapter.setNewData(pigeonScoreItemEntities);
            mTvAllScore.setText(MathUtil.doubleformat(mAdapter.getAllScore(), 2));
        });

        mScoreViewModel.mDataSetScoreR.observe(this, s -> {
            setProgressVisible(false);
            EventBus.getDefault().post(new PigeonAddEvent());
            ToastUtils.showLong(getBaseActivity(), s);
            mAdapter.notifyOperatePosition();
            EventBus.getDefault().post(new PigeonAddEvent());
        });
    }
}
