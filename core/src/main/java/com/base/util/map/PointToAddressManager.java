package com.base.util.map;

import android.content.Context;
import android.view.ContextThemeWrapper;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;

/**
 * Created by Zhu TingYu on 2018/8/7.
 */

public class PointToAddressManager {

    GeocodeSearch geocoderSearch;
    RegeocodeQuery query;

    public PointToAddressManager(Context context){
        geocoderSearch = new GeocodeSearch(context);
    }

    public PointToAddressManager setSearchPoint(LatLonPoint latLonPoint){
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        query = null;
        query = new RegeocodeQuery(latLonPoint, 50,GeocodeSearch.AMAP);
        return this;
    }

    public PointToAddressManager setSearchListener(GeocodeSearch.OnGeocodeSearchListener listener){
        geocoderSearch.setOnGeocodeSearchListener(listener);
        return this;
    }

    public void search(){
        geocoderSearch.getFromLocationAsyn(query);
    }
}
