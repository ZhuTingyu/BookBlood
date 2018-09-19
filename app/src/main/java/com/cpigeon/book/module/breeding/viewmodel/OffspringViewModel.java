package com.cpigeon.book.module.breeding.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.RacingPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BasePigeonViewModel;

/**
 * Created by Administrator on 2018/9/19 0019.
 */

public class OffspringViewModel extends BasePigeonViewModel {


    public MutableLiveData<PigeonEntryEntity> mEntryData = new MutableLiveData<>();
    public int pigeonType = 1;//1 种鸽录入   2：赛鸽录入


    public void addRacingPigeonEntry() {

        if (pigeonType == 1) {
            //种鸽录入
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
                    "",
                    "",
                    setImageMap()), r -> {
                if (r.isOk()) {
                    mEntryData.setValue(r.data);
                } else throw new HttpErrorException(r);
            });

        } else {
            //赛鸽录入
            submitRequestThrowError(RacingPigeonModel.getTXGP_Pigeon_Racing_Add(countryId,
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
                    llHangingRingDate,
                    setImageMap()), r -> {
                if (r.isOk()) {
                    mEntryData.setValue(r.data);
                } else throw new HttpErrorException(r);
            });
        }

    }


    public void isCanCommit() {

        if (pigeonType == 1) {
            //种鸽录入
            isCanCommit(foot, sourceId, sexId, featherColor, eyeSandId, theirShellsDate, lineage, stateId);
        } else {
            //赛鸽录入
            isCanCommit(foot, sourceId, sexId, featherColor, eyeSandId, theirShellsDate, lineage, llHangingRingDate);
        }
    }

}
