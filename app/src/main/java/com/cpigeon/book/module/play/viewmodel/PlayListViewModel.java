package com.cpigeon.book.module.play.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PlayModel;
import com.cpigeon.book.model.entity.PigeonPlayEntity;

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
    public int ps = 15;

    public MutableLiveData<List<PigeonPlayEntity>> mPigeonPlayListData = new MutableLiveData<>();


    //获取  赛绩列表
    public void getZGW_Users_GetLogData() {
        submitRequestThrowError(PlayModel.getTXGP_PigeonMatch_SelectAll(pigeonid, footid, String.valueOf(pi), String.valueOf(ps)), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPigeonPlayListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
