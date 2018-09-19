package com.base.util.map;

import android.arch.lifecycle.LiveData;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.base.application.BaseApplication;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class WeatherLiveData extends LiveData<LocalWeatherLive> {

    static WeatherLiveData mWeatherLiveData;

    private String city;

    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private LocalWeatherLive weatherlive;

    public static WeatherLiveData get(String city) {
        synchronized (WeatherLiveData.class) {
            if (mWeatherLiveData == null) {
                mWeatherLiveData = new WeatherLiveData();
            }
            mWeatherLiveData.city = city;
            return mWeatherLiveData;
        }
    }

    @Override
    protected void onActive() {
        super.onActive();


        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mweathersearch = new WeatherSearch(BaseApplication.getAppContext());

        mweathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                try {
                    if (rCode == 1000) {
                        if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                            weatherlive = weatherLiveResult.getLiveResult();
                            setValue(weatherlive);
                        } else {
                        }
                    } else {
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int rCode) {
            }
        });
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }
}
