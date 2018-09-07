package com.cpigeon.book.module.breed.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntryEntity;

import java.util.HashMap;

import io.reactivex.functions.Consumer;

/**
 * 种鸽录入
 * Created by Administrator on 2018/8/28.
 */

public class BreedPigeonEntryViewModel extends BasePigeonViewModel {


    public MutableLiveData<PigeonEntryEntity> mBreedPigeonData = new MutableLiveData<>();

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
                setImageMap()), r -> {

            if (r.isOk()) {

                mBreedPigeonData.setValue(r.data);

//                hintDialog(r.msg);
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


    public HashMap<String, String> setImageMap() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0, len = images.size(); i < len; i++) {
            map.put("photo", images.get(i));
        }
        return map;
    }

}
