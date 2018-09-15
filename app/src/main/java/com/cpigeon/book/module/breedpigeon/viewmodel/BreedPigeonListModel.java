package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/4.
 */

public class BreedPigeonListModel extends BaseViewModel {


    public int pi = 1;
    public int ps = 15;

    public boolean isSearch = false;//true  搜索   false  删选

    public String typeid = "8";//鸽子类型（8为种鸽，9为赛鸽，不传则全部查询）
    public String year;//年份
    public String sexid;//性别
    public String stateid;//状态
    public String bloodid;//血统id （1,2）

    public MutableLiveData<List<PigeonEntity>> mPigeonListData = new MutableLiveData<>();

    public String searchStr;

    //获取  种鸽列表
    public void getPigeonList() {
        if (isSearch) {
            submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_SearchBreed(String.valueOf(pi), String.valueOf(ps), searchStr, typeid), r -> {
                if (r.isOk()) {
                    listEmptyMessage.setValue(r.msg);
                    mPigeonListData.setValue(r.data);
                } else throw new HttpErrorException(r);
            });
        } else {
            submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_SelectAll(String.valueOf(pi), String.valueOf(ps),
                    typeid,
                    bloodid,
                    sexid,
                    year,
                    stateid), r -> {

                if (r.isOk()) {
                    listEmptyMessage.setValue(r.msg);
                    mPigeonListData.setValue(r.data);
                } else throw new HttpErrorException(r);
            });
        }
    }

}
