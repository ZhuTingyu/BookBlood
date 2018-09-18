package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.CareDrugEntity;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/18 0018.
 */

public class CareDrugModel {


    //hl 保健品记录  添加
    public static Observable<ApiResponse<Object>> getTXGP_PigeonHealth_Add(String footid,
                                                                           String pigeonid,
                                                                           String healthname,
                                                                           String healthtype,
                                                                           String effectInfo,
                                                                           String biteffect,

                                                                           String healthtime,
                                                                           String recordtime,

                                                                           String bodytemper,
                                                                           String weather,
                                                                           String temper,
                                                                           String hum,
                                                                           String dir,
                                                                           String remark
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.drug_care_drug_add)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//

                .addBody("healthname", healthname)//保健品名称
                .addBody("healthtype", healthtype)//保健瓶功效

                .addBody("effectInfo", effectInfo)//有什么效果
                .addBody("biteffect", biteffect)// 是否有副作用

                .addBody("healthtime", healthtime)//使用时间
                .addBody("recordtime", recordtime)//记录时间

                .addBody("bodytemper", bodytemper)//体温

                .addBody("weather", weather)//天气
                .addBody("temper", temper)//气温
                .addBody("hum", hum)//风向
                .addBody("hum", dir)//湿度
                .addBody("remark", remark)//备注
                .request();
    }


    //hl 保健品记录  修改
    public static Observable<ApiResponse<Object>> getTXGP_PigeonHealth_Update(String footid,
                                                                              String pigeonid,
                                                                              String healthid,
                                                                              String healthname,
                                                                              String healthtype,
                                                                              String effectInfo,
                                                                              String biteffect,

                                                                              String healthtime,
                                                                              String recordtime,

                                                                              String bodytemper,
                                                                              String weather,
                                                                              String temper,
                                                                              String hum,
                                                                              String dir,
                                                                              String remark
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.drug_care_drug_modify)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("healthid", healthid)//保健品记录id

                .addBody("healthname", healthname)//保健品名称
                .addBody("healthtype", healthtype)//保健瓶功效

                .addBody("effectInfo", effectInfo)//有什么效果
                .addBody("biteffect", biteffect)// 是否有副作用

                .addBody("healthtime", healthtime)//使用时间
                .addBody("recordtime", recordtime)//记录时间

                .addBody("bodytemper", bodytemper)//体温

                .addBody("weather", weather)//天气
                .addBody("temper", temper)//气温
                .addBody("hum", hum)//风向
                .addBody("hum", dir)//湿度
                .addBody("remark", remark)//备注
                .request();
    }


    //hl 保健品记录  详情
    public static Observable<ApiResponse<CareDrugEntity>> getTXGP_PigeonHealth_Select(String footid,
                                                                                      String pigeonid,
                                                                                      String healthid) {
        return RequestData.<ApiResponse<CareDrugEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<CareDrugEntity>>() {
                }.getType())
                .url(R.string.drug_care_drug_details)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("healthid", healthid)//保健品记录id
                .request();
    }


    //hl 保健品记录  删除
    public static Observable<ApiResponse<Object>> getTXGP_PigeonHealth_Delete(String footid,
                                                                              String pigeonid,
                                                                              String healthid) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.drug_care_drug_del)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("healthid", healthid)//保健品记录id
                .request();
    }


}
