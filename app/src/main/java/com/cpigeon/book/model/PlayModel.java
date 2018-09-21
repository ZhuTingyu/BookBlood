package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.PigeonPlayEntity;
import com.cpigeon.book.model.entity.PlayAdditionalInfoEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * 赛绩
 * Created by Administrator on 2018/9/4.
 */

public class PlayModel {


    //hl 赛绩录入
    public static Observable<ApiResponse<Object>> getTXGP_PigeonMatch_Add(String pigeonid,
                                                                          String footid,

                                                                          String isocname,
                                                                          String name,
                                                                          String count,
                                                                          String number,
                                                                          String adds,
                                                                          String interval,
                                                                          String time,
                                                                          String remark
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_play_entry)
                .addBody("pigeonid", pigeonid)//鸽子id
                .addBody("footid", footid)//足环ID
                .addBody("isocname", isocname)//赛事组织（传赛事组织名称）
                .addBody("name", name)//比赛名称
                .addBody("count", count)//比赛规模
                .addBody("number", number)//比赛排名
                .addBody("adds", adds)//比赛地点
                .addBody("interval", interval)//比赛空距
                .addBody("time", time)//比赛时间
                .addBody("remark", remark)//备注
                .request();
    }

    //hl 标准赛绩修改
    public static Observable<ApiResponse<Object>> getTXGP_PigeonMatch_Modify(String matchid,
                                                                             String pigeonid,
                                                                             String footid,
                                                                             String isocname,
                                                                             String name,
                                                                             String count,
                                                                             String number,
                                                                             String adds,
                                                                             String interval,
                                                                             String time,
                                                                             String remark
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_play_modify)
                .addBody("matchid", matchid)//赛绩id
                .addBody("pigeonid", pigeonid)//鸽子id
                .addBody("footid", footid)//足环ID
                .addBody("isocname", isocname)//赛事组织（传赛事组织名称）
                .addBody("name", name)//比赛名称
                .addBody("count", count)//比赛规模
                .addBody("number", number)//比赛排名
                .addBody("adds", adds)//比赛地点
                .addBody("interval", interval)//比赛空距
                .addBody("time", time)//比赛时间
                .addBody("remark", remark)//备注
                .request();
    }


    //标准赛绩列表
    public static Observable<ApiResponse<List<PigeonPlayEntity>>> getTXGP_PigeonMatch_SelectAll(String puid,
                                                                                                String pigeonid,
                                                                                                String footid,
                                                                                                String pi,
                                                                                                String ps) {
        return RequestData.<ApiResponse<List<PigeonPlayEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonPlayEntity>>>() {
                }.getType())
                .url(R.string.pigeon_play_list)
                .addBody("puid", puid)
                .addBody("pigeonid", pigeonid)
                .addBody("footid", footid)
                .addBody("pi", pi)
                .addBody("ps", ps)
                .request();
    }


    //标准赛绩 删除
    public static Observable<ApiResponse<Object>> getTXGP_PigeonMatch_Delete(String pigeonid,
                                                                             String footid,
                                                                             String matchid) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_play_del)
                .addBody("pigeonid", pigeonid)
                .addBody("footid", footid)
                .addBody("matchid", matchid)
                .request();
    }

    //标准赛绩 详情
    public static Observable<ApiResponse<PigeonPlayEntity>> getTXGP_PigeonMatch_Select(String pigeonid,
                                                                                       String footid,
                                                                                       String matchid) {
        return RequestData.<ApiResponse<PigeonPlayEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonPlayEntity>>() {
                }.getType())
                .url(R.string.pigeon_play_details)
                .addBody("pigeonid", pigeonid)
                .addBody("footid", footid)
                .addBody("matchid", matchid)
                .request();
    }


    //添加赛绩附加信息
    public static Observable<ApiResponse<Object>> getTXGP_PigeonInfoList_AddInfo(String pigeonid,
                                                                                 String footid,
                                                                                 String info) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_play_additional_info_add)
                .addBody("pigeonid", pigeonid)
                .addBody("footid", footid)
                .addBody("info", info)
                .request();
    }

    //修改赛绩附加信息
    public static Observable<ApiResponse<Object>> getTXGP_PigeonInfoList_ModifyInfo(String infoid,
                                                                                    String pigeonid,
                                                                                    String footid,
                                                                                    String info) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_play_additional_info_modify)
                .addBody("infoid", infoid)
                .addBody("pigeonid", pigeonid)
                .addBody("footid", footid)
                .addBody("info", info)
                .request();
    }


    //删除赛绩附加信息
    public static Observable<ApiResponse<Object>> getTXGP_PigeonInfoList_Delete(String pigeonid,
                                                                                String footid,
                                                                                String infoid) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_play_additional_info_del)
                .addBody("pigeonid", pigeonid)
                .addBody("footid", footid)
                .addBody("infoid", infoid)
                .request();
    }


    //赛绩附加信息列表
    public static Observable<ApiResponse<List<PlayAdditionalInfoEntity>>> getTXGP_PigeonInfoList_SelectAll(String puid,
                                                                                                           String pigeonid,
                                                                                                           String footid,
                                                                                                           String pi,
                                                                                                           String ps) {
        return RequestData.<ApiResponse<List<PlayAdditionalInfoEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PlayAdditionalInfoEntity>>>() {
                }.getType())
                .url(R.string.pigeon_play_additional_info_list)
                .addBody("puid", puid)
                .addBody("pigeonid", pigeonid)
                .addBody("footid", footid)
                .addBody("pi", pi)
                .addBody("ps", ps)
                .request();
    }
}
