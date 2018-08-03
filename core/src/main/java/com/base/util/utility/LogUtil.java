package com.base.util.utility;

import android.text.TextUtils;
import android.util.Log;

import com.base.util.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangwei on 2016/3/19.
 */
public class LogUtil {
    public static void print(Object error) {
        jsonFormatterLog("" + error);
    }

    public static void print(Map<String, String> map){
            Set<String> keys = map.keySet();
            StringBuffer params = new StringBuffer();
            params.append("{" + "\n");
            for (String key : keys) {
                params.append("  body    " + key + ": " + map.get(key) + "\n");
            }
            params.append("}");
            params.toString();
            Log.e("LOG:", params.toString());
    }

    public static void print(List<Map<String, String>> maps){
        StringBuffer params = new StringBuffer();
        params.append("{" + "\n");
        for (Map<String, String> map : maps) {
            Set<String> keys = map.keySet();
            for (String key : keys) {
                params.append("  body    " + key + ": " + map.get(key) + "\n");
            }
        }
        params.append("}");
        Log.e("LOG:", params.toString());
    }

    private static void jsonFormatterLog(String s) {
        if (TextUtils.isEmpty(s)) {
            Log.e("LOG:", "" + s);
            return;
        }
        String json = s;
        String tag = null;
        if (s.indexOf("{") > 0) {
            tag = s.substring(0, s.indexOf("{"));
            json = s.substring(s.indexOf("{"));
        }
        if (s.indexOf("{") > -1) {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(json);
                json = gson.toJson(jsonElement);
                if (!TextUtils.isEmpty(tag)) {
                    json = tag + "\n" + json;
                }
                Log.e("LOG:", "" + json);
                return;
            } catch (Exception e) {
            }
        }
        Log.e("LOG:", "" + s);
    }
}
