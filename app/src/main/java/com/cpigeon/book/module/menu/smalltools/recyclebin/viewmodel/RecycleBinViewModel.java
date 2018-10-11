package com.cpigeon.book.module.menu.smalltools.recyclebin.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.RecycleBinModel;
import com.cpigeon.book.model.entity.RecycleBinEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */

public class RecycleBinViewModel extends BaseViewModel {


    public int pi = 1;
    public int ps = 50;

    public MutableLiveData<List<RecycleBinEntity>> mRecycleBinData = new MutableLiveData<>();

    //回收站 列表
    public void getZGW_Users_GetLogData() {
        submitRequestThrowError(RecycleBinModel.getTXGP_RecycleAllData(String.valueOf(pi), String.valueOf(ps)), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mRecycleBinData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //回收站  清空
    public void getTXGP_EmptyData() {
        submitRequestThrowError(RecycleBinModel.getTXGP_EmptyData(), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    public String recyid = "";

    //回收站  恢复
    public void getTXGP_RecoveryData() {
        submitRequestThrowError(RecycleBinModel.getTXGP_RecoveryData(recyid), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


}
