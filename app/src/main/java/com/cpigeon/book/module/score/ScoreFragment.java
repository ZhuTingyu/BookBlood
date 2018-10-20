package com.cpigeon.book.module.score;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.widget.StarRatingView;

import butterknife.BindView;

/**
 * 评分
 * Created by Administrator on 2018/10/15 0015.
 */

public class ScoreFragment extends BaseBookFragment {


    @BindView(R.id.ratint_bar)
    RatingBar mRatingBar;
    private int count;
//    @BindView(R.id.mRatingStarView)
//    RatingStarView mRatingStarView;

    public static void start(Activity activity, PigeonEntity mPigeonEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                .startParentActivity(activity, ScoreFragment.class);
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

//        mRatingStarView.setOnRatingBarChangeListener(v -> {
//
//        });


        mRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            Log.d("xiaohls", "onViewCreated: " + rating + "       " + fromUser + "      ->" + ratingBar.getRating());
        });



        StarRatingView srv_ratable = (StarRatingView) findViewById(R.id.srv_ratable);
        srv_ratable.setOnRateChangeListener(new StarRatingView.OnRateChangeListener() {
            @Override
            public void onRateChange(int rate) {
               ToastUtils.showLong(getBaseActivity(),rate+"分");
            }
        });

    }
}
