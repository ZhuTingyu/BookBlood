package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.weather.LocalWeatherLive;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.map.WeatherManager;
import com.base.util.utility.StringUtil;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.FlyBackAddRecordEvent;
import com.cpigeon.book.event.UpdateTrainEvent;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.adpter.FlyBackRecordAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.FlyBackRecordViewModel;
import com.cpigeon.book.util.TextViewUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

/**
 * Created by Zhu TingYu on 2018/9/6.
 * //归巢记录
 */

public class FlyBackRecordFragment extends BaseBookFragment {

    boolean isEnd = false;

    XRecyclerView mRecyclerView;
    FlyBackRecordAdapter mAdapter;
    TextView mTvOk;
    FlyBackRecordViewModel mViewModel;
    CompleteTrainDialog mCompleteTrainDialog;

    WeatherManager mWeatherManager;

    public static void start(Activity activity, TrainEntity trainEntity, boolean isEnd) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_BOOLEAN, isEnd)
                .putExtra(IntentBuilder.KEY_DATA, trainEntity)
                .startParentActivity(activity, FlyBackRecordFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new FlyBackRecordViewModel(getBaseActivity());
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xreclyview_with_bottom_btn, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWeatherManager = new WeatherManager(getBaseActivity());
        setTitle(mViewModel.mTrainEntity.getPigeonTrainName());

        isEnd = getBaseActivity().getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);
        mRecyclerView = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);
        mAdapter = new FlyBackRecordAdapter(isEnd);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setRefreshListener(() -> {
            mViewModel.getFlyBackRecord();
        });

        if (isEnd) {
            mTvOk.setVisibility(View.GONE);
        } else {
            setToolbarRight(R.string.text_add, item -> {

                if (mViewModel.mTrainEntity.getDistance() != 0) {
                    if (mViewModel.isHaveNotBack()) {
                        SelectPigeonToFlyBackFragment.start(getBaseActivity(), mViewModel.mTrainEntity);
                    } else {
                        DialogUtils.createHintDialog(getBaseActivity(), Utils.getString(R.string.text_all_pigeon_back_yet));
                    }
                } else {
                    mCompleteTrainDialog = new CompleteTrainDialog();
                    mCompleteTrainDialog.setOnSureClickListener((time, latLng, dis, location) -> {
                        setProgressVisible(true);
                        mViewModel.fromTime = time;
                        mViewModel.fromLo = latLng.longitude;
                        mViewModel.fromLa = latLng.latitude;
                        mViewModel.dis = dis;
                        mViewModel.fromplace = location;
                        mViewModel.setTrainInfo();
                    });
                    mCompleteTrainDialog.show(getFragmentManager());
                }


                return false;
            });
            TextViewUtil.setCancle(mTvOk);
            mTvOk.setText(R.string.text_end_train);
            mTvOk.setOnClickListener(v -> {
                DialogUtils.createDialogWithLeft(getBaseActivity(), Utils.getString(R.string.text_is_end_train), sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    setProgressVisible(true);
                    mViewModel.endTrain();
                });
            });
        }
        composite.add(mWeatherManager.searchCityByLatLng(mViewModel.mHouseLocation, r -> {
            composite.add(mWeatherManager.requestWeatherByCityName(r.data.getCity(), response -> {
                setProgressVisible(false);
                StringBuilder sb = new StringBuilder();
                if (response.isOk()) {
                    LocalWeatherLive weatherLive = response.getData();
                    sb.append(weatherLive.getWeather());
                    sb.append(StringUtil.blankString());
                    sb.append(Utils.getString(R.string.text_temp, weatherLive.getTemperature()));
                    sb.append(StringUtil.blankString());
                    sb.append(Utils.getString(R.string.text_wind_direction, weatherLive.getWindDirection()));
                    sb.append(StringUtil.blankString());

                    mViewModel.temper = weatherLive.getTemperature();
                    mViewModel.windPower = weatherLive.getWindPower();
                    mViewModel.weather = weatherLive.getWeather();
                    mViewModel.dir = weatherLive.getWindDirection();
                    mViewModel.hum = weatherLive.getHumidity();

                } else {
                    sb.append(Utils.getString(R.string.text_not_get_weather));
                }
            }));
        }));

        setProgressVisible(true);
        mViewModel.getTrainEntity();
        mViewModel.getFlyBackRecord();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(FlyBackAddRecordEvent event) {
        mViewModel.getFlyBackRecord();
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mDataTrain.observe(this, trainEntity -> {
            mViewModel.mTrainEntity.setDistance(trainEntity.getDistance());
            mViewModel.mTrainEntity.setFromFlyTime(trainEntity.getFromFlyTime());
            mAdapter.setTrainEntity(mViewModel.mTrainEntity);
        });

        mViewModel.mDataFlyBack.observe(this, flyBackRecordEntities -> {
            setProgressVisible(false);
            if (Lists.isEmpty(flyBackRecordEntities)) {
                mAdapter.setNewData(Lists.newArrayList());
            } else {
                Collections.sort(flyBackRecordEntities, new FlyBackRecordComparator());
                for (int i = 0, len = flyBackRecordEntities.size(); i < len; i++) {
                    flyBackRecordEntities.get(i).order = i + 1;
                }
                mAdapter.setNewData(mAdapter.get(flyBackRecordEntities));
            }
        });

        mViewModel.mDataDeleteR.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createSuccessDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                EventBus.getDefault().post(new UpdateTrainEvent());
                finish();
            });
        });

        mViewModel.mDataSetTrainInfoR.observe(this, s -> {
            setProgressVisible(false);
            mViewModel.mTrainEntity.setDistance(mViewModel.dis);
            mViewModel.mTrainEntity.setFromFlyTime(mViewModel.fromTime);
            DialogUtils.createSuccessDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                mCompleteTrainDialog.dismiss();
            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCompleteTrainDialog != null) {
            mCompleteTrainDialog.onActivityResult(requestCode, resultCode, data);
        }
    }
}
