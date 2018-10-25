package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.ShootInfoEntity;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class ShootModel {


    public static Observable<ApiResponse<Object>> getTXGP_SetTouXiangGeSheMingCheng(String face, String gsname) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.set_shoot_logo)
                .addImageFileBody("imgfile", face)
                .addBody("sszz", gsname)
                .request();
    }

    public static Observable<ApiResponse<ShootInfoEntity>> getTXGP_GetTouXiangGeSheMingCheng() {
        return RequestData.<ApiResponse<ShootInfoEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<ShootInfoEntity>>() {
                }.getType())
                .url(R.string.get_shoot_logo)
                .request();
    }


}
