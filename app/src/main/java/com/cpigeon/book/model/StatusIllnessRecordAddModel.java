package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.StatusIllnessRecordEntity;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class StatusIllnessRecordAddModel {


    //hl 病情记录  添加
    public static Observable<ApiResponse<Object>> getTXGP_PigeonDisease_Add(String footid,
                                                                            String pigeonid,
                                                                            String name,
                                                                            String diseaseinfo,
                                                                            String weather,
                                                                            String temper,
                                                                            String bodytemper,
                                                                            String diseasetime,
                                                                            String hum,
                                                                            String dir,
                                                                            String remark
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.status_illness_record_add)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("name", name)//
                .addBody("diseaseinfo", diseaseinfo)//
                .addBody("weather", weather)//
                .addBody("temper", temper)//
                .addBody("bodytemper", bodytemper)//
                .addBody("diseasetime", diseasetime)//
                .addBody("hum", hum)//
                .addBody("hum", dir)//
                .addBody("remark", remark)//
                .request();
    }

    //hl 病情记录  修改
    public static Observable<ApiResponse<Object>> getTXGP_PigeonDisease_Edit(String footid,
                                                                            String pigeonid,
                                                                             String diseaseid,
                                                                             String name,
                                                                            String diseaseinfo,
                                                                            String weather,
                                                                            String temper,
                                                                            String bodytemper,
                                                                            String diseasetime,
                                                                            String hum,
                                                                            String dir,
                                                                            String remark
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.status_illness_record_modify)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("diseaseid", diseaseid)//
                .addBody("name", name)//
                .addBody("diseaseinfo", diseaseinfo)//
                .addBody("weather", weather)//
                .addBody("temper", temper)//
                .addBody("bodytemper", bodytemper)//
                .addBody("diseasetime", diseasetime)//
                .addBody("hum", hum)//
                .addBody("hum", dir)//
                .addBody("remark", remark)//
                .request();
    }


    //hl 病情记录  详情
    public static Observable<ApiResponse<StatusIllnessRecordEntity>> getTXGP_PigeonDisease_Select(String footid,
                                                                                                  String pigeonid,
                                                                                                  String diseaseid

    ) {
        return RequestData.<ApiResponse<StatusIllnessRecordEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<StatusIllnessRecordEntity>>() {
                }.getType())
                .url(R.string.status_illness_record_details)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("diseaseid", diseaseid)//病情记录id
                .request();
    }

    //hl 病情记录  删除
    public static Observable<ApiResponse<Object>> getTXGP_Delete_PigeonDisease(String footid,
                                                                               String pigeonid,
                                                                               String diseaseid
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.status_illness_record_del)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("diseaseid", diseaseid)//
                .request();
    }

}
