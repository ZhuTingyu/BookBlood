package com.cpigeon.book.module.play.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PlayModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/31.
 */

public class PlayViewModel extends BaseViewModel {

    //鸽子id
    public String pigeonid;
    //足环ID
    public String footid;
    //组织名称
    public String playOrg = "";
    //项目名称
    public String projectName = "";
    //比赛规模
    public String palyScale = "";
    //比赛名次
    public String palyRank = "";
    //司放地点
    public String plyPlace = "";
    //司放空距
    public String plyUllage = "";
    //比赛时间
    public String playTime = "";

    public MutableLiveData<Object> addPigeonPlayData = new MutableLiveData<>();

    //是否是标准赛绩
    public boolean isStandardPlay = true;

    //赛绩附加信息
    public String playAdditionalInfo;

    public Consumer<String> setPlayAdditionalInfo() {
        return s -> {
            this.playAdditionalInfo = s;
            isCanCommit2();
        };
    }

    //赛绩录入
    public void addPigeonPlay() {

        if (isStandardPlay) {
            submitRequestThrowError(PlayModel.getTXGP_PigeonMatch_Add(pigeonid,
                    footid,
                    playOrg,
                    projectName,
                    palyScale,
                    palyRank,
                    plyPlace,
                    plyUllage,
                    playTime,
                    ""), r -> {
                if (r.isOk()) {
                    addPigeonPlayData.setValue(r.data);
                } else throw new HttpErrorException(r);
            });
        } else {
            submitRequestThrowError(PlayModel.getTXGP_PigeonInfoList_AddInfo(pigeonid,
                    footid,
                    playAdditionalInfo), r -> {
                if (r.isOk()) {
                    addPigeonPlayData.setValue(r.data);
                } else throw new HttpErrorException(r);
            });
        }


    }


    public void isCanCommit() {
        isCanCommit(playOrg, projectName, palyScale, palyRank, plyUllage, plyPlace, playTime);
    }


    public void isCanCommit2() {
        isCanCommit(playAdditionalInfo);
    }

}
