package com.cpigeon.book.module.play.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.entity.RestHintInfo;
import com.base.http.HttpErrorException;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.PlayModel;
import com.cpigeon.book.model.entity.PigeonPlayEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/31.
 */

public class PlayViewModel extends BaseViewModel {

    public int type;//0 添加赛绩，可切换标准，附加信息   1  标准 修改删除  2 附加信息修改删除

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
    //赛绩附加信息
    public String playAdditionalInfo;

    //是否是标准赛绩
    public boolean isStandardPlay = true;
    //ture  添加   false   修改
    public boolean isAdd = true;
    public String matchid;//标准赛绩id
    public String infoid;//附加信息id

    public List<SelectTypeEntity> mPlayOrgData;//赛事组织
    //添加结果
    public MutableLiveData<Object> addPigeonPlayData = new MutableLiveData<>();
    //修改结果
    public MutableLiveData<Object> modifyPigeonPlayData = new MutableLiveData<>();
    //获取标准赛绩详情
    public MutableLiveData<PigeonPlayEntity> getPigeonPlayDatails = new MutableLiveData<>();

    //详情第一次数据不回调
    public boolean isOne = true;

    public Consumer<String> setPlayAdditionalInfo() {
        return s -> {
            this.playAdditionalInfo = s;
            if (isAdd) {
                isCanCommit2();
            } else {
                if (isOne) {
                    isOne = false;
                } else {
                    isCanCommit2();
                }
            }
        };
    }

    //赛绩录入
    public void addPigeonPlay() {

        if (isStandardPlay) {

            if (!StringUtil.isStringValid(playOrg)) {
                error(getString(R.string.tv_play_org));
                return;
            }

            if (!StringUtil.isStringValid(projectName)) {
                error(getString(R.string.tv_project_name));
                return;
            }

            if (!StringUtil.isStringValid(palyScale)) {
                error(getString(R.string.tv_paly_scale));
                return;
            }

            if (!StringUtil.isStringValid(palyRank)) {
                error(getString(R.string.tv_paly_rank));
                return;
            }

            if (!StringUtil.isStringValid(plyPlace)) {
                error(getString(R.string.tv_fly_place));
                return;
            }

            if (!StringUtil.isStringValid(plyUllage)) {
                error(getString(R.string.tv_fly_ullage));
                return;
            }

            if (!StringUtil.isStringValid(playTime)) {
                error(getString(R.string.tv_paly_time));
                return;
            }


            if (isAdd) {
                //添加
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
                //修改
                submitRequestThrowError(PlayModel.getTXGP_PigeonMatch_Modify(matchid, pigeonid,
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
//                        modifyPigeonPlayData.setValue(r.data);
                        hintDialog(r.msg);
                    } else throw new HttpErrorException(r);
                });
            }

        } else {

            if (!StringUtil.isStringValid(playAdditionalInfo)) {
                error(getString(R.string.tv_additional_info));
                return;
            }

            if (isAdd) {
                //添加
                submitRequestThrowError(PlayModel.getTXGP_PigeonInfoList_AddInfo(pigeonid,
                        footid,
                        playAdditionalInfo), r -> {
                    if (r.isOk()) {
                        addPigeonPlayData.setValue(r.data);
                    } else throw new HttpErrorException(r);
                });
            } else {
                //修改
                submitRequestThrowError(PlayModel.getTXGP_PigeonInfoList_ModifyInfo(infoid, pigeonid,
                        footid,
                        playAdditionalInfo), r -> {
                    if (r.isOk()) {
                        hintDialog(r.msg);
                    } else throw new HttpErrorException(r);
                });
            }
        }
    }

    //删除标准的赛绩
    public void delStandardPlay() {
        submitRequestThrowError(PlayModel.getTXGP_PigeonMatch_Delete(pigeonid,
                footid,
                matchid), r -> {
            if (r.isOk()) {
                showHintClosePage.setValue(new RestHintInfo.Builder().message(r.msg).isClosePage(true).cancelable(false).build());
            } else throw new HttpErrorException(r);
        });
    }


    //标准的赛绩 详情
    public void getStandardPlayDatails() {
        submitRequestThrowError(PlayModel.getTXGP_PigeonMatch_Select(pigeonid,
                footid,
                matchid), r -> {
            if (r.isOk()) {
                getPigeonPlayDatails.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //删除赛绩附加的信息
    public void delAdditionalPlay() {
        submitRequestThrowError(PlayModel.getTXGP_PigeonInfoList_Delete(pigeonid,
                footid,
                infoid), r -> {
            if (r.isOk()) {
                showHintClosePage.setValue(new RestHintInfo.Builder().message(r.msg).isClosePage(true).cancelable(false).build());
            } else throw new HttpErrorException(r);
        });
    }


    public void isCanCommit() {
        isCanCommit(playOrg, projectName, palyScale, palyRank, plyUllage, plyPlace, playTime);
    }

    public void isCanCommit2() {
        isCanCommit(playAdditionalInfo);
    }
}
