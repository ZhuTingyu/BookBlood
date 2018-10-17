package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.http.ApiResponse;
import com.base.http.HttpErrorException;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.RacingPigeonModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * 种鸽录入
 * Created by Administrator on 2018/8/28.
 */

public class InputPigeonViewModel extends BasePigeonViewModel {


    public MutableLiveData<ApiResponse<PigeonEntryEntity>> mDataPigeon = new MutableLiveData<>();
    public MutableLiveData<PigeonEntity> mDataPigeonDetails = new MutableLiveData<>();
    public String pigeonId;
    public String sonFootId;
    public String sonPigeonId;

    public PigeonEntity mBreedPigeonEntity = new PigeonEntity();

    //种鸽录入
    public void addPigeon() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_Add(
                pigeonType.equals(PigeonEntity.ID_BREED_PIGEON) ? PigeonEntity.ID_BREED_PIGEON : PigeonEntity.ID_MATCH_PIGEON,
                countryId,
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
                llHangingRingDate,
                setImageMap()), r -> {

            if (r.isOk()) {
                mDataPigeon.setValue(r);
                EventBus.getDefault().post(new PigeonAddEvent());
            } else throw new HttpErrorException(r);
        });
    }

    public void modifyBreedPigeonEntry() {
        if (pigeonType.equals(PigeonEntity.ID_BREED_PIGEON)) {
            submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_Modify(
                    mBreedPigeonEntity.getPigeonID(),// 鸽子id
                    countryId,// 国家Id
                    foot,//足环（可选可填，传足环号）
                    footVice,//副足环
                    sourceId,//信鸽来源ID
                    footFather,// 父足环号码
                    footMother,// 母足环号码
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

                    mDataPigeon.setValue(r);
                    EventBus.getDefault().post(new PigeonAddEvent());
                } else throw new HttpErrorException(r);
            });
        } else {
            submitRequestThrowError(BreedPigeonModel.getTXGP_Racing_Pigeon_Modify(
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

                    mDataPigeon.setValue(r);
                    EventBus.getDefault().post(new PigeonAddEvent());
                } else throw new HttpErrorException(r);
            });
        }
    }

    public void getPigeonDetails() {

        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_GetInfo(pigeonId, UserModel.getInstance().getUserId()), r -> {
            if (r.isOk()) {
                mBreedPigeonEntity = r.data;
                mDataPigeonDetails.setValue(mBreedPigeonEntity);
            } else throw new HttpErrorException(r);
        });
    }

    public void isCanCommit() {
        isCanCommit(foot, countryId, sexId, lineage, featherColor, stateId);
    }

    public boolean isHavePigeonInfo() {
        return mBreedPigeonEntity != null && StringUtil.isStringValid(mBreedPigeonEntity.getPigeonID());
    }

    public boolean isHaveSex() {
        return mBreedPigeonEntity != null && StringUtil.isStringValid(mBreedPigeonEntity.getPigeonSexID());
    }

}
