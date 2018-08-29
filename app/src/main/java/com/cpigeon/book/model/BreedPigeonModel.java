package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/6.
 */

public class BreedPigeonModel {

    //hl 获取种鸽详细
    public static Observable<ApiResponse<Object>> getTXGP_Pigeon_GetInfo(String footId) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_breed_details)
                .addBody("pigeonid", footId)
                .request();
    }


    //hl 添加种鸽
    public static Observable<ApiResponse<Object>> getTXGP_Pigeon_Add(String coodid,
                                                                     String footnum,
                                                                     String footnumto,
                                                                     String sourceid,
                                                                     String menfootnum,
                                                                     String wofootnum,
                                                                     String name,
                                                                     String sex,
                                                                     String plume,
                                                                     String eye,
                                                                     String outtime,
                                                                     String blood,
                                                                     String stateid,
                                                                     Map<String, String> body) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_breed_add)
                .addBody("typeid  ", String.valueOf(8))//
                .addBody("coodid  ", coodid)// 国家Id
                .addBody("footnum ", footnum)//足环（可选可填，传足环号）
                .addBody("footnumto ", footnumto)// 副环（可选可填 ，传足环号）
                .addBody("sourceid", sourceid)// 信鸽来源ID
                .addBody("menfootnum", menfootnum)// 父足环号码
                .addBody("wofootnum", wofootnum)// 母足环号码
                .addBody("name", name)// 信鸽名称
                .addBody("sex", sex)//  性别（传ID）
                .addBody("plume", plume)//  羽色（可选可填，传羽色名称）
                .addBody("eye", eye)//  眼沙（传ID）
                .addBody("outtime", outtime)//   出壳时间
                .addBody("blood", blood)//  血统 （可选可填，传血统名称）
                .addBody("stateid", stateid)// 信鸽状态ID
                .addImageFileBodys(body)
                .request();
    }
}
