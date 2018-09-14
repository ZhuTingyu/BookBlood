package com.cpigeon.book.module.menu.smalltools.ullage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.weather.LocalWeatherLive;
import com.base.base.BaseMapFragment;
import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.http.GsonUtil;
import com.base.util.map.MapMarkerManager;
import com.base.util.map.WeatherManager;
import com.base.util.utility.ImageUtils;
import com.base.util.utility.LogUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.module.menu.smalltools.lineweather.view.activity.AWeekWeatherFragment;
import com.cpigeon.book.util.BitmapUtils;
import com.cpigeon.book.util.MapUtil;
import com.cpigeon.book.util.MathUtil;
import com.cpigeon.book.widget.mydialog.ShareDialogFragment;
import com.cpigeon.book.widget.mydialog.ViewControlShare;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMShareAPI;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;


/**
 * 空距计算详情
 * Created by Administrator on 2018/1/23.
 */

public class UllageToolDetailsFragment extends BaseMapFragment {


    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tv_kj)
    TextView tvKj;//空距
    @BindView(R.id.tv_fs)
    TextView tvFs;//分速
    @BindView(R.id.ll_z)
    LinearLayout llZ;//

    private AMap aMap;
    protected Unbinder unbinder;

    private ShareDialogFragment dialogFragment;

    private double sfd_lo, sfd_la, gc_lo, gc_la;
    private String result, time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ullage_tool_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("空距计算");
        sfd_lo = getBaseActivity().getIntent().getDoubleExtra("sfd_lo", 0);
        sfd_la = getBaseActivity().getIntent().getDoubleExtra("sfd_la", 0);

        gc_lo = getBaseActivity().getIntent().getDoubleExtra("gc_lo", 0);
        gc_la = getBaseActivity().getIntent().getDoubleExtra("gc_la", 0);
        result = getBaseActivity().getIntent().getStringExtra("result");
        time = getBaseActivity().getIntent().getStringExtra("time");

        initViews(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected void initViews(Bundle savedInstanceState) {


        if (aMap == null) {
            aMap = mapView.getMap();
            markerManager = new MapMarkerManager(aMap, getBaseActivity());
        }

        if (!result.equals("")) {
            tvKj.setText(String.valueOf(MathUtil.doubleformat(Double.parseDouble(result), 2)) + "公里");//空距
        }

        if (time != null) {
            tvFs.setText(time);//分速
        }

        afterPoints = new ArrayList<>();
        afterPoints.add(new LatLng(sfd_la, sfd_lo));
        afterPoints.add(new LatLng(gc_la, gc_lo));


        dialogFragment = new ShareDialogFragment();
        setToolbarRight("分享", item -> {
            startJt();
            return true;
        });


        manager = new WeatherManager(getContext());
        icMap1 = MapUtil.initIcMap1();
        icMap2 = MapUtil.initIcMap2();

        i = 0;
        weatherList.clear();
        searchCityByPoint(Double.parseDouble(result) * 1000);
    }

    protected final CompositeDisposable composite = new CompositeDisposable();
    private WeatherManager manager;
    private List<LatLng> afterPoints;//沿途路线的点
    int i = 0;
    private ArrayList<LocalWeatherLive> weatherList = new ArrayList<>();
    private MapMarkerManager markerManager;
    private Map<String, Integer> icMap1;
    private Map<String, Integer> icMap2;
    private boolean isShowEnd = true;

    private void searchCityByPoint(double distanceOne) {
        try {
            Log.d("sousuo", "onRegeocodeSearched: 0");
            composite.add(manager.searchCityByLatLng(afterPoints.get(i), r -> {
                composite.add(manager.requestWeatherByCityName(r.data.getCity(), response -> {
                    if (response.isOk()) {
                        weatherList.add(response.data);

                        Log.d("sousuo", "1---->: " + weatherList.size());
                        if (weatherList.size() == afterPoints.size()) {

                            initMap(distanceOne);

                            LogUtil.print("debug" + "hideLoading");
                        } else {
                            searchCityByPoint(distanceOne);
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
                            initMap(distanceOne);
                            LogUtil.print("debug" + "hideLoading");
                        } else {
                            searchCityByPoint(distanceOne);
                        }
                    }
                }));
                i++;
            }));
        } catch (Exception e) {
            Log.d("sousuo", "3---->: 异常" + e.getLocalizedMessage());
        }
    }

    public static final int MARKER_NORMAL = -1;
    public static final int MARKER_START = 0;
    public static final int MARKER_END = 1;

    private void initMap(double distanceOne) {
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


    private LinearLayout llz_weather;//点标记
    private LinearLayout llz_place;//总的地点
    private ImageView img_weather;//天气图标
    private TextView tv_place_name;//地名
    private TextView tv_distance;//距离


    private LinearLayout ll_weather;//一周天气
    private TextView tv_weather1;//一周
    private TextView tv_weather2;//天气

    private TextView tv_triangle;//三角

    private ImageView img_start_stop;//定位点起点终点图标

    public View getInfoWindow(String info, int type, int position, double distanceOne) {

        Log.d("xiaohl", "getInfoWindow: 111-->" + type);

        if (llz_weather == null) {
            llz_weather = (LinearLayout) findViewById(R.id.llz_weather);
            llz_place = (LinearLayout) llz_weather.findViewById(R.id.llz_place);
            img_weather = (ImageView) llz_weather.findViewById(R.id.img_weather);
            tv_place_name = (TextView) llz_weather.findViewById(R.id.tv_place_name);
            tv_distance = (TextView) llz_weather.findViewById(R.id.tv_distance);

            ll_weather = (LinearLayout) llz_weather.findViewById(R.id.ll_weather);
            tv_weather1 = (TextView) llz_weather.findViewById(R.id.tv_weather1);
            tv_weather2 = (TextView) llz_weather.findViewById(R.id.tv_weather2);

            tv_triangle = (TextView) llz_weather.findViewById(R.id.tv_triangle);

            img_start_stop = (ImageView) llz_weather.findViewById(R.id.img_start_stop);
        }

        if (type == MARKER_END) {
            img_start_stop.setImageResource(R.drawable.line_weather_stop);
        } else if (type == MARKER_START) {
            img_start_stop.setImageResource(R.drawable.line_weather_start);
        } else {
            img_start_stop.setImageResource(R.drawable.line_weather_start);
        }

        render(info, type, position, distanceOne);
        return llz_weather;
    }

    private void render(String info, int type, int position, double distanceOne) {
        LocalWeatherLive weatherLive = GsonUtil.fromJson(info, new TypeToken<LocalWeatherLive>() {
        }.getType());

        try {
            tv_place_name.setText(weatherLive.getCity());
        } catch (Exception e) {
            tv_place_name.setText("暂无该地区名称");
        }

        int integer1 = 0;
        int integer2 = 0;
        try {
            integer1 = icMap1.get(weatherLive.getWeather());
            integer2 = icMap2.get(weatherLive.getWeather());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (type == MARKER_START) {
            tv_distance.setText("司放地");
        } else if (type == MARKER_END) {
            double jl = distanceOne * position;
            tv_distance.setText("鸽舍：约" + new DecimalFormat("0.00").format(jl / 1000) + "KM");
        } else {
            double jl = distanceOne * position;
            tv_distance.setText("约" + new DecimalFormat("0.00").format(jl / 1000) + "KM");
        }

        if (type == MARKER_START || type == MARKER_END) {
            //起点 或者终点
            tv_triangle.setBackgroundResource(R.color.colorWhite);
            llz_place.setBackgroundResource(R.color.colorWhite);
            ll_weather.setBackgroundResource(R.color.bg_line_weather_l);
            tv_weather1.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_weather2.setTextColor(getResources().getColor(R.color.colorWhite));

            tv_place_name.setTextColor(getResources().getColor(R.color.bg_line_weather_l));
            tv_distance.setTextColor(getResources().getColor(R.color.bg_line_weather_l));

            try {
                if (integer2 == 0) {
                    img_weather.setImageResource(icMap2.get("阴"));
                } else {
                    img_weather.setImageResource(icMap2.get(weatherLive.getWeather()));
                }
            } catch (Exception e) {

            }

        } else {
            //中间点
            tv_triangle.setBackgroundResource(R.color.bg_line_weather_l);
            llz_place.setBackgroundResource(R.color.bg_line_weather_l);
            ll_weather.setBackgroundResource(R.color.colorWhite);
            tv_weather1.setTextColor(getResources().getColor(R.color.bg_line_weather_l));
            tv_weather2.setTextColor(getResources().getColor(R.color.bg_line_weather_l));

            tv_place_name.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_distance.setTextColor(getResources().getColor(R.color.colorWhite));

            try {
                if (integer1 == 0) {
                    img_weather.setImageResource(icMap1.get("阴"));
                } else {
                    img_weather.setImageResource(icMap1.get(weatherLive.getWeather()));
                }
            } catch (Exception e) {

            }
        }
    }

    //截图
    private void startJt() {

        showLoading();
        /**
         * 对地图进行截屏
         */
        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {

            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int status) {

                try {
                    img.setVisibility(View.VISIBLE);

                    img.setImageBitmap(bitmap);
                    Bitmap bitmaps = ImageUtils.view2Bitmap(llZ);

                    img.setVisibility(View.GONE);
                    //弹出分享框
                    if (dialogFragment != null && dialogFragment.getDialog() != null && dialogFragment.getDialog().isShowing()) {
                        dialogFragment.dismiss();
                    }

                    hideLoading();

                    if (dialogFragment != null) {
                        dialogFragment.setShareContent(bitmaps);
                        dialogFragment.setShareListener(ViewControlShare.getShareResultsDown(getContext(), dialogFragment, "tp"));
                        dialogFragment.setShareType(3);
                        dialogFragment.show(getBaseActivity().getFragmentManager(), "share");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showLoading() {
        setProgressVisible(true);
    }

    private void hideLoading() {
        setProgressVisible(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getBaseActivity()).onActivityResult(requestCode, resultCode, data);
    }
}
