package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/9.
 */

public class PigeonPublicModel {
    //hl 足环，种赛鸽的类型，状态，来源，羽色，血统，眼沙，性别
    public static Observable<ApiResponse<List<SelectTypeEntity>>> getTXGP_Type_Select(String selectType) {
        return RequestData.<ApiResponse<List<SelectTypeEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<SelectTypeEntity>>>() {
                }.getType())
                .url(R.string.pigeon_select_all_type)
                .addBody("whichid", selectType)
                .request();
    }

    public static Observable<ApiResponse<List<SelectTypeEntity>>> getSelectMushType(String selectTypes) {
        return RequestData.<ApiResponse<List<SelectTypeEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<SelectTypeEntity>>>() {
                }.getType())
                .url(R.string.pigeon_select_filtrate_type)
                .addBody("whichidstr", selectTypes)
                .request();
    }

    public static Observable<ApiResponse> modifyPigeonStatue(
            String pigeonId,
            int footId,
            String stateID,
            String deathId) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.modify_pigeon_statue)
                .addBody("pigeonid", pigeonId)
                .addBody("footid", String.valueOf(footId))
                .addBody("stateid", "37")
                .addBody("deathid", deathId)
                .request();
    }


}
