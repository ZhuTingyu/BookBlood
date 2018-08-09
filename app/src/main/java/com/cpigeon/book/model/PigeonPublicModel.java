package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/9.
 */

public class PigeonPublicModel {

    //hl 获取鸽子性别
    public static Observable<ApiResponse<Object>> getTXGP_PigeonSexType_Select() {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_sex_select)
                .request();
    }


    //hl 获取鸽子选择  血统
    public static Observable<ApiResponse<Object>> getTXGP_PigeonBloodType_Select() {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_lineage_select)
                .request();
    }


    //hl 获取鸽子选择  眼砂
    public static Observable<ApiResponse<Object>> getTXGP_PigeonEyeType_Select() {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_eye_sand_select)
                .request();
    }

    //hl 获取鸽子选择  羽色
    public static Observable<ApiResponse<Object>> getTXGP_PigeonPlumeType_Select() {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_feather_color_select)
                .request();
    }
}
