package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
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
}
