package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/11.
 */

public class PairingModel {


    //hl 添加配對
    public static Observable<ApiResponse<List<BreedPigeonEntity>>> getTXGP_PigeonBreed_Add(String wofootnum,
                                                                                           String menfootnum,
                                                                                           String time,
                                                                                           String weather,
                                                                                           String temper,
                                                                                           String hum,
                                                                                           String dir,
                                                                                           String bitpair,
                                                                                           String reamrk) {
        return RequestData.<ApiResponse<List<BreedPigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<BreedPigeonEntity>>>() {
                }.getType())
                .url(R.string.pigeon_breed_search)
                .addBody("wofootnum", wofootnum)//母足环号码
                .addBody("menfootnum", menfootnum)//父足环号码
                .addBody("time", time)//配对时间
                .addBody("weather", weather)//配对天气
                .addBody("temper", temper)//配对气温
                .addBody("hum", hum)//配对湿度
                .addBody("dir", dir)//配对风向
                .addBody("bitpair", bitpair)//是否是平台配对（1和2）
                .addBody("reamrk", reamrk)//配对备注
                .request();
    }


    //hl 配对信息列表
    public static Observable<ApiResponse<List<PairingInfoEntity>>> getTXGP_PigeonBreed_SelectPigeonAll(String pi,
                                                                                                       String ps,
                                                                                                       String pigeonid,
                                                                                                       String footid,
                                                                                                       String sexid
    ) {
        return RequestData.<ApiResponse<List<PairingInfoEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PairingInfoEntity>>>() {
                }.getType())
                .url(R.string.pairing_info_list)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .addBody("pigeonid", pigeonid)//
                .addBody("footid", footid)//
                .addBody("sexid", sexid)//
                .request();
    }


    //hl 添加窝次信息
    public static Observable<ApiResponse<List<PairingInfoEntity>>> getTXGP_PigeonBreedNest_Add(String pi,
                                                                                               String ps) {
        return RequestData.<ApiResponse<List<PairingInfoEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PairingInfoEntity>>>() {
                }.getType())
                .url(R.string.pairing_nest_info_add)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .request();
    }

}
