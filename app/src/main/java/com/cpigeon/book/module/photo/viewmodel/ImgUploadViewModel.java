package com.cpigeon.book.module.photo.viewmodel;

import com.base.base.BaseViewModel;
import com.base.entity.RestHintInfo;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PhotoAlbumModel;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.service.EventBusService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/31.
 */

public class ImgUploadViewModel extends BaseViewModel {


    //图片类型
    public List<SelectTypeEntity> mSelectTypes_ImgType;
    //图片类型选择
    public String imgTypeStr = "";
    public String imgTypeId = "";
    //图片地址
    public String imgUrlStr;

    public ImgTypeEntity mImgTypeEntity;


    public Consumer<String> setImgLabel() {
        return s -> {
            this.imgTypeStr = s;
            isCanCommit();
        };
    }

    public Consumer<String> setImgRemark() {
        return s -> {
            mImgTypeEntity.setImgRemark(s);
            isCanCommit();
        };
    }

    //上传图片
    public void getTXGP_PigeonPhoto_AddData() {
        submitRequestThrowError(PhotoAlbumModel.getTXGP_PigeonPhoto_Add(
                mImgTypeEntity.getPigeonId(),
                mImgTypeEntity.getFoootId(),
                imgTypeId,
                mImgTypeEntity.getImgPath(),
                mImgTypeEntity.getImgRemark()
        ), r -> {
            if (r.isOk()) {
                EventBus.getDefault().post(EventBusService.PIGEON_PHOTO_REFRESH);
                EventBus.getDefault().post(EventBusService.FEED_PIGEON_DETAILS_REFRESH);
                showHintClosePage.setValue(new RestHintInfo.Builder().message(r.msg).cancelable(false).isClosePage(true).build());
            } else throw new HttpErrorException(r);
        });
    }

    public void isCanCommit() {
        isCanCommit(imgTypeStr);
    }


}
