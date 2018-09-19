package com.base.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.view.View;


import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.base.BaseFragment;
import com.base.http.R;
import com.base.util.Utils;
import com.base.util.map.AmapManager;
import com.base.util.map.MapMarkerManager;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/3/30.
 */

public class BaseMapFragment extends BaseFragment {

    protected MapView mapView;
    protected AMap aMap;
    protected AmapManager amapManager;
    protected MapMarkerManager mMapMarkerManager;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = findViewById(R.id.map);
        aMap = mapView.getMap();
        amapManager = new AmapManager(aMap);
        mMapMarkerManager = new MapMarkerManager(aMap, getBaseActivity());
        mapView.onCreate(savedInstanceState);

    }

    public void addLine(List<LatLng> points, @ColorRes int resId){
        aMap.addPolyline(new PolylineOptions().
                addAll(points).width(10).color(Utils.getColor(resId)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }
}
