package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/19 0019.
 */

public class PhotoAlbumModel {

    //hl 上传图片  一张
    public static Observable<ApiResponse<Object>> getTXGP_PigeonPhoto_Add(String pigeonid,
                                                                                           String footid,
                                                                                           String typeid,
                                                                                           String photo) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.upload_img_one)
                .addBody("pigeonid", pigeonid)//
                .addBody("footid", footid)//
                .addBody("typeid", typeid)//
                .addImageFileBody("photo", photo)//
                .request();
    }

}
