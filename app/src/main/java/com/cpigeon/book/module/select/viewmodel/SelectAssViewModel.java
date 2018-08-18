package com.cpigeon.book.module.select.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.AssModel;
import com.cpigeon.book.model.entity.AssEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public class SelectAssViewModel extends BaseViewModel {

    private String key;

    public MutableLiveData<List<AssEntity>> liveAss = new MutableLiveData<>();
    public MutableLiveData<List<AssEntity>> liveHistory = new MutableLiveData<>();

    public void getAssList(){
        submitRequestThrowError(AssModel.getAssList(key), r -> {
            if(r.isOk()){
                liveAss.setValue(r.data);
                listEmptyMessage.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
