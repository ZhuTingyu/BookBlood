package com.cpigeon.book.module.select.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.AssModel;
import com.cpigeon.book.model.entity.AssEntity;
import com.cpigeon.book.model.entity.LoftEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public class SelectAssViewModel extends BaseViewModel {

    private String key;

    //协会
    public MutableLiveData<List<AssEntity>> liveAss = new MutableLiveData<>();

    //公棚
    public MutableLiveData<List<LoftEntity>> liveLoft = new MutableLiveData<>();

    //协会数据
    public void getAssList() {
        submitRequestThrowError(AssModel.getAssList(key), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                liveAss.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //公棚列表数据
    public void getLoftList() {
        submitRequestThrowError(AssModel.getLoftList(key), r -> {
            if (r.isOk()) {
                liveLoft.setValue(r.data);
                listEmptyMessage.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
