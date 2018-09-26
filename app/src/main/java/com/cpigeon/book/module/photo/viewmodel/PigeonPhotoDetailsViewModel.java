package com.cpigeon.book.module.photo.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.entity.RestHintInfo;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PhotoAlbumModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonPhotoEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.service.EventBusService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/30.
 */

public class PigeonPhotoDetailsViewModel extends BaseViewModel {


    public PigeonEntity mPigeonEntity;
    public List<PigeonPhotoEntity> mPigeonPhotoData;
    public List<SelectTypeEntity> mPhotoType;
    public String typeid;//

    public String photoid;// 当前图片id

    public String currentImgTypeStr;

    public MutableLiveData<Object> imgEditCallBack = new MutableLiveData<>();//图片编辑后回调
    public MutableLiveData<Object> imgDelCallBack = new MutableLiveData<>();//图片删除后回调

    public String remark;
    public MutableLiveData<Object> imgRemarkEditCallBack = new MutableLiveData<>();//图片修改备注后回调


    //移动图片 到别的标签下
    public void getTXGP_PigeonPhoto_UpdateData() {
        submitRequestThrowError(PhotoAlbumModel.getTXGP_PigeonPhoto_Update(
                photoid,
                typeid
        ), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
                EventBus.getDefault().post(EventBusService.PIGEON_PHOTO_REFRESH);
                imgEditCallBack.setValue(r);
            } else throw new HttpErrorException(r);
        });
    }

    //删除图片  一张
    public void getTXGP_PigeonPhoto_DeleteData() {
        submitRequestThrowError(PhotoAlbumModel.getTXGP_PigeonPhoto_Delete(
                photoid), r -> {
            if (r.isOk()) {
                EventBus.getDefault().post(EventBusService.PIGEON_PHOTO_REFRESH);
                EventBus.getDefault().post(EventBusService.PIGEON_PHOTO_DEL_REFRESH);
                imgDelCallBack.setValue(r.msg);
                showHintClosePage.setValue(new RestHintInfo.Builder().message(r.msg).cancelable(false).isClosePage(false).build());
            } else throw new HttpErrorException(r);
        });
    }


    //将图片设置为封面
    public void getTXGP_PigeonPhoto_EideConverData() {
        submitRequestThrowError(PhotoAlbumModel.getTXGP_PigeonPhoto_EideConver(
                mPigeonEntity.getPigeonID(),
                photoid
        ), r -> {
            if (r.isOk()) {
                EventBus.getDefault().post(EventBusService.PIGEON_PHOTO_REFRESH);
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    //修改图片备注
    public void getTXGP_PigeonPhoto_EideRemarkData() {
        submitRequestThrowError(PhotoAlbumModel.getTXGP_PigeonPhoto_EideRemark(
                mPigeonEntity.getPigeonID(),
                photoid,
                remark
        ), r -> {
            if (r.isOk()) {
                EventBus.getDefault().post(EventBusService.PIGEON_PHOTO_REFRESH);
                imgRemarkEditCallBack.setValue(r.msg);
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


}
