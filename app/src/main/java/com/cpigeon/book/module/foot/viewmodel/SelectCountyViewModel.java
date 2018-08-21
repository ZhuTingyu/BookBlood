package com.cpigeon.book.module.foot.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseActivity;
import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.SelectCountyModel;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.model.entity.CountyEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class SelectCountyViewModel extends BaseViewModel {
    public String id;

    public MutableLiveData<List<CountyEntity>> mLiveData = new MutableLiveData<>();
    public MutableLiveData<List<CountyAreaEntity>> mAreaLiveData = new MutableLiveData<>();

    public SelectCountyViewModel(BaseActivity activity){
        id = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
    }

    public void getCountyList(){
        submitRequestThrowError(SelectCountyModel.getCountyList(), r -> {
            if(r.isOk()){
                mLiveData.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }

    public void getAreaList(){
        submitRequestThrowError(SelectCountyModel.getAreaList(id), r -> {
            if(r.isOk()){
                mAreaLiveData.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
