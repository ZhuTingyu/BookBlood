package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/9.
 */

public class StatisticalModel {

    //hl 得到各种类型的足环个数
    public static Observable<ApiResponse<Object>> getTXGP_FootRing_SelectType() {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.foot_num_type)
                .request();
    }


    //hl 获取种鸽 （赛鸽） 雌雄数量
    public static Observable<ApiResponse<Object>> getPigeonSexCount(String pigeonType) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_sex_count)
                .addBody("type", pigeonType)//种鸽（赛鸽） 或全部
                .request();
    }

}
