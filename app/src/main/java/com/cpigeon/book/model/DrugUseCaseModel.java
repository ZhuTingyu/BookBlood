package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.base.http.HttpErrorException;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.DrugUseCaseEntity;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * 用药情况
 * Created by Administrator on 2018/9/17 0017.
 */

public class DrugUseCaseModel {


    //hl 病情记录  添加
    public static Observable<ApiResponse<Object>> getTXGP_PigeonDrug_Add(String footid,
                                                                         String pigeonid,
                                                                         String diseaseid,
                                                                         String drugname,
                                                                         String stateid,
                                                                         String sideeffect,
                                                                         String bodytemper,
                                                                         String diseasetime,
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
                .addBody("diseaseid", diseaseid)//
                .addBody("drugname", drugname)//
                .addBody("stateid", stateid)//
                .addBody("sideeffect", sideeffect)//
                .addBody("bodytemper", bodytemper)//
                .addBody("diseasetime", diseasetime)//
                .addBody("recordtime", recordtime)//

                .addBody("weather", weather)//
                .addBody("temper", temper)//
                .addBody("hum", hum)//
                .addBody("hum", dir)//
                .addBody("remark", remark)//
                .request();
    }


    //hl 获取 病情记录  列表
    public static Observable<ApiResponse<List<DrugUseCaseEntity>>> getTXGP_PigeonDisease_SelectAll(String pi,
                                                                                                   String ps,
                                                                                                   String footid,
                                                                                                   String pigeonid) {
        return RequestData.<ApiResponse<List<DrugUseCaseEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<DrugUseCaseEntity>>>() {
                }.getType())
                .url(R.string.status_illness_record_list)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .request();
    }

}
