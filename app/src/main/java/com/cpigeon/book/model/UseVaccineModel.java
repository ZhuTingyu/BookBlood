package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.FeedPigeonStatistical;
import com.cpigeon.book.model.entity.UseVaccineEntity;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class UseVaccineModel {


    //hl 疫苗注射  添加
    public static Observable<ApiResponse<Object>> getTXGP_PigeonVaccine_Add(String footid,
                                                                            String pigeonid,
                                                                            String viccinename,
                                                                            String bodytemper,
                                                                            String viccinetime,
                                                                            String reason,

                                                                            String weather,
                                                                            String temper,
                                                                            String hum,
                                                                            String dir,
                                                                            String remark
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.use_vaccine_add)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("viccinename", viccinename)//
                .addBody("bodytemper", bodytemper)//
                .addBody("viccinetime", viccinetime)//
                .addBody("reason", reason)//

                .addBody("weather", weather)//天气
                .addBody("temper", temper)//气温
                .addBody("hum", hum)//风向
                .addBody("hum", dir)//湿度
                .addBody("remark", remark)//备注
                .request();
    }


    //hl 疫苗注射  修改
    public static Observable<ApiResponse<Object>> getTXGP_PigeonVaccine_Update(String vaccineid,
                                                                               String footid,
                                                                               String pigeonid,
                                                                               String viccinename,
                                                                               String weather,
                                                                               String temper,
                                                                               String bodytemper,
                                                                               String viccinetime,
                                                                               String reason,
                                                                               String remark
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.use_vaccine_modify)
                .addBody("vaccineid", vaccineid)//
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("viccinename", viccinename)//
                .addBody("weather", weather)//
                .addBody("temper", temper)//
                .addBody("bodytemper", bodytemper)//
                .addBody("viccinetime", viccinetime)//
                .addBody("reason", reason)//
                .addBody("remark", remark)//
                .request();
    }


    //hl 疫苗注射  详情
    public static Observable<ApiResponse<UseVaccineEntity>> getTXGP_PigeonVaccine_Select(String footid,
                                                                                         String pigeonid,
                                                                                         String vaccineid) {
        return RequestData.<ApiResponse<UseVaccineEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<UseVaccineEntity>>() {
                }.getType())
                .url(R.string.use_vaccine_details)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("vaccineid", vaccineid)//疫苗记录ID
                .request();
    }

    //hl 疫苗注射  删除
    public static Observable<ApiResponse<UseVaccineEntity>> getTXGP_PigeonVaccine_Delete(String footid,
                                                                                         String pigeonid,
                                                                                         String vaccineid) {
        return RequestData.<ApiResponse<UseVaccineEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<UseVaccineEntity>>() {
                }.getType())
                .url(R.string.use_vaccine_del)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .addBody("vaccineid", vaccineid)//疫苗记录ID
                .request();
    }
}
