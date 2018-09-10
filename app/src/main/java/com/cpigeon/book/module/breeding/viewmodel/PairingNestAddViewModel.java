package com.cpigeon.book.module.breeding.viewmodel;

import com.base.base.BaseViewModel;

/**
 * 添加窝次
 * Created by Administrator on 2018/9/10.
 */

public class PairingNestAddViewModel extends BaseViewModel {

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


    public void isCanCommit() {
        isCanCommit( pairingTime, layEggs, hatchesInfo, offspringInfo);
    }


}
