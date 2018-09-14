package com.base.base;

import android.os.Bundle;
import android.view.View;


import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.base.BaseFragment;
import com.base.http.R;
import com.base.util.map.AmapManager;

/**
 * Created by Zhu TingYu on 2018/3/30.
 */

public class BaseMapFragment extends BaseFragment {

    protected MapView mapView;
    protected AMap aMap;
    protected AmapManager amapManager;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = findViewById(R.id.map);
        aMap = mapView.getMap();
        amapManager = new AmapManager(aMap);
        mapView.onCreate(savedInstanceState);

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
