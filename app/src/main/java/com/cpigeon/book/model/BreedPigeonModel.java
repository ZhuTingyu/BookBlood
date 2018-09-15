package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/6.
 */

public class BreedPigeonModel {

    //hl 获取种鸽详细
    public static Observable<ApiResponse<BreedPigeonEntity>> getTXGP_Pigeon_GetInfo(String pigeonid) {
        return RequestData.<ApiResponse<BreedPigeonEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<BreedPigeonEntity>>() {
                }.getType())
                .url(R.string.pigeon_breed_details)
                .addBody("pigeonid", pigeonid)
                .request();
    }


    //hl 种鸽列表，筛选
    public static Observable<ApiResponse<List<BreedPigeonEntity>>> getTXGP_Pigeon_SelectAll(String pi, String ps,
                                                                                            String typeid,
                                                                                            String bloodid,
                                                                                            String sexid,
                                                                                            String year,
                                                                                            String stateid) {
        return RequestData.<ApiResponse<List<BreedPigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<BreedPigeonEntity>>>() {
                }.getType())
                .url(R.string.pigeon_breed_sift)
                .addBody("pi", pi)
                .addBody("ps", ps)
                .addBody("typeid", typeid)
                .addBody("blood", bloodid)
                .addBody("sex", sexid)
                .addBody("year", year)
                .addBody("stateid", stateid)
                .request();
    }




    //hl 添加种鸽
    public static Observable<ApiResponse<PigeonEntryEntity>> getTXGP_Pigeon_Add(String coodid,
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
                                                                                String phototypeid,
                                                                                String sonFootId,
                                                                                String sonPigeonId,
                                                                                Map<String, String> body) {
        return RequestData.<ApiResponse<PigeonEntryEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonEntryEntity>>() {
                }.getType())
                .url(R.string.pigeon_breed_add)
                .addBody("typeid", String.valueOf(8))//
                .addBody("coodid", coodid)// 国家Id
                .addBody("footnum", footnum)//足环（可选可填，传足环号）
                .addBody("footnumto", footnumto)// 副环（可选可填 ，传足环号）
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
                .addBody("phototypeid", phototypeid)// 信鸽状态ID
                .addBody("sonpigeonid", sonPigeonId)// 子类鸽子id
                .addBody("sonfootid", sonFootId)// 子类足环id
                .addImageFileBodys(body)
                .request();
    }

    //hl 种鸽信息修改
    public static Observable<ApiResponse<PigeonEntryEntity>> getTXGP_Pigeon_Modify(String pigeonid,
                                                                                   String coodid,
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
                                                                                   String phototypeid,
                                                                                   Map<String, String> body) {
        return RequestData.<ApiResponse<PigeonEntryEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonEntryEntity>>() {
                }.getType())
                .url(R.string.pigeon_breed_modify)
                .addBody("typeid", String.valueOf(8))//
                .addBody("pigeonid", pigeonid)// 鸽子id
                .addBody("coodid", coodid)// 国家Id
                .addBody("footnum", footnum)//足环（可选可填，传足环号）
                .addBody("footnumto", footnumto)// 副环（可选可填 ，传足环号）
                .addBody("sourceid", sourceid)// 信鸽来源ID
                .addBody("menfootnum", menfootnum)// 母足环号码
                .addBody("wofootnum", wofootnum)// 父足环号码
                .addBody("name", name)// 信鸽名称
                .addBody("sex", sex)//  性别（传ID）
                .addBody("plume", plume)//  羽色（可选可填，传羽色名称）
                .addBody("eye", eye)//  眼沙（传ID）
                .addBody("outtime", outtime)//   出壳时间
                .addBody("blood", blood)//  血统 （可选可填，传血统名称）
                .addBody("stateid", stateid)// 信鸽状态ID
                .addBody("phototypeid", phototypeid)// 图片类型
                .addImageFileBodys(body)
                .request();
    }


    //hl 种鸽(赛鸽)列表，搜索
    public static Observable<ApiResponse<List<BreedPigeonEntity>>> getTXGP_Pigeon_SearchBreed(String pi, String ps, String footnum,String typeid) {
        return RequestData.<ApiResponse<List<BreedPigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<BreedPigeonEntity>>>() {
                }.getType())
//                .url(R.string.pigeon_breed_search)
                .url(R.string.pigeon_breed_sift)
                .addBody("pi", pi)
                .addBody("ps", ps)
                .addBody("footnum", footnum)
                .addBody("typeid", typeid)
                .request();
    }
}
