package com.cpigeon.book.module.photo.viewmodel;

import com.base.base.BaseViewModel;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/31.
 */

public class ImgUploadViewModel  extends BaseViewModel {

    //足环号
    public String imgLabel = "";

    //图片类型
    public List<SelectTypeEntity> mSelectTypes_ImgType;
    public String imgTypeStr = "";
    public String imgTypeId = "";


    public Consumer<String> setImgLabel() {
        return s -> {
            this.imgLabel = s;
            isCanCommit();
        };
    }


    public void isCanCommit() {
        isCanCommit(imgLabel);
    }


}
