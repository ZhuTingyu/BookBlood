package com.cpigeon.book.module.play.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PlayModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.LeagueDetailsEntity;
import com.cpigeon.book.model.entity.PigeonPlayEntity;
import com.cpigeon.book.model.entity.PlayAdditionalInfoEntity;

import java.util.List;

/**
 * 赛绩列表
 * Created by Administrator on 2018/9/4.
 */

public class PlayListViewModel extends BaseViewModel {


    //鸽子id
    public String pigeonid;
    //足环ID
    public String footid;

    public int pi = 1;
    public int ps = 99999;

    public MutableLiveData<List<PigeonPlayEntity>> mPigeonPlayListData = new MutableLiveData<>();
    public MutableLiveData<List<LeagueDetailsEntity>> mDataFristLeague = new MutableLiveData<>();


    //获取  赛绩列表
    public void getZGW_Users_GetLogData() {
        submitRequestThrowError(PlayModel.getTXGP_PigeonMatch_SelectAll(UserModel.getInstance().getUserId(), pigeonid, footid, String.valueOf(pi), String.valueOf(ps)), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPigeonPlayListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    public MutableLiveData<List<PlayAdditionalInfoEntity>> mPlayAdditionalInfoListData = new MutableLiveData<>();


    public int infoPi = 1;
    public int infoPs = 99999;

    //获取  赛绩 附加信息 列表
    public void getPlayAdditionalInfoList() {
        submitRequestThrowError(PlayModel.getTXGP_PigeonInfoList_SelectAll(UserModel.getInstance().getUserId(), pigeonid, footid, String.valueOf(infoPi), String.valueOf(infoPs)), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPlayAdditionalInfoListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    public void getFirstLeague() {
        submitRequestThrowError(PlayModel.getFirstLeague(), r -> {
            if (r.isOk()) {
                mDataFristLeague.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


}
