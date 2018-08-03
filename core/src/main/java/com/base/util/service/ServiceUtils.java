package com.base.util.service;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务相关工具
 * Created by Administrator on 2017/12/29.
 */

public class ServiceUtils {
    /**
     * 判断服务是否开启
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(100);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 方法描述：判断某一应用是否正在运行
     *
     * @param context     上下文
     * @param packageName 应用的包名
     * @return true 表示正在运行，false表示没有运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}
