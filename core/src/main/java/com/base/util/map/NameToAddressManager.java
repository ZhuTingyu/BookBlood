package com.base.util.map;

import android.content.Context;

import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeSearch;

/**
 * Created by Zhu TingYu on 2018/8/17.
 */

public class NameToAddressManager {

    GeocodeSearch geocoderSearch;
    GeocodeQuery mGeocodeQuery;

    public NameToAddressManager(Context context){
        geocoderSearch = new GeocodeSearch(context);
    }

    public NameToAddressManager setName(String name, String city){
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        mGeocodeQuery = null;
        mGeocodeQuery = new GeocodeQuery(name, city);
        return this;
    }
    public NameToAddressManager setSearchListener(GeocodeSearch.OnGeocodeSearchListener listener){
        geocoderSearch.setOnGeocodeSearchListener(listener);
        return this;
    }

    public void search(){
        geocoderSearch.getFromLocationNameAsyn(mGeocodeQuery);
    }

}
