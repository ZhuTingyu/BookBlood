package com.cpigeon.book.module.foot.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.FootAdminModel;

/**
 * 足环管理
 * Created by Administrator on 2018/8/7.
 */

public class FootAdminViewModel extends BaseViewModel {


    //得到各种类型的足环个数
    public void getTXGP_FootRing_SelectData() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_SelectType(), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }


    public int pi = 1;
    public int ps = 20;
    public String year = "";
    public String typeid = "";
    public String stateid = "";
    public String key = "";

    //足环号码 列表
    public void getTXGP_FootRing_SelectKeyAllData() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_SelectKeyAll(pi, ps, year, typeid, stateid, key), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }
}
