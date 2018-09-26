package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonSexCountEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/4.
 */

public class BreedPigeonListModel extends BaseViewModel {

    public static final String CODE_IN_SHARE_HALL = "1";
    public static final String CODE_IN_NOT_SHARE_HALL = "2";

    public int pi = 1;
    public int ps = 15;

    public boolean isSearch = false;//true  搜索   false  删选

    public String typeid = PigeonEntity.ID_BREED_PIGEON;//鸽子类型（8为种鸽，9为赛鸽，不传则全部查询）

    public String bitmatch = PigeonEntity.BIT_MATCH;//是否返回赛绩（1，返回）

    public String year;//年份
    public String sexid;//性别
    public String stateid;//状态
    public String bloodid;//血统id （1,2）

    public String bitbreed;//是否有父母（1存在，2.不存在，其他全查）
    public String pigeonidStr;// ：在列表中排除的鸽子
    public String bitshare;// ：是否是在共享厅（1：存在，2，不存在，其他全查）
    public String bitMotto;// ：是不是铭鸽（1：是，2：正在申请 ，3，不是，其他全查）

    public MutableLiveData<List<PigeonEntity>> mPigeonListData = new MutableLiveData<>();
    public MutableLiveData<PigeonSexCountEntity> mLivePigeonSexCount = new MutableLiveData<>();

    public String searchStr;

    //获取  种鸽列表
    public void getPigeonList() {
        if (isSearch) {
            submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_SearchBreed(String.valueOf(pi),
                    String.valueOf(ps),
                    searchStr,
                    typeid,
                    bitmatch

            ), r -> {
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
                    stateid,
                    bitmatch,
                    bitbreed,//是否有父母（1存在，2.不存在，其他全查）
                    pigeonidStr,// ：在列表中排除的鸽子
                    bitshare,// ：是否是在共享厅（1：存在，2，不存在，其他全查）
                    bitMotto), r -> {

                if (r.isOk()) {
                    listEmptyMessage.setValue(r.msg);
                    mPigeonListData.setValue(r.data);
                } else throw new HttpErrorException(r);
            });
        }
    }

    //获取统计数据
    public void getPigeonCount() {
        submitRequestThrowError(BreedPigeonModel.getPigeonSexCount(typeid), r -> {
            if (r.isOk()) {
                mLivePigeonSexCount.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
