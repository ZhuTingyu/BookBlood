package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.http.HttpErrorException;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import io.reactivex.functions.Consumer;

/**
 * 种鸽修改
 * Created by Administrator on 2018/8/28.
 */

public class BreedPigeonModifyViewModel extends BasePigeonViewModel {


    public MutableLiveData<PigeonEntity> mBreedPigeonData = new MutableLiveData<>();

    //鸽子id
    public String pigeonid;

    public PigeonEntity mPigeonEntity;

    //种鸽修改
    public void modifyBreedPigeonEntry() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_Modify(
                pigeonType.equals(PigeonEntity.ID_BREED_PIGEON) ? PigeonEntity.ID_BREED_PIGEON : PigeonEntity.ID_MATCH_PIGEON,
                pigeonid,
                countryId,
                foot,
                footVice,
                sourceId,
                footFatherId,
                pigeonFatherId,
                footFather,
                pigeonFatherStateId,
                footMotherId,
                pigeonMotherId,
                footMother,
                pigeonMotherStateId,
                pigeonName,
                sexId,
                featherColor,
                eyeSandId,
                theirShellsDate,
                lineage,
                stateId,
                phototypeid,
                StringUtil.emptyString(),
                StringUtil.emptyString(),
                llHangingRingDate,
                nestId,
                setImageMap()), r -> {

            if (r.isOk()) {

                mBreedPigeonData.setValue(r.data);

                EventBus.getDefault().post(new PigeonAddEvent());
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
