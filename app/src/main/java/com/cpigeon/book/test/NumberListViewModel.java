package com.cpigeon.book.test;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/3/2.
 */

public class NumberListViewModel extends BaseViewModel{

    public int page = 1;
    public MutableLiveData<List<String>> number = new MutableLiveData<>();
    List<String> data;

    public NumberListViewModel(){}

    public void loadNumber(){
        submitRequestThrowError(NumberListModel.getNumber(page),r -> {
            if(r.isOk()){
                if(r.status){
                    number.setValue(r.data);
                }else number.setValue(Lists.newArrayList());
            }else throw new HttpErrorException(r);
        });
    }

    public void add() {

    }
}
