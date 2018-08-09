package com.cpigeon.book.module.foot.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BreedPigeonModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/6.
 */

public class BreedPigeonViewModel extends BaseViewModel {


    public String footDetailsID = "";

    public Consumer<String> setDetailsFootId() {
        return s -> {
            footDetailsID = s;
        };
    }

    //获取种鸽详细
    public void getTXGP_Pigeon_GetInfoData() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_GetInfo(footDetailsID), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }



    //添加种鸽
    public void getTXGP_Pigeon_AddData() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_GetInfo(footDetailsID), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }
}
