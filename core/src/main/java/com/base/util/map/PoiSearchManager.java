package com.base.util.map;


import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;

/**
 * 搜索工具
 * Created by Zhu TingYu on 2018/1/17.
 */

public class PoiSearchManager {

    private static PoiSearch.Query query;
    static PoiSearch poiSearch;
    public static PoiSearchManager build(Context context){
        PoiSearchManager poiSearchManager = new PoiSearchManager();
        query = new PoiSearch.Query("地名", "", "");
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);
        poiSearch = new PoiSearch(context, query);
        return poiSearchManager;
    }

    public PoiSearchManager setSearchListener(PoiSearch.OnPoiSearchListener listener){
        poiSearch.setOnPoiSearchListener(listener);
        return this;
    }

    /**
     * 设置所搜坐标
     * @param lat 纬度
     * @param lot 经度
     * @return
     */

    public PoiSearchManager setBound(double lat, double lot){
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lot), 3000));
        return this;
    }

    public void search(){
        poiSearch.searchPOIAsyn();
    }

}
