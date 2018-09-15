package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/8.
 */

public class RacingPigeonModel {


    //hl 添加赛鸽
    public static Observable<ApiResponse<PigeonEntryEntity>> getTXGP_Pigeon_Racing_Add(String coodid,
                                                                                String footnum,
                                                                                String footnumto,
                                                                                String sourceid,
                                                                                String menfootnum,
                                                                                String wofootnum,
                                                                                String name,
                                                                                String sex,
                                                                                String plume,
                                                                                String eye,
                                                                                String outtime,
                                                                                String blood,
                                                                                String stateid,
                                                                                String phototypeid,
                                                                                String foottime,
                                                                                Map<String, String> body) {
        return RequestData.<ApiResponse<PigeonEntryEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonEntryEntity>>() {
                }.getType())
                .url(R.string.pigeon_racing_add)
                .addBody("coodid", coodid)// 国家Id
                .addBody("footnum", footnum)//足环（可选可填，传足环号）
                .addBody("footnumto", footnumto)// 副环（可选可填 ，传足环号）
                .addBody("sourceid", sourceid)// 信鸽来源ID
                .addBody("menfootnum", menfootnum)// 父足环号码
                .addBody("wofootnum", wofootnum)// 母足环号码
                .addBody("name", name)// 信鸽名称
                .addBody("sex", sex)//  性别（传ID）
                .addBody("plume", plume)//  羽色（可选可填，传羽色名称）
                .addBody("eye", eye)//  眼沙（传ID）
                .addBody("outtime", outtime)//   出壳时间
                .addBody("blood", blood)//  血统 （可选可填，传血统名称）
                .addBody("stateid", stateid)// 信鸽状态ID
                .addBody("phototypeid", phototypeid)// 信鸽状态ID
                .addBody("foottime", foottime)// 挂环日期
                .addBody("typeid", PigeonEntity.ID_MATCH_PIGEON)// 挂环日期
                .addImageFileBodys(body)
                .request();
    }
}
