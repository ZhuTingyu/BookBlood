package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.http.HttpErrorException;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;

import io.reactivex.functions.Consumer;

/**
 * 种鸽录入
 * Created by Administrator on 2018/8/28.
 */

public class BreedPigeonEntryViewModel extends BasePigeonViewModel {


    public MutableLiveData<PigeonEntryEntity> mBreedPigeonData = new MutableLiveData<>();
    public MutableLiveData<PigeonEntity> mPigeonDetailsData = new MutableLiveData<>();
    public String pigeonId;
    public String sonFootId;
    public String sonPigeonId;

    public PigeonEntity mBreedPigeonEntity = new PigeonEntity();

    //种鸽录入
    public void addBreedPigeonEntry() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_Add(countryId,
                foot,
                footVice,
                sourceId,
                footFather,
                footMother,
                pigeonName,
                sexId,
                featherColor,
                eyeSandId,
                theirShellsDate,
                lineage,
                stateId,
                phototypeid,
                sonFootId,
                sonPigeonId,
                setImageMap()), r -> {

            if (r.isOk()) {

                mBreedPigeonData.setValue(r.data);

//                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    public void modifyBreedPigeonEntry() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_Modify(
                mBreedPigeonEntity.getPigeonID(),// 鸽子id
                countryId,// 国家Id
                foot,//足环（可选可填，传足环号）
                footVice,//副足环
                sourceId,//信鸽来源ID
                footMother,// 母足环号码
                footFather,// 父足环号码
                pigeonName,// 信鸽名称
                sexId,//  性别（传ID）
                featherColor,//  羽色（可选可填，传羽色名称）
                eyeSandId,//  眼沙（传ID）
                theirShellsDate,//   出壳时间
                lineage,//  血统 （可选可填，传血统名称）
                stateId,// 信鸽状态ID
                phototypeid,//
                setImageMap()), r -> {

            if (r.isOk()) {

                mBreedPigeonData.setValue(r.data);

            } else throw new HttpErrorException(r);
        });
    }

    public void getPigeonDetails() {

        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_GetInfo(pigeonId, UserModel.getInstance().getUserId()), r -> {
            if (r.isOk()) {
                mBreedPigeonEntity = r.data;
                mPigeonDetailsData.setValue(mBreedPigeonEntity);
            } else throw new HttpErrorException(r);
        });
    }


    public Consumer<String> setFootNumber() {
        return s -> {
            this.foot = s;
            isCanCommit();
        };
    }


    public void isCanCommit() {
        isCanCommit(foot, sourceId, sexId, featherColor, eyeSandId, theirShellsDate, lineage, stateId);
    }

    public boolean isHavePigeonInfo(){
        return mBreedPigeonEntity != null && StringUtil.isStringValid(mBreedPigeonEntity.getPigeonID());
    }

    public boolean isHaveSex(){
        return mBreedPigeonEntity != null && StringUtil.isStringValid(mBreedPigeonEntity.getPigeonSexID());
    }


}
