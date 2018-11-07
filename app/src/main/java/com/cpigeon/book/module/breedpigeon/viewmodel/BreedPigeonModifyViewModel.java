package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.http.HttpErrorException;
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
                mPigeonEntity.getFootRingTime(),
                mPigeonEntity .getWoPigeonStateID(),
                mPigeonEntity.getMenPigeonStateID(),
                mPigeonEntity.getPigeonID(),// 鸽子id
                mPigeonEntity.getFootCodeID(),// 国家Id
                mPigeonEntity.getFootRingNum(),//足环（可选可填，传足环号）
                mPigeonEntity.getFootRingIDToNum(),//副足环
                mPigeonEntity.getSourceID(),//信鸽来源ID
                mPigeonEntity.getMenFootRingNum(),// 父足环号码
                mPigeonEntity.getWoFootRingNum(),// 母足环号码
                mPigeonEntity.getPigeonName(),// 信鸽名称
                mPigeonEntity.getPigeonSexID(),//  性别（传ID）
                mPigeonEntity.getPigeonPlumeName(),//  羽色（可选可填，传羽色名称）
                mPigeonEntity.getPigeonEyeID(),//  眼沙（传ID）
                mPigeonEntity.getOutShellTime(),//   出壳时间
                mPigeonEntity.getPigeonBloodName(),//  血统 （可选可填，传血统名称）
                mPigeonEntity.getStateID(),// 信鸽状态ID
                mPigeonEntity.getCoverPhotoID(),//

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
