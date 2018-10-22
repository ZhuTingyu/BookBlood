package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.FootRingStateEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonSexCountEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/6.
 */

public class BreedPigeonModel {

    //hl 获取 鸽子  详情
    public static Observable<ApiResponse<PigeonEntity>> getTXGP_Pigeon_GetInfo(String pigeonid, String puid) {
        return RequestData.<ApiResponse<PigeonEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonEntity>>() {
                }.getType())
                .url(R.string.pigeon_breed_details)
                .addBody("pigeonid", pigeonid)
                .addBody("puid", puid)
                .request();
    }


    //hl 种鸽列表，筛选
    public static Observable<ApiResponse<List<PigeonEntity>>> getTXGP_Pigeon_SelectAll(String pi, String ps,
                                                                                       String typeid,
                                                                                       String bloodid,
                                                                                       String sexid,
                                                                                       String year,
                                                                                       String stateid,
                                                                                       String bitmatch,
                                                                                       String bitbreed,//是否有父母（1存在，2.不存在，其他全查）
                                                                                       String pigeonidStr,// ：在列表中排除的鸽子
                                                                                       String bitshare,// ：是否是在共享厅（1：存在，2，不存在，其他全查）
                                                                                       String bitMotto,
                                                                                       String footNumber//搜索的足环号
    ) {
        return RequestData.<ApiResponse<List<PigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonEntity>>>() {
                }.getType())
                .url(R.string.pigeon_breed_sift)
                .addBody("pi", pi)
                .addBody("ps", ps)
                .addBody("typeid", typeid)
                .addBody("bloodid", bloodid)
                .addBody("sexid", sexid)
                .addBody("year", year)
                .addBody("stateid", stateid)
                .addBody("bitmatch", bitmatch)
                .addBody("bitbreed", bitbreed)
                .addBody("pigeonidstr", pigeonidStr)
                .addBody("bitshare", bitshare)
                .addBody("bitmotto", bitMotto)
                .addBody("footnum", footNumber)
                .request();
    }


    //hl 添加鸽子
    public static Observable<ApiResponse<PigeonEntity>> getTXGP_Pigeon_Add(
            String pigeonType,
            String coodid,
            String footnum,
            String footnumto,
            String sourceid,

            String manFootId,
            String manPigeonId,// ：父鸽子id
            String manFootnum,
            String manPigeonStateId,

            String womenFootId,
            String womenPigeonId,// ：母鸽子id
            String womenFootnum,
            String womenPigeonStateId,

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
            String setFootTime,
            Map<String, String> body) {
        return RequestData.<ApiResponse<PigeonEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonEntity>>() {
                }.getType())
                .url(R.string.pigeon_breed_add)
                .addBody("typeid", pigeonType)//
                .addBody("coodid", coodid)// 国家Id
                .addBody("footnum", footnum)//足环（可选可填，传足环号）
                .addBody("footnumto", footnumto)// 副环（可选可填 ，传足环号）
                .addBody("sourceid", sourceid)// 信鸽来源ID

                .addBody("menfootid", manFootId)// 父足环id
                .addBody("menfootnum", manFootnum)// 父足环号码
                .addBody("menpigeonid", manPigeonId)// 父鸽子id
                .addBody("menfootstate", manPigeonStateId)// 父鸽子状态

                .addBody("wofootid", womenFootId)// 母足环id
                .addBody("wofootnum", womenFootnum)// 母足环号码
                .addBody("wopigeonid", womenPigeonId)// 母鸽子id
                .addBody("wofootstate", womenPigeonStateId)// 母鸽子状态

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
                .addBody("foottim", setFootTime)// 足环挂环日期
                .addImageFileBodys(body)
                .request();
    }

    //hl 种鸽信息修改
    public static Observable<ApiResponse<PigeonEntity>> getTXGP_Pigeon_Modify(
            String pigeonid,
            String coodid,
            String footnum,
            String footnumto,
            String sourceid,
            String manfootnum,
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
        return RequestData.<ApiResponse<PigeonEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonEntity>>() {
                }.getType())
                .url(R.string.pigeon_breed_modify)
                .addBody("typeid", PigeonEntity.ID_BREED_PIGEON)//
                .addBody("pigeonid", pigeonid)// 鸽子id
                .addBody("coodid", coodid)// 国家Id
                .addBody("footnum", footnum)//足环（可选可填，传足环号）
                .addBody("footnumto", footnumto)// 副环（可选可填 ，传足环号）
                .addBody("sourceid", sourceid)// 信鸽来源ID
                .addBody("menfootnum", manfootnum)// 父足环号码
                .addBody("wofootnum", wofootnum)// 母足环号码
                .addBody("name", name)// 信鸽名称
                .addBody("sex", sex)//  性别（传ID）
                .addBody("plume", plume)//  羽色（可选可填，传羽色名称）
                .addBody("eye", eye)//  眼沙（传ID）
                .addBody("outtime", outtime)//   出壳时间
                .addBody("blood", blood)//  血统 （可选可填，传血统名称）
                .addBody("stateid", stateid)// 信鸽状态ID
