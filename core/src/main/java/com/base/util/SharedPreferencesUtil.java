package com.base.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    public static final String CONFIG_FILE = "Config_file";
    public static final String GUIDE_FILE = "GUIDE_FILE";
    public static final String IS_OUT_NET = "IS_OUT_NET";

    public static final String SP_FILE_APPSETTING = "apcpSetting";// 应用配置
    public static final String SP_FILE_APPSTATE = "appState";// 应用状态

    /**
     * 读取数据 SharedPreferences
     *
     * @param context
     * @param configname
     * @param name
     * @return
     */
    public static String get(Context context, String configname, String name) {
        try {
            SharedPreferences userInfo = context.getSharedPreferences(configname, 0);
            String check = userInfo.getString(name, "");
            return check;
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 保存数据到 SharedPreferences
     *
     * @param context
     * @param configname
     * @param name
     * @param data
     */
    public static void set(Context context, String configname, String name, String data) {
        try {
            SharedPreferences userInfo = context.getSharedPreferences(configname, 0);
            userInfo.edit().putString(name, data).commit();
        } catch (Exception e) {
        }
    }

    /**
     * 读取数据 SharedPreferences
     *
     * @param context
     * @param configname
     * @param name
     * @return
     */
    public static boolean getBoolean(Context context, String configname, String name, boolean value) {
        try {
            SharedPreferences userInfo = context.getSharedPreferences(configname, 0);
            boolean check = userInfo.getBoolean(name, value);
            return check;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 保存数据到 SharedPreferences
     *
     * @param context
     * @param configname
     * @param name
     * @param data
     */
    public static void setBoolean(Context context, String configname, String name, boolean data) {
        try {
            SharedPreferences userInfo = context.getSharedPreferences(configname, 0);
            userInfo.edit().putBoolean(name, data).commit();
        } catch (Exception e) {
        }
    }

    /**s
     * 删除 SharedPreferences 的文件
     * @param context
     * @param filename
     */

    public static void deleteFile(Context context, String filename){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
