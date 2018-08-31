package com.cpigeon.book.module.play.viewmodel;

import com.base.base.BaseViewModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/31.
 */

public class PlayViewModel extends BaseViewModel {


    //组织名称
    public String playOrg = "";
    //项目名称
    public String projectName = "";
    //比赛规模
    public String palyScale = "";
    //比赛名次
    public String palyRank = "";
    //司放空距
    public String plyUllage = "";
    //司放地点
    public String plyPlace = "";
    //比赛时间
    public String playTime = "";

    public Consumer<String> setPlayTime() {
        return s -> {
            this.playTime = s;
            isCanCommit();
        };
    }


    public void isCanCommit() {
        isCanCommit(playOrg, projectName, palyScale, palyRank, plyUllage, plyPlace, playTime);
    }


}
