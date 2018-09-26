package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PigeonPhotoEntity;
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


    //hl 单只鸽子  相片统计
    public static Observable<ApiResponse<PigeonPhotoEntity>> getTXGP_PigeonPhoto_CountPhoto(String pigeonid,
                                                                                            String footid) {
        return RequestData.<ApiResponse<PigeonPhotoEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonPhotoEntity>>() {
                }.getType())
                .url(R.string.pigeion_img_count)
                .addBody("pigeonid", pigeonid)//
                .addBody("footid", footid)//
                .request();
    }

    //hl 移动图片 到别的标签下
    public static Observable<ApiResponse<Object>> getTXGP_PigeonPhoto_Update(String photoid,

                                                                             String typeid) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeion_img_type_move)
                .addBody("photoid", photoid)//
                .addBody("typeid", typeid)//
                .request();
    }

    //hl 图片  删除
    public static Observable<ApiResponse<Object>> getTXGP_PigeonPhoto_Delete(String photoid
    ) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeion_img_one_del)
                .addBody("photoid", photoid)//
                .request();
    }

    //hl 图片  将图片设置为封面
    public static Observable<ApiResponse<Object>> getTXGP_PigeonPhoto_EideConver(String pigeonid,
                                                                                 String photoid) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeion_img_set_cover)
                .addBody("pigeonid", pigeonid)//
                .addBody("photoid", photoid)//
                .request();
    }

    //hl 图片  修改图片备注
    public static Observable<ApiResponse<Object>> getTXGP_PigeonPhoto_EideRemark(String pigeonid,
                                                                                 String photoid,
                                                                                 String remark) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeion_img_edit_remark)
                .addBody("pigeonid", pigeonid)//
                .addBody("photoid", photoid)//
                .addBody("remark", remark)//
                .request();
    }


    //hl 获取图片列表
    public static Observable<ApiResponse<List<PigeonPhotoEntity>>> getTXGP_PigeonPhoto_Select(String pi,
                                                                                              String ps,
                                                                                              String pigeonid,
                                                                                              String puid,
                                                                                              String sort, String typeid) {

        return RequestData.<ApiResponse<List<PigeonPhotoEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonPhotoEntity>>>() {
                }.getType())
                .url(R.string.pigeion_img_list)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .addBody("pigeonid", pigeonid)//
                .addBody("puid", puid)//
                .addBody("sort", sort)//
                .addBody("typeid", typeid)//
                .request();
    }


}
