package com.cpigeon.book.module.photo.viewmodel;

import com.base.base.BaseViewModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/31.
 */

public class ImgUploadViewModel  extends BaseViewModel {

    //足环号
    public String imgLabel = "";

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
