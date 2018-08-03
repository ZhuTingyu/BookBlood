package com.cpigeon.book;

import com.base.application.BaseApplication;
import com.facebook.stetho.Stetho;

/**
 * Created by Zhu TingYu on 2018/1/5.
 */

public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this.getApplicationContext())
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this.getApplicationContext()))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this.getApplicationContext()))
                        .build());
    }
}
