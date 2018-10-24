package com.base.util.map;

import android.content.Context;

import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;

/**
 * 地区搜索工具
 * Created by Zhu TingYu on 2018/3/31.
 */

public class DistrictSearchManager {
    static DistrictSearch search;
    static DistrictSearchManager districtSearchManager;
    public static DistrictSearchManager build(Context context){
         districtSearchManager = new DistrictSearchManager();
        search = new DistrictSearch(context);
        return districtSearchManager;
    }

    public DistrictSearchManager setSearchListener(DistrictSearch.OnDistrictSearchListener listener){
        search.setOnDistrictSearchListener(listener);
        return this;
    }

    public DistrictSearchManager keyword(String keyword){
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords(keyword);//传入关键字
        query.setShowBoundary(true);//是否返回边界值
        search.setQuery(query);
        return this;
    }

    public void search(){
        search.searchDistrictAsyn();
    }
}
