package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;

import java.util.HashMap;

import io.reactivex.functions.Consumer;

/**
 * 种鸽修改
 * Created by Administrator on 2018/8/28.
 */

public class BreedPigeonModifyViewModel extends BasePigeonViewModel {


    public MutableLiveData<PigeonEntryEntity> mBreedPigeonData = new MutableLiveData<>();

    //鸽子id
    public String pigeonid;

    public BreedPigeonEntity mBreedPigeonEntity;

    //种鸽修改
    public void modifyBreedPigeonEntry() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_Modify(
                mBreedPigeonEntity.getPigeonID(),// 鸽子id
                mBreedPigeonEntity.getFootCodeID(),// 国家Id
                mBreedPigeonEntity.getFootRingNum(),//足环（可选可填，传足环号）
                mBreedPigeonEntity.getFootRingIDToNum(),//副足环
                mBreedPigeonEntity.getSourceID(),//信鸽来源ID
                mBreedPigeonEntity.getWoFootRingNum(),// 母足环号码
                mBreedPigeonEntity.getMenFootRingNum(),// 父足环号码
                mBreedPigeonEntity.getPigeonName(),// 信鸽名称
                mBreedPigeonEntity.getPigeonSexID(),//  性别（传ID）
                mBreedPigeonEntity.getPigeonPlumeName(),//  羽色（可选可填，传羽色名称）
                mBreedPigeonEntity.getPigeonEyeID(),//  眼沙（传ID）
                mBreedPigeonEntity.getOutShellTime(),//   出壳时间
                mBreedPigeonEntity.getPigeonBloodName(),//  血统 （可选可填，传血统名称）
                mBreedPigeonEntity.getStateID(),// 信鸽状态ID
                mBreedPigeonEntity.getCoverPhotoID(),//
                setImageMap()), r -> {

            if (r.isOk()) {

                mBreedPigeonData.setValue(r.data);

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
