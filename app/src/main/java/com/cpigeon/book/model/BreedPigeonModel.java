package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/6.
 */

public class BreedPigeonModel {

    //hl 获取种鸽详细
    public static Observable<ApiResponse<Object>> getTXGP_Pigeon_GetInfo(String footId) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_breed_details)
                .addBody("pigeonid", footId)
                .request();
    }


//    //hl 添加种鸽
//    public static Observable<ApiResponse<Object>> getTXGP_Pigeon_Add(String name, String typeid, String stateid, String source,
//                                                                     String sex, String eye, String bitdate, String plume, String blood,
//                                                                     String outtime) {
//        return RequestData.<ApiResponse<Object>>build()
//                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
//                }.getType())
//                .url(R.string.pigeon_breed_add)
//                .addBody("name", name)
//                .addBody("typeid", typeid)
//                .addBody("stateid", stateid)
//                .addBody("sourceid", source)//来源
//                .addBody("sex", sex)//性别  id
//                .addBody("eye", eye)//眼砂  id
//                .addBody("sex", sex)//性别
//                .addBody("bitdate", bitdate)//是否要挂卖数据
//                .addBody("plume", plume)//羽色
//                .addBody("blood", blood)//血统
//                .addBody("outtime", outtime)//出壳时间
//                .request();
//    }
}
