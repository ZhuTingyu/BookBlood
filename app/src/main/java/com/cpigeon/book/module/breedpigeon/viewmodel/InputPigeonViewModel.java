package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.http.ApiResponse;
import com.base.http.HttpErrorException;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.FootRingStateEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * 种鸽录入
 * Created by Administrator on 2018/8/28.
 */

public class InputPigeonViewModel extends BasePigeonViewModel {


    public MutableLiveData<ApiResponse<PigeonEntity>> mDataPigeon = new MutableLiveData<>();
    public MutableLiveData<PigeonEntity> mDataPigeonDetails = new MutableLiveData<>();
    public MutableLiveData<FootRingStateEntity> mDataFootRingState = new MutableLiveData<>();
    public String pigeonId;
    public String sonFootId;
    public String sonPigeonId;

    public PigeonEntity mPigeonEntity = new PigeonEntity();

    public PairingInfoEntity mPairingInfoEntity;

    //种鸽录入
    public void addPigeon() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_Add(
                pigeonType.equals(PigeonEntity.ID_BREED_PIGEON) ? PigeonEntity.ID_BREED_PIGEON : PigeonEntity.ID_MATCH_PIGEON,
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
                sonFootId,
                sonPigeonId,
                llHangingRingDate,
                nestId,
                setImageMap()), r -> {

            if (r.isOk()) {
                mPigeonEntity = r.data;
                mDataPigeon.setValue(r);

            } else throw new HttpErrorException(r);
        });
    }

    public void modifyBreedPigeonEntry() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_Modify(
                pigeonType.equals(PigeonEntity.ID_BREED_PIGEON) ? PigeonEntity.ID_BREED_PIGEON : PigeonEntity.ID_MATCH_PIGEON,
                pigeonId,
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
                sonFootId,
                sonPigeonId,
                llHangingRingDate,
                nestId), r -> {
            if (r.isOk()) {
                mPigeonEntity = r.data;
                mDataPigeon.setValue(r);
            } else throw new HttpErrorException(r);
        });
    }

    public void getPigeonDetails() {

        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_GetInfo(pigeonId, UserModel.getInstance().getUserId()), r -> {
            if (r.isOk()) {
                mPigeonEntity = r.data;
                mDataPigeonDetails.setValue(mPigeonEntity);
            } else throw new HttpErrorException(r);
        });
    }

    public void getFootRingState() {
        submitRequestThrowError(BreedPigeonModel.getFootRingState(foot), r -> {
            if (r.isOk()) {
                mDataFootRingState.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    public void isCanCommit() {
        isCanCommit(foot, countryId, sexId, lineage, featherColor, stateId,eyeSandId);
    }
    public void isFatherCanCommit() {
        isCanCommit(foot, countryId, sexId, lineage, featherColor, stateId,footFather,pigeonFatherStateId,eyeSandId);
    }
    public void isMotherCanCommit() {
        isCanCommit(foot, countryId, sexId, lineage, featherColor, stateId,footMother,pigeonMotherStateId,eyeSandId);
    }
    public void isallCanCommit() {
        isCanCommit(foot, countryId, sexId, lineage, featherColor, stateId,footMother,pigeonMotherStateId,eyeSandId,pigeonFatherStateId,footFather);
    }
    public boolean isHavePigeonInfo() {
        return mPigeonEntity != null && StringUtil.isStringValid(mPigeonEntity.getPigeonID());
    }

    public boolean isHaveSex() {
        return mPigeonEntity != null && StringUtil.isStringValid(mPigeonEntity.getPigeonSexID());
    }

}
