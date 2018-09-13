package com.cpigeon.book.module.menu.smalltools.lineweather.view.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.base.util.IntentBuilder;
import com.base.util.utility.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.menu.smalltools.lineweather.view.adapter.AWeekWeatherAdapter;
import com.cpigeon.book.widget.mydialog.ShareDialogFragment;
import com.cpigeon.book.widget.mydialog.ViewControlShare;

import java.util.List;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/5/9.
 */

public class AWeekWeatherFragment extends BaseBookFragment {

    private LatLng mLatLng;//需要查询天气的坐标值

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;//

    @BindView(R.id.llz_weather)
    LinearLayout llz_weather;//

    private AWeekWeatherAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_a_week_weather, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialogFragment = new ShareDialogFragment();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getMenu().add("").setIcon(R.drawable.ic_share_line_weather).setOnMenuItemClickListener(item -> {

            showLoading();
            getImageByMap();

            return false;
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);



        setTitle("赛线天气");
        toolbar.setNavigationOnClickListener(v -> finish());

        initViews();

        try {
            mLatLng = getBaseActivity().getIntent().getParcelableExtra("data");
            getAddressByLatlng(mLatLng.longitude, mLatLng.latitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(Activity activity, LatLng mLatLng) {
        IntentBuilder.Builder()
                .putExtra("data", mLatLng)
                .startParentActivity(activity, AWeekWeatherFragment.class);
    }


    private void initViews() {
        mAdapter = new AWeekWeatherAdapter(null);

        GridLayoutManager manager = new GridLayoutManager(getBaseActivity(), 1);
        mRecyclerView.setLayoutManager(manager);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mAdapter.setEnableLoadMore(true);
    }

    /**
     * @param lo 经度
     * @param la 纬度
     */
    private void getAddressByLatlng(double lo, double la) {
        //地理搜索类
        GeocodeSearch geocodeSearch = new GeocodeSearch(getActivity());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                try {
                    if (regeocodeResult.getRegeocodeAddress().getCity().length() != 0) {
                        setTitle(regeocodeResult.getRegeocodeAddress().getCity() + "近期天气预报");
                        Log.d("sousuo", "地点: " + regeocodeResult.getRegeocodeAddress().getCity());
                    }

                    getWeatherTypeForecast(regeocodeResult.getRegeocodeAddress().getCity());
                } catch (Exception e) {
                    Log.d("sousuo", "地点: 异常" + e.getLocalizedMessage());
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });

        //逆地理编码查询条件：逆地理编码查询的地理坐标点、查询范围、坐标类型。
        LatLonPoint latLonPoint = new LatLonPoint(la, lo);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500f, GeocodeSearch.AMAP);
        //异步查询
        geocodeSearch.getFromLocationAsyn(query);
    }

    //获取天气预报
    private void getWeatherTypeForecast(String city) {
        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        WeatherSearchQuery mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        WeatherSearch mweathersearch = new WeatherSearch(getBaseActivity());
        mweathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {

            }

            @Override
            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
                List<LocalDayWeatherForecast> mforecast = localWeatherForecastResult.getForecastResult().getWeatherForecast();
                if (mforecast.size() > 0) {
                    mAdapter.setNewData(mforecast);
                }
            }
        });

        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }


    private ShareDialogFragment dialogFragment;

    private void getImageByMap() {
        try {
            Bitmap bitmap = ImageUtils.view2Bitmap(llz_weather);
            //弹出分享框
            if (dialogFragment != null && dialogFragment.getDialog() != null && dialogFragment.getDialog().isShowing()) {
                dialogFragment.dismiss();
            }

            if (dialogFragment != null) {
                dialogFragment.setShareContent(bitmap);
                dialogFragment.setShareListener(ViewControlShare.getShareResultsDown(getBaseActivity(), dialogFragment, "tp"));
                dialogFragment.setShareType(3);
                dialogFragment.show(getBaseActivity().getFragmentManager(), "share");
            }
            hideLoading();
        } catch (Exception e) {
            hideLoading();
            e.printStackTrace();
        }

        hideLoading();
    }


    private void showLoading() {
        setProgressVisible(true);
    }

    private void hideLoading() {
        setProgressVisible(false);
    }
}
