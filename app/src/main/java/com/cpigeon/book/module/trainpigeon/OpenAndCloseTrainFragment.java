package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.weather.LocalWeatherLive;
import com.base.base.BaseMapFragment;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.LocationFormatUtils;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.map.LocationLiveData;
import com.base.util.map.WeatherManager;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.event.UpdateTrainEvent;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonHouseEntity;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.viewmodel.OpenAndCloseTrainViewModel;
import com.cpigeon.book.util.MathUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class OpenAndCloseTrainFragment extends BaseMapFragment {

    private MapView mMap;
    private TextView mTvWeather;
    private TextView mTvPosition;
    private TextView mTvFlyPosition;
    private TextView mTvOk;
    private CardView mCard;
    private TextView mTvDis;
    private WeatherManager mWeatherManager;

    OpenAndCloseTrainViewModel mViewModel;

    public static void start(Activity activity, boolean isOpen, TrainEntity entity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, entity)
                .putExtra(IntentBuilder.KEY_BOOLEAN, isOpen)
                .startParentActivity(activity, OpenAndCloseTrainFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new OpenAndCloseTrainViewModel(getActivity());
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_open_and_close_train, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(mViewModel.mTrainEntity.getPigeonTrainName());
        setToolbarRight(Utils.getString(R.string.text_delete), item -> {
            DialogUtils.createDialogWithLeft(getBaseActivity(), Utils.getString(R.string.text_is_sure_delete_train), sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                setProgressVisible(true);
                mViewModel.deleteTrain();
            });
            return false;
        });
        mWeatherManager = new WeatherManager(getBaseActivity());
        amapManager.setZoomControlsVisible(false);
        mMap = findViewById(R.id.map);
        mTvWeather = findViewById(R.id.tvWeather);
        mTvPosition = findViewById(R.id.tvPosition);
        mTvFlyPosition = findViewById(R.id.tvFlyPosition);
        mCard = findViewById(R.id.card);
        mTvDis = findViewById(R.id.tvDis);
        mTvOk = findViewById(R.id.tvOk);


        mTvOk.setOnClickListener(v -> {
            if (mViewModel.isOpen) {
                setProgressVisible(true);
                mViewModel.openTrain();
            }
        });

        setProgressVisible(true);
        LocationLiveData.get(true).observe(this, aMapLocation -> {
            PigeonHouseEntity entity = UserModel.getInstance().getUserData().pigeonHouseEntity;
            LatLng houseP = new LatLng(entity.getLatitude(), entity.getLongitude());
            LatLng flyP = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());

            String endLo = LocationFormatUtils.strToDMS(LocationFormatUtils.GPS2AjLocation(houseP.longitude));
            String endLa = LocationFormatUtils.strToDMS(LocationFormatUtils.GPS2AjLocation(houseP.latitude));
            String flyLo = LocationFormatUtils.strToDMS(LocationFormatUtils.GPS2AjLocation(flyP.longitude));
            String flyLa = LocationFormatUtils.strToDMS(LocationFormatUtils.GPS2AjLocation(flyP.latitude));
            mTvPosition.setText(Utils.getString(R.string.text_location_lo_la, endLo, endLa));
            mTvFlyPosition.setText(Utils.getString(R.string.text_location_lo_la, flyLo, flyLa));


            mMapMarkerManager.addCustomCenterMarker(houseP, "", R.mipmap.ic_blue_point);
            mMapMarkerManager.addCustomCenterMarker(flyP, "", R.mipmap.ic_red_point);
            mMapMarkerManager.addMap();
            addLine(Lists.newArrayList(houseP, flyP), R.color.colorPrimary);

            mViewModel.fromLa = aMapLocation.getLatitude();
            mViewModel.fromLo = aMapLocation.getLongitude();

            double dis = AMapUtils.calculateLineDistance(houseP, flyP);
            mTvDis.setText(Utils.getString(R.string.text_KM
                    , String.valueOf(MathUtil.doubleformat(dis / 1000, 2))));

            composite.add(mWeatherManager.searchCityByLatLng(flyP, r -> {
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
                        sb.append(Utils.getString(R.string.text_altitude, aMapLocation.getAltitude()));

                        mViewModel.temper = weatherLive.getTemperature();
                        mViewModel.windPower = weatherLive.getWindPower();
                        mViewModel.weather = weatherLive.getWeather();
                        mViewModel.dir = weatherLive.getWindDirection();
                        mViewModel.hum = weatherLive.getHumidity();
                        mViewModel.alt = String.valueOf(aMapLocation.getAltitude());

                    } else {
                        sb.append(Utils.getString(R.string.text_not_get_weather));
                    }
                    mTvWeather.setText(sb);
                }));
            }));
        });
    }

    @Override
    protected void initObserve() {

        mViewModel.mDataOpenR.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createHintDialog(getBaseActivity(), s, sweetAlertDialog -> {
                EventBus.getDefault().post(new UpdateTrainEvent());
                sweetAlertDialog.dismiss();
                finish();
            });

        });

        mViewModel.mDataDeleteR.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createHintDialog(getBaseActivity(), s, sweetAlertDialog -> {
                EventBus.getDefault().post(new UpdateTrainEvent());
                sweetAlertDialog.dismiss();
                finish();
            });
        });
    }

}
