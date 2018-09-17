package com.cpigeon.book.module.breeding.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PairingModel;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * 添加窝次
 * Created by Administrator on 2018/9/10.
 */

public class PairingNestAddViewModel extends BaseViewModel {


    public PairingInfoEntity mPairingInfoEntity;
    public PigeonEntity mBreedPigeonEntity;

    //窝次
    public String nestNum;
    //父足环号码
    public String footFather;
    //母足环号码
    public String footMother;
    //配对时间
    public String pairingTime;
    //产蛋信息
    public String layEggs;
    //产蛋时间
    public String layEggsTime;
    //受精蛋
    public String fertilizedEgg;
    //无精蛋
    public String fertilizedEggNo;
    //出壳信息
    public String hatchesInfo;
    //出壳时间
    public String hatchesTime;
    //出壳个数
    public String hatchesNum;
    //子代信息
    public String offspringInfo;
    //配对天气
    public String weather;
    //配对气温
    public String temper;
    //配对湿度
    public String hum;
    //配对风向
    public String dir;

    //添加窝次信息   TXGP_PigeonBreedNest_Add
    public void getTXGP_PigeonBreedNest_Add() {
        submitRequestThrowError(PairingModel.getTXGP_PigeonBreedNest_Add(mPairingInfoEntity.getPigeonBreedID(),
                pairingTime,
                layEggsTime,
                fertilizedEgg,
                fertilizedEggNo,
                weather,
                temper,
                hum,
                dir,
                hatchesTime,
                hatchesNum,
                weather,
                temper,
                hum,
                dir,
                ""), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    public void isCanCommit() {
        isCanCommit(pairingTime, layEggs, hatchesInfo, offspringInfo);
    }

}
