package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.base.base.BaseMapFragment;
import com.base.util.IntentBuilder;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class OpenAndCloseTrainFragment extends BaseMapFragment {

    private MapView mMap;
    private TextView mTvWeather;
    private TextView mTvPosition;
    private TextView mTvFlyPosition;
    private TextView mTvOk;

    private boolean isEnd;

    public static void start(Activity activity){
        IntentBuilder.Builder()
                .startParentActivity(activity, OpenAndCloseTrainFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_open_and_close_train, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        amapManager.setZoomControlsVisible(false);

        mMap = findViewById(R.id.map);
        mTvWeather = findViewById(R.id.tvWeather);
        mTvPosition = findViewById(R.id.tvPosition);
        mTvFlyPosition = findViewById(R.id.tvFlyPosition);
        mTvOk = findViewById(R.id.tvOk);

    }
}
