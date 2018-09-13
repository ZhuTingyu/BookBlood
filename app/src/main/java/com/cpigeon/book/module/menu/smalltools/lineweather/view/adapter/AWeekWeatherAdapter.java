package com.cpigeon.book.module.menu.smalltools.lineweather.view.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.book.R;
import com.cpigeon.book.util.MapUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/9.
 */

public class AWeekWeatherAdapter extends BaseQuickAdapter<LocalDayWeatherForecast, BaseViewHolder> {

    private Map<String, Integer> icMap2;

    public AWeekWeatherAdapter(List<LocalDayWeatherForecast> data) {
        super(R.layout.item_a_week_weather, data);

       icMap2 = MapUtil.initIcMap1();
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalDayWeatherForecast item) {

        if (helper.getPosition() == 0) {
            helper.setText(R.id.tv_time1, "今");
        } else {
            helper.setText(R.id.tv_time1, item.getDate().substring(item.getDate().length() - 2, item.getDate().length()));
        }


        helper.setText(R.id.tv_ewather1, item.getDayWeather());//白天天气
        helper.setText(R.id.tv_ewather2, item.getNightWeather());//晚上天气

        AppCompatImageView img_weather1 = helper.getView(R.id.img_weather1);
        AppCompatImageView img_weather2 = helper.getView(R.id.img_weather2);

        helper.setText(R.id.tv_temperature1, item.getDayTemp() + "℃");//白天温度
        helper.setText(R.id.tv_temperature2, item.getNightTemp() + "℃");//晚上温度

        helper.setText(R.id.tv_wind_direction1, item.getDayWindDirection());//白天方向
        helper.setText(R.id.tv_wind_direction2, item.getNightWindDirection());//晚上风向

        int integer1 = -1;
        try {
            integer1 = icMap2.get(item.getDayWeather());
        } catch (Exception e) {
            integer1 =-1;
        }

        if (integer1 == -1) {
            img_weather1.setImageResource(icMap2.get("未知"));
        } else {
            img_weather1.setImageResource(icMap2.get(item.getDayWeather()));
        }

        int integer2 = -1;
        try {
            integer2 = icMap2.get(item.getNightWeather());
        } catch (Exception e) {
            integer2 = -1;
        }


        if (integer2 == -1) {
            img_weather2.setImageResource(icMap2.get("未知"));
        } else {
            img_weather2.setImageResource(icMap2.get(item.getNightWeather()));
        }
        if (helper.getPosition() == mData.size() - 1) {
            helper.getView(R.id.tv_bo).setVisibility(View.VISIBLE);
        }
    }
}
