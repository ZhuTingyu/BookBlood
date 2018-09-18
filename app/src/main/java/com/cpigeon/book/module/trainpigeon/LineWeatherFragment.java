package com.cpigeon.book.module.trainpigeon;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.weather.LocalWeatherLive;
import com.base.base.BaseMapFragment;
import com.base.util.FragmentUtils;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.http.GsonUtil;
import com.base.util.map.MapMarkerManager;
import com.base.util.map.WeatherManager;
import com.base.util.utility.LogUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.module.menu.smalltools.lineweather.view.activity.AWeekWeatherFragment;
import com.cpigeon.book.util.BitmapUtils;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Zhu TingYu on 2018/9/18.
 */

public class LineWeatherFragment extends BaseMapFragment {

    int i = 0;
    double mStartLo;
    double mStartLa;

    double mEndLo;
    double mEndLa;

    List<LatLng> afterPoints = Lists.newArrayList();
    @BindView(R.id.img_weather)
    ImageView imgWeather;
    @BindView(R.id.tv_place_name)
    TextView tvPlaceName;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_weather1)
    TextView tvWeather1;
    @BindView(R.id.tv_weather2)
    TextView tvWeather2;
    @BindView(R.id.ll_weather)
    LinearLayout llWeather;
    @BindView(R.id.llz_place)
    LinearLayout llzPlace;
    @BindView(R.id.tv_triangle)
    TextView tvTriangle;
    @BindView(R.id.img_start_stop)
    ImageView imgStartStop;
    @BindView(R.id.llz_weather)
    LinearLayout llzWeather;
    ImageView mImgClose;
    private ArrayList<LocalWeatherLive> weatherList = Lists.newArrayList();
    private WeatherManager manager;
    private MapMarkerManager markerManager;
    private boolean isShowEnd = true;

    private Map<String, Integer> icMap1;
    private Map<String, Integer> icMap2;

    public static final int MARKER_NORMAL = -1;
    public static final int MARKER_START = 0;
    public static final int MARKER_END = 1;
    double distanceOne = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_line_weather, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImgClose = findViewById(R.id.imgClose);
        mImgClose.setOnClickListener(v -> {
            FragmentUtils.remove(this);
        });

        markerManager = new MapMarkerManager(aMap,getBaseActivity());
        manager = new WeatherManager(getBaseActivity());
        Bundle bundle = getArguments();

        int point = 1;
        LatLng start = bundle.getParcelable(IntentBuilder.KEY_DATA);
        LatLng end = bundle.getParcelable(IntentBuilder.KEY_DATA_2);
         mStartLo = start.longitude;
         mStartLa = start.latitude;
         mEndLo = end.longitude;
         mEndLa = end.latitude;
        float distance = bundle.getFloat(IntentBuilder.KEY_DATA_3);
        if (distance < 150 * 1000) {
            point = 2;
            distanceOne = (distance / 2);
        } else if (150 * 1000 <= distance && distance < 300 * 1000) {
            point = 3;
            distanceOne = (distance / 3);
        } else if (300 * 1000 <= distance && distance < 450 * 1000) {
            point = 4;
            distanceOne = (distance / 4);
        } else if (450 * 1000 <= distance && distance < 600 * 1000) {
            point = 5;
            distanceOne = (distance / 5);
        } else if (distance >= 600 * 1000) {
            point = 6;
            distanceOne = (distance / 6);
        }

        double x = (mStartLo - mEndLo) * (1d / point);
        double y = (mStartLa - mEndLa) * (1d / point);
        Log.d("fdf", "斜度:" + x);

        afterPoints.add(new LatLng(mEndLa, mEndLo));
        for (int i = 1; i < point; i++) {
            double x1 = mEndLa + (y * i);//第一个点La
            double x2 = mEndLo + (x * i);//第一个点Long
            LatLng latLng = new LatLng(x1, x2);
            afterPoints.add(latLng);
        }
        afterPoints.add(new LatLng(mStartLa, mStartLo));

        setProgressVisible(true);
        search();

    }

    private void search() {
        composite.add(manager.searchCityByLatLng(afterPoints.get(i), r -> {
            composite.add(manager.requestWeatherByCityName(r.data.getCity(), response -> {
                if (response.isOk()) {
                    weatherList.add(response.data);

                    Log.d("sousuo", "1---->: " + response.getData().getWeather());
                    Log.d("sousuo", "1---->: " + weatherList.size());
                    if (weatherList.size() == afterPoints.size()) {

                        initMap();
                        setProgressVisible(false);
                        LogUtil.print("debug" + "hideLoading");
                    } else {
                        search();
                    }
                } else {
                    Log.d("sousuo", "2---->: " + weatherList.size());

                    LocalWeatherLive mData = new LocalWeatherLive();
                    mData.setCity("未知");
                    mData.setAdCode("未知");
                    mData.setHumidity("未知");
                    mData.setProvince("未知");
                    mData.setReportTime("未知");
                    mData.setWeather("未知");
                    mData.setWindDirection("未知");
                    mData.setWindPower("未知");
                    weatherList.add(mData);

                    if (weatherList.size() == afterPoints.size()) {

                        initMap();
                        setProgressVisible(false);
                        LogUtil.print("debug" + "hideLoading");
                    } else {
                        search();
                    }
                }
            }));
            i++;
        }));
    }

    private void initMap() {
        aMap.addPolyline(new PolylineOptions().
                addAll(afterPoints).width(10).color(Color.argb(255, 61, 188, 196)));

        markerManager.addCustomMarker2(afterPoints.get(0), null, BitmapUtils.getViewBitmap(getInfoWindow(GsonUtil.toJson(weatherList.get(0)), MARKER_START, 0, distanceOne)));

        for (int i = 1, len = afterPoints.size() - 1; i < len; i++) {
            markerManager.addCustomMarker2(afterPoints.get(i), null, BitmapUtils.getViewBitmap(getInfoWindow(GsonUtil.toJson(weatherList.get(i)), MARKER_NORMAL, i, distanceOne)));
        }

        markerManager.addCustomMarker2(afterPoints.get(afterPoints.size() - 1), null,
                BitmapUtils.getViewBitmap(getInfoWindow(GsonUtil.toJson(weatherList.get(afterPoints.size() - 1)), MARKER_END, afterPoints.size() - 1, distanceOne)));


        List<Marker> markerList = markerManager.addMap();
        if (isShowEnd) {
            markerList.get(markerList.size() - 1).showInfoWindow();
        } else {
            markerList.get(0).showInfoWindow();
        }

        RxUtils.delayed(200, aLong -> {
            aMap.moveCamera(CameraUpdateFactory.zoomTo(aMap.getCameraPosition().zoom - 1));
        });

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Log.d("sousuo", "onMarkerClick: " + marker.getPosition());

                int siez = markerList.size();
                for (int i = 0; i < siez; i++) {
                    if (markerList.get(i).getPosition().latitude == marker.getPosition().latitude &&
                            markerList.get(i).getPosition().longitude == marker.getPosition().longitude) {
                        AWeekWeatherFragment.start(getBaseActivity(), markerList.get(i).getPosition());
                        return true;
                    }
                }
                return false;
            }
        });

    }

    public View getInfoWindow(String info, int type, int position, double distanceOne) {

        Log.d("xiaohl", "getInfoWindow: 111-->" + type);

        if (llzWeather == null) {
            llzWeather = (LinearLayout) findViewById(R.id.llz_weather);
            llzPlace = (LinearLayout) llzWeather.findViewById(R.id.llz_place);
            imgWeather = (ImageView) llzWeather.findViewById(R.id.img_weather);
            tvPlaceName = (TextView) llzWeather.findViewById(R.id.tv_place_name);
            tvDistance = (TextView) llzWeather.findViewById(R.id.tv_distance);

            llWeather = (LinearLayout) llzWeather.findViewById(R.id.ll_weather);
            tvWeather1 = (TextView) llzWeather.findViewById(R.id.tv_weather1);
            tvWeather2 = (TextView) llzWeather.findViewById(R.id.tv_weather2);

            tvTriangle = (TextView) llzWeather.findViewById(R.id.tv_triangle);

            imgStartStop = (ImageView) llzWeather.findViewById(R.id.img_start_stop);
        }

        if (type == MARKER_END) {
            imgStartStop.setImageResource(R.drawable.line_weather_stop);
        } else if (type == MARKER_START) {
            imgStartStop.setImageResource(R.drawable.line_weather_start);
        } else {
            imgStartStop.setImageResource(R.drawable.line_weather_start);
        }

        render(info, type, position, distanceOne);
        return llzWeather;
    }

    private void render(String info, int type, int position, double distanceOne) {
        LocalWeatherLive weatherLive = GsonUtil.fromJson(info, new TypeToken<LocalWeatherLive>() {
        }.getType());

        try {
            tvPlaceName.setText(weatherLive.getCity());
        } catch (Exception e) {
            tvPlaceName.setText("暂无该地区名称");
        }

        int integer1 = -1;
        int integer2 = -1;
        try {
            integer1 = icMap1.get(weatherLive.getWeather());
            integer2 = icMap2.get(weatherLive.getWeather());
        } catch (Exception e) {
            integer1 = -1;
            integer2 = -1;
        }

        if (type == MARKER_START) {
            tvDistance.setText("司放地");
        } else if (type == MARKER_END) {
            double jl = distanceOne * position;
            tvDistance.setText("归巢地：约" + new DecimalFormat("0.00").format(jl / 1000) + "KM");
        } else {
            double jl = distanceOne * position;
            tvDistance.setText("约" + new DecimalFormat("0.00").format(jl / 1000) + "KM");
        }

        if (type == MARKER_START || type == MARKER_END) {
            //起点 或者终点
            tvTriangle.setBackgroundResource(R.color.colorWhite);
            llzPlace.setBackgroundResource(R.color.colorWhite);
            llWeather.setBackgroundResource(R.color.bg_line_weather_l);
            tvWeather1.setTextColor(getResources().getColor(R.color.colorWhite));
            tvWeather2.setTextColor(getResources().getColor(R.color.colorWhite));

            tvPlaceName.setTextColor(getResources().getColor(R.color.bg_line_weather_l));
            tvDistance.setTextColor(getResources().getColor(R.color.bg_line_weather_l));

            try {
                if (integer2 == -1) {
                    imgWeather.setImageResource(icMap2.get("未知"));
                } else {
                    imgWeather.setImageResource(icMap2.get(weatherLive.getWeather()));
                }
            } catch (Exception e) {

            }

        } else {
            //中间点
            tvTriangle.setBackgroundResource(R.color.bg_line_weather_l);
            llzPlace.setBackgroundResource(R.color.bg_line_weather_l);
            llWeather.setBackgroundResource(R.color.colorWhite);
            tvWeather1.setTextColor(getResources().getColor(R.color.bg_line_weather_l));
            tvWeather2.setTextColor(getResources().getColor(R.color.bg_line_weather_l));

            tvPlaceName.setTextColor(getResources().getColor(R.color.colorWhite));
            tvDistance.setTextColor(getResources().getColor(R.color.colorWhite));

            try {
                if (integer1 == -1) {
                    imgWeather.setImageResource(icMap1.get("未知"));
                } else {
                    imgWeather.setImageResource(icMap1.get(weatherLive.getWeather()));
                }
            } catch (Exception e) {

            }
        }
    }

}
