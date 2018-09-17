package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.FeedPigeonStatistical;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class UseVaccineModel {


    //hl 养鸽记录 统计
    public static Observable<ApiResponse<Object>> getTXGP_PigeonVaccine_Add(String footid,
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
                .url(R.string.use_vaccine_add)
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
}
