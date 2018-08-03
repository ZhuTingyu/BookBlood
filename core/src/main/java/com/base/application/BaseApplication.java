package com.base.application;

import android.app.Application;
import android.support.multidex.MultiDex;


/**
 * Created by wangwei on 2016/4/14.
 */
public abstract class BaseApplication extends Application {
    private static BaseApplication httpApplication;

    public static BaseApplication getAppContext() {
        return httpApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.httpApplication = this;
        MultiDex.install(this);//压缩包名工具初始
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    /*public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }*/
}
