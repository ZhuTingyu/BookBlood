package com.cpigeon.book.module.racing.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.http.HttpErrorException;
import com.cpigeon.book.model.RacingPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.module.breed.viewmodel.BasePigeonViewModel;

/**
 * Created by Administrator on 2018/9/8.
 */

public class RacingPigeonEntryViewModel extends BasePigeonViewModel {


    public MutableLiveData<PigeonEntryEntity> mEntryData = new MutableLiveData<>();

    //赛鸽录入
    public void addRacingPigeonEntry() {
//        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_Add(countryId,
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

//                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    public void isCanCommit() {
        isCanCommit(foot, sourceId, sexId, featherColor, eyeSandId, theirShellsDate, lineage, llHangingRingDate);
    }

}
