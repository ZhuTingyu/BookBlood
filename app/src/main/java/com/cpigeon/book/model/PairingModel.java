package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;
import com.cpigeon.book.model.entity.PriringRecommendEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/11.
 */

public class PairingModel {


    //hl 添加配對
    public static Observable<ApiResponse<List<PigeonEntity>>> getTXGP_PigeonBreed_Add(String footid,
                                                                                      String footnum,
                                                                                      String blood,
                                                                                      String plume,
                                                                                      String sex,
                                                                                      String time,
                                                                                      String weather,
                                                                                      String temper,
                                                                                      String hum,
                                                                                      String dir,
                                                                                      String bitpair,
                                                                                      String reamrk) {
        return RequestData.<ApiResponse<List<PigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonEntity>>>() {
                }.getType())
                .url(R.string.pairing_info_add)
                .addBody("footid", footid)//要配对的足环id
                .addBody("footnum", footnum)//配对的足环号码
                .addBody("blood", blood)//配对的血统
                .addBody("plume", plume)//配对的羽色
                .addBody("sex", sex)//要配对的性别
                .addBody("time", time)//配对时间
                .addBody("weather", weather)//配对天气
                .addBody("temper", temper)//配对气温
                .addBody("hum", hum)//配对湿度
                .addBody("dir", dir)//配对风向
                .addBody("bitpair", bitpair)//是否是平台配对（1和2） 是否相亲配对
                .addBody("reamrk", reamrk)//配对备注
                .request();
    }


    //hl 配对信息列表
    public static Observable<ApiResponse<List<PairingInfoEntity>>> getTXGP_PigeonBreed_SelectPigeonAll(String pi,
                                                                                                       String ps,
                                                                                                       String pigeonid,
                                                                                                       String footid,
                                                                                                       String sexid) {
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

    //hl 获取  单个繁育表里的窝次信息 列表
    public static Observable<ApiResponse<List<PairingNestInfoEntity>>> getTXGP_PigeonBreed_SelectIDAll(String pi,
                                                                                                       String ps,
                                                                                                       String breedid) {
        return RequestData.<ApiResponse<List<PairingNestInfoEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PairingNestInfoEntity>>>() {
                }.getType())
                .url(R.string.pairing_nest_info_list)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .addBody("breedid", breedid)//
                .request();
    }

    //hl 窝次信息  添加
    public static Observable<ApiResponse<List<PairingInfoEntity>>> getTXGP_PigeonBreedNest_Add(String breedid,
                                                                                               String time,
                                                                                               String eggtime,
                                                                                               String igg,
                                                                                               String fgg,
                                                                                               String eggweather,
                                                                                               String eggtemper,
                                                                                               String egghum,
                                                                                               String eggdir,
                                                                                               String outtime,
                                                                                               String outcount,

                                                                                               String pigeonidstr,
                                                                                               String footidstr,

                                                                                               String outweather,
                                                                                               String outtemper,
                                                                                               String outhum,
                                                                                               String outdir,
                                                                                               String reamrk) {
        return RequestData.<ApiResponse<List<PairingInfoEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PairingInfoEntity>>>() {
                }.getType())
                .url(R.string.pairing_nest_info_add)
                .addBody("breedid", breedid)//
                .addBody("time", time)//配对时间
                .addBody("eggtime", eggtime)//
                .addBody("igg", igg)//
                .addBody("fgg", fgg)//
                .addBody("eggweather", eggweather)//
                .addBody("eggtemper", eggtemper)//
                .addBody("egghum", egghum)//
                .addBody("eggdir", eggdir)//
                .addBody("outtime", outtime)//
                .addBody("outcount", outcount)//

                .addBody("pigeonidstr", pigeonidstr)//
                .addBody("footidstr", footidstr)//

                .addBody("outweather", outweather)//
                .addBody("outtemper", outtemper)//
                .addBody("outhum", outhum)//
                .addBody("outdir", outdir)//
                .addBody("reamrk", reamrk)//
                .request();
    }

    //hl 窝次信息  修改
    public static Observable<ApiResponse<List<PairingInfoEntity>>> getTXGP_PigeonBreedNest_Update(String nestid,
                                                                                                  String breedid,
                                                                                                  String time,
                                                                                                  String eggtime,
                                                                                                  String igg,
                                                                                                  String fgg,
                                                                                                  String eggweather,
                                                                                                  String eggtemper,
                                                                                                  String egghum,
                                                                                                  String eggdir,
                                                                                                  String outtime,
                                                                                                  String outcount,

                                                                                                  String pigeonidstr,
                                                                                                  String footidstr,

                                                                                                  String outweather,
                                                                                                  String outtemper,
                                                                                                  String outhum,
                                                                                                  String outdir,
                                                                                                  String reamrk) {
        return RequestData.<ApiResponse<List<PairingInfoEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PairingInfoEntity>>>() {
                }.getType())
                .url(R.string.pairing_nest_info_modify)
                .addBody("nestid", nestid)//
                .addBody("breedid", breedid)//
                .addBody("time", time)//配对时间
                .addBody("eggtime", eggtime)//
                .addBody("igg", igg)//
                .addBody("fgg", fgg)//
                .addBody("eggweather", eggweather)//
                .addBody("eggtemper", eggtemper)//
                .addBody("egghum", egghum)//
                .addBody("eggdir", eggdir)//
                .addBody("outtime", outtime)//
                .addBody("outcount", outcount)//

                .addBody("pigeonidstr", pigeonidstr)//
                .addBody("footidstr", footidstr)//

                .addBody("outweather", outweather)//
                .addBody("outtemper", outtemper)//
                .addBody("outhum", outhum)//
                .addBody("outdir", outdir)//
                .addBody("reamrk", reamrk)//
                .request();
    }


    //hl 窝次信息  删除
    public static Observable<ApiResponse<Object>> getTXGP_PigeonBreedNest_Delete(String breedid,
                                                                                 String nestid) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pairing_nest_info_del)
                .addBody("breedid", breedid)//
                .addBody("nestid", nestid)//
                .request();
    }


    //hl 信鸽血统推荐
    public static Observable<ApiResponse<List<PriringRecommendEntity>>> getTXGP_PigeonBreed_RecomBlood(String pi,
                                                                                                       String ps,
                                                                                                       String sex,
                                                                                                       String pigeonid,
                                                                                                       String blood) {
        return RequestData.<ApiResponse<List<PriringRecommendEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PriringRecommendEntity>>>() {
                }.getType())
                .url(R.string.pairing_recommend_lineage)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .addBody("sex", sex)//
                .addBody("pigeonid", pigeonid)//
                .addBody("blood", blood)//
                .request();
    }


    //hl 信鸽赛绩推荐
    public static Observable<ApiResponse<List<PriringRecommendEntity>>> getTXGP_PigeonBreed_RecomMatch(String pi,
                                                                                                       String ps,
                                                                                                       String sex,
                                                                                                       String pigeonid) {
        return RequestData.<ApiResponse<List<PriringRecommendEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PriringRecommendEntity>>>() {
                }.getType())
                .url(R.string.pairing_recommend_play)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .addBody("sex", sex)//
                .addBody("pigeonid", pigeonid)//
                .request();
    }


    //hl 信鸽评分推荐
    public static Observable<ApiResponse<List<PriringRecommendEntity>>> getTXGP_PigeonTrain_RecomSorce(String pi,
                                                                                                       String ps,
                                                                                                       String sex,
                                                                                                       String pigeonid) {
        return RequestData.<ApiResponse<List<PriringRecommendEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PriringRecommendEntity>>>() {
                }.getType())
                .url(R.string.pairing_recommend_sorce)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .addBody("sex", sex)//
                .addBody("pigeonid", pigeonid)//
                .request();
    }


}