//                .addBody("phototypeid", phototypeid)// 图片类型
//                .addImageFileBodys(body)
                .request();
    }

    //hl 赛鸽信息修改
    public static Observable<ApiResponse<PigeonEntity>> getTXGP_Racing_Pigeon_Modify(String pigeonid,
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
        return RequestData.<ApiResponse<PigeonEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonEntity>>() {
                }.getType())
                .url(R.string.pigeon_racing_modify)
                .addBody("typeid", PigeonEntity.ID_MATCH_PIGEON)//
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
//                .addBody("phototypeid", phototypeid)// 图片类型
//                .addImageFileBodys(body)
                .request();
    }


    //hl 种鸽(赛鸽)列表，搜索
    public static Observable<ApiResponse<List<PigeonEntity>>> getTXGP_Pigeon_SearchBreed(
            String pi,
            String ps,
            String footnum,
            String typeid,
            String bitmatch

    ) {
        return RequestData.<ApiResponse<List<PigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonEntity>>>() {
                }.getType())
//                .url(R.string.pigeon_breed_search)
                .url(R.string.pigeon_breed_sift)
                .addBody("pi", pi)
                .addBody("ps", ps)
                .addBody("footnum", footnum)
                .addBody("typeid", typeid)
                .addBody("bitmatch", bitmatch)
                .request();
    }

    public static Observable<ApiResponse<PigeonSexCountEntity>> getPigeonSexCount(String typeId) {
        return RequestData.<ApiResponse<PigeonSexCountEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonSexCountEntity>>() {
                }.getType())
                .url(R.string.get_pigeon_sex_count)
                .addBody("typeid", typeId)
                .request();
    }

    public static Observable<ApiResponse> applyAddShareHall(String pigeonId, String footId) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.add_apply_share_hall)
                .addBody("pigeonid", pigeonId)
                .addBody("footid", footId)
                .request();
    }

    public static Observable<ApiResponse> cancelAddShareHall(String pigeonId, String footId) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.cancel_share_hall)
                .addBody("pigeonid", pigeonId)
                .addBody("footid", footId)
                .request();
    }

    public static Observable<ApiResponse<FootRingStateEntity>> getFootRingState(String footNumber) {
        return RequestData.<ApiResponse<FootRingStateEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<FootRingStateEntity>>() {
                }.getType())
                .url(R.string.get_foot_ring_state)
                .addBody("footnum", footNumber)
                .request();
    }

    public static Observable<ApiResponse<List<PigeonEntity>>> getPigeonByFootNumber(String footNumber, String sexId) {
        return RequestData.<ApiResponse<List<PigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonEntity>>>() {
                }.getType())
                .url(R.string.get_pigeon_by_footNumber_and_sex)
                .addBody("footnum", footNumber)
                .addBody("sexid", sexId)
                .request();
    }

}
