package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.DrugUseCaseEntity;
import com.cpigeon.book.model.entity.StatusIllnessRecordEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * 用药情况
 * Created by Administrator on 2018/9/17 0017.
 */

public class DrugUseCaseModel {


    //hl 用药情况  添加
    public static Observable<ApiResponse<Object>> getTXGP_PigeonDrug_Add(String footid,
                                                                         String pigeonid,
                                                                         String diseasename,
                                                                         String drugname,
                                                                         String stateid,
                                                                         String biteffect,
                                                                         String bodytemper,
                                                                         String drugtime,
                                                                         String recordtime,

                                                                         String weather,
                                                                         String temper,
                                                                         String hum,
                                                                         String dir,
                                                                         String remark
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.drug_use_case_add)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("diseasename", diseasename)//疾病名称
                .addBody("drugname", drugname)//药品名称（传入id）
                .addBody("stateid", stateid)//用药后状态
                .addBody("biteffect", biteffect)//是否有副作用
                .addBody("bodytemper", bodytemper)//体温
                .addBody("drugtime", drugtime)// 药瓶使用时间
                .addBody("recordtime", recordtime)//记录时间
                .addBody("weather", weather)//天气
                .addBody("temper", temper)//气温
                .addBody("hum", hum)//
                .addBody("hum", dir)//
                .addBody("remark", remark)//
                .request();
    }


    //hl 用药情况  修改
    public static Observable<ApiResponse<Object>> getTXGP_PigeonDrug_Edit(String footid,
                                                                          String pigeonid,
                                                                          String drugid,
                                                                          String diseasename,
                                                                          String drugname,
                                                                          String stateid,
                                                                          String biteffect,
                                                                          String bodytemper,
                                                                          String drugtime,
                                                                          String recordtime,

                                                                          String weather,
                                                                          String temper,
                                                                          String hum,
                                                                          String dir,
                                                                          String remark
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.drug_use_case_modify)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("drugid", drugid)//
                .addBody("diseasename", diseasename)//
                .addBody("drugname", drugname)//药品名称
                .addBody("stateid", stateid)//用药后状态
                .addBody("biteffect", biteffect)//是否有副作用
                .addBody("bodytemper", bodytemper)//体温
                .addBody("drugtime", drugtime)// 药瓶使用时间
                .addBody("recordtime", recordtime)//记录时间
                .addBody("weather", weather)//天气
                .addBody("temper", temper)//气温
                .addBody("hum", hum)//
                .addBody("hum", dir)//
                .addBody("remark", remark)//
                .request();
    }


    //hl 用药情况  详情
    public static Observable<ApiResponse<DrugUseCaseEntity>> getTXGP_PigeonDrug_Select(String footid,
                                                                                       String pigeonid,
                                                                                       String drugid
    ) {
        return RequestData.<ApiResponse<DrugUseCaseEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<DrugUseCaseEntity>>() {
                }.getType())
                .url(R.string.drug_use_case_details)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("drugid", drugid)//用药记录ID
                .request();
    }


    //hl 用药情况  删除
    public static Observable<ApiResponse<Object>> getTXGP_PigeonDrug_Delete(String footid,
                                                                            String pigeonid,
                                                                            String drugid
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.drug_use_case_del)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("drugid", drugid)//用药记录ID

                .request();
    }


    //hl 获取 病情记录  列表
    public static Observable<ApiResponse<List<StatusIllnessRecordEntity>>> getTXGP_PigeonDisease_SelectAll(String pi,
                                                                                                           String ps,
                                                                                                           String footid,
                                                                                                           String pigeonid) {
        return RequestData.<ApiResponse<List<StatusIllnessRecordEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<StatusIllnessRecordEntity>>>() {
                }.getType())
                .url(R.string.status_illness_record_list)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .request();
    }

}
