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


    {
        PlatformConfig.setWeixin("wx86455b1f11ad03d0", "f154ca061506224c4f72115c71ae05d7");
        PlatformConfig.setQQZone("1105989530", "ztLtwrRKr7YiDLly");
    }


    public static int screenWidth;
    public static int screenHeight;


    @Override
    public void onCreate() {
        super.onCreate();

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
}
