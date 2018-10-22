package com.cpigeon.book.module.score;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.score.viewmodel.ScoreViewModel;
import com.cpigeon.book.widget.StarRatingView;

import butterknife.BindView;

/**
 * 评分
 * Created by Administrator on 2018/10/15 0015.
 */

public class ScoreFragment extends BaseBookFragment {


    @BindView(R.id.srv_ratable)
    StarRatingView srv_ratable;

    private ScoreViewModel mScoreViewModel;


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

        srv_ratable.setRate(Integer.parseInt(mScoreViewModel.mPigeonEntity.getPigeonScore()));

        srv_ratable.setOnRateChangeListener(new StarRatingView.OnRateChangeListener() {
            @Override
            public void onRateChange(int rate) {
                ToastUtils.showLong(getBaseActivity(), rate + "分");
                mScoreViewModel.score = String.valueOf(rate);
                mScoreViewModel.getZGW_Users_GetLogData();
            }
        });


    }
}
