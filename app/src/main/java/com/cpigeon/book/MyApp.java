package com.cpigeon.book;

import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.base.application.BaseApplication;
import com.facebook.stetho.Stetho;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

/**
 * Created by Zhu TingYu on 2018/1/5.
 */

public class MyApp extends BaseApplication {

    public static int screenWidth;
    public static int screenHeight;
    public boolean isOutNet = false;

    private static MyApp myApp;

    public static BaseApplication getAppContext() {
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        PlatformConfig.setWeixin(getString(R.string.weixin_appid), "f154ca061506224c4f72115c71ae05d7");
        PlatformConfig.setQQZone("1105989530", "ztLtwrRKr7YiDLly");

        MultiDex.install(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this.getApplicationContext())
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this.getApplicationContext()))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this.getApplicationContext()))
                        .build());

        UMShareAPI.get(this);//友盟初始化

        Config.DEBUG = true;

        //bugly
//        CrashReport.initCrashReport(getApplicationContext(), "4d1c4ee910", false);

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);


        DisplayMetrics mDisplayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();
        screenWidth = mDisplayMetrics.widthPixels;
        screenHeight = mDisplayMetrics.heightPixels;
    }

    public static MyApp getMyApp() {
        return myApp;
    }

    public void setOutNet(boolean outNet) {
        isOutNet = outNet;
    }

    public boolean isOutNet() {
        return isOutNet;
    }
}
