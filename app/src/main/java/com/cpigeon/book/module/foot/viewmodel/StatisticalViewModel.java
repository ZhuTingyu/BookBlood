package com.cpigeon.book.module.foot.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.StatisticalModel;

import io.reactivex.functions.Consumer;

/**
 * 该viewmodel  为统计相关
 * Created by Administrator on 2018/8/9.
 */

public class StatisticalViewModel extends BaseViewModel {


    //得到各种类型的足环个数
    public void getgetTXGP_FootRing_SelectTypeData() {
        submitRequestThrowError(StatisticalModel.getTXGP_FootRing_SelectType(), r -> {
            if (r.isOk()) {
                hintDialog(r.toJsonString());
            } else throw new HttpErrorException(r);
        });
    }


    public String pigeonType;


    public Consumer<String> setPigeonType() {
        return s -> {
            this.pigeonType = s;
        };
    }

    //得到各种类型的足环个数
    public void getPigeonSexCountData() {
        submitRequestThrowError(StatisticalModel.getPigeonSexCount(pigeonType), r -> {
            if (r.isOk()) {
                hintDialog(r.toJsonString());
            } else throw new HttpErrorException(r);
        });
    }


}
