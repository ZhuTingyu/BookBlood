package com.base.util.gps;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by Administrator on 2018/5/5.
 */

public class GpsUtil {

    /**
     * 判断GPS是否打开
     * ACCESS_FINE_LOCATION权限
     *
     * @param context
     * @return
     */
    public static boolean isGPSEnabled(Context context) {
        //获取手机所有连接LOCATION_SERVICE对象
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context
                .LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
