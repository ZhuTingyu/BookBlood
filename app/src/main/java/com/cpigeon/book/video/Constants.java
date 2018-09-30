package com.cpigeon.book.video;


import android.os.Environment;

import com.cpigeon.book.MyApp;

import java.io.File;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class Constants {

    public static String getBaseFolder() {
        String baseFolder = Environment.getExternalStorageDirectory() + "/Codec/";
        File f = new File(baseFolder);
        if (!f.exists()) {
            boolean b = f.mkdirs();
            if (!b) {
                baseFolder = MyApp.getAppContext().getExternalFilesDir(null).getAbsolutePath() + "/";
            }
        }
        return baseFolder;
    }

    //获取VideoPath
    public static String getPath(String path, String fileName) {
        String p = getBaseFolder() + path;
        File f = new File(p);
        if (!f.exists() && !f.mkdirs()) {
            return getBaseFolder() + fileName;
        }
        return p + fileName;
    }
}
