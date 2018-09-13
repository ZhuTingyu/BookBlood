package com.cpigeon.book.util;

import com.cpigeon.book.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 地图工具类
 * Created by Administrator on 2017/11/17.
 */
public class MapUtil {

    public static Map<String, Integer> initIcMap1() {
        Map<String, Integer> icMap1 = new HashMap<>();
        icMap1.put("阵雨", R.drawable.ic_weather_white_a_shower_b);
        icMap1.put("多云", R.drawable.ic_weather_white_cloudy_b);
        icMap1.put("大雨", R.drawable.ic_weather_white_heavy_rain_b);
        icMap1.put("中雨", R.drawable.ic_weather_white_moderate_rain_b);
        icMap1.put("小雨", R.drawable.ic_weather_white_light_rain_b);

        icMap1.put("小雪", R.drawable.ic_weather_light_snow_b);
        icMap1.put("中雪", R.drawable.ic_weather_light_snow_b);
        icMap1.put("大雪", R.drawable.ic_weather_light_snow_b);

        icMap1.put("雨夹雪", R.drawable.ic_weather_white_sleet_b);
        icMap1.put("霾", R.drawable.ic_weather_white_smog_b);
        icMap1.put("晴", R.drawable.ic_weather_white_sunny_b);
        icMap1.put("雷阵雨", R.drawable.ic_weather_white_thunder_shower_b);
        icMap1.put("阴", R.drawable.ic_weather_white_yin_b);

        icMap1.put("未知", R.drawable.ic_weather_unknown_b);

        return icMap1;
    }

    public static Map<String, Integer> initIcMap2() {
        Map<String, Integer> icMap2 = new HashMap<>();

        icMap2.put("阵雨", R.drawable.ic_weather_white_a_shower_l);
        icMap2.put("多云", R.drawable.ic_weather_white_cloudy_l);

        icMap2.put("大雨", R.drawable.ic_weather_white_heavy_rain_l);
        icMap2.put("中雨", R.drawable.ic_weather_white_moderate_rain_l);
        icMap2.put("小雨", R.drawable.ic_weather_white_light_rain_l);

        icMap2.put("小雪", R.drawable.ic_weather_light_snow_l);
        icMap2.put("中雪", R.drawable.ic_weather_light_snow_l);
        icMap2.put("大雪", R.drawable.ic_weather_light_snow_l);

        icMap2.put("雨夹雪", R.drawable.ic_weather_white_sleet_l);
        icMap2.put("霾", R.drawable.ic_weather_white_smog_l);
        icMap2.put("晴", R.drawable.ic_weather_white_sunny_l);
        icMap2.put("雷阵雨", R.drawable.ic_weather_white_thunder_shower_l);
        icMap2.put("阴", R.drawable.ic_weather_white_yin_l);

        icMap2.put("未知", R.drawable.ic_weather_unknown_l);

        return icMap2;
    }
}
