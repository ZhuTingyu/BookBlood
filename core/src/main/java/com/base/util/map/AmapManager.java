package com.base.util.map;


import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.alibaba.idst.nls.internal.protocol.Content;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;

import java.util.List;

/**
 * 高德地图操作管理工具
 * Created by Zhu TingYu on 2018/3/30.
 */

public class AmapManager {

    public MutableLiveData<LatLng> mMoveEndLiveData = new MutableLiveData<>();
    public MutableLiveData<LatLng> mInMoveLiveData = new MutableLiveData<>();
    private UiSettings mUiSettings;//定义一个UiSettings对象
    AMap aMap;
    public AmapManager(AMap aMap){
        this.aMap = aMap;
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象

    }

    public void moveByLatLng(double la, double lo){
        aMap.animateCamera(CameraUpdateFactory.changeLatLng(new LatLng(la, lo)));
    }

    public void moveByLatLng(LatLng latLng){
        aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng));
    }

    /**
     * 设置地图放大级别
     * @param level
     */

    public void setMapZoomLevel(float level){
        aMap.moveCamera(CameraUpdateFactory.zoomTo(level));
    }

    /**
     * 获取地图放大级别
     * @return
     */

    public float getMapZoomLevel(){
        return aMap.getCameraPosition().zoom;
    }

    public void setMoveCenterListener(){
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mInMoveLiveData.setValue(cameraPosition.target);
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                mMoveEndLiveData.setValue(cameraPosition.target);
            }
        });
    }

    public static LatLng converter(Context context, LatLng sourceLatLng){

        CoordinateConverter converter  = new CoordinateConverter(context);

        converter.from(CoordinateConverter.CoordType.GPS);

        converter.coord(sourceLatLng);

        return converter.convert();
    }

    public void setZoomControlsVisible(boolean isShow){
        mUiSettings.setZoomControlsEnabled(isShow);
    }

}
