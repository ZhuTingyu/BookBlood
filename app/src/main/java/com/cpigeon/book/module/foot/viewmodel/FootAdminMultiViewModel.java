package com.cpigeon.book.module.foot.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.FootAdminModel;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/8/21.
 */

public class FootAdminMultiViewModel extends BaseViewModel {

    private String footId;
    private String startFoot;
    private String count = "0";
    public String typeId;
    private String source;
    public String cityCode = "2";
    private String money;
    private String remark;

    public List<SelectTypeEntity> mSelectTypes;

    public MutableLiveData<String> addR = new MutableLiveData<>();
    public MutableLiveData<String> deleteR = new MutableLiveData<>();
    public MutableLiveData<FootEntity> mFootEntityLiveData = new MutableLiveData<>();

    public FootAdminMultiViewModel(Activity activity) {
        footId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
    }

    public void getFootInfo(){
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_Select(footId),r -> {
            if(r.isOk()){
                mFootEntityLiveData.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }

    public void addMultiFoot() {
        submitRequestThrowError(FootAdminModel.addMultiFoot(startFoot, count
                , typeId, source, cityCode, money, remark),r -> {
            if(r.isOk()){
                addR.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }

    public Consumer<String> setStartFoot() {
        return s -> {
            startFoot = s;
            isCanCommit();
        };
    }

    public Consumer<String> setCount() {
        return s -> {
            count = s;
            isCanCommit();
        };
    }

    public Consumer<String> setSource() {
        return s -> {
            source = s;
            isCanCommit();
        };
    }


    public Consumer<String> setMoney() {
        return s -> {
            money = s;
            isCanCommit();
        };
    }

    public Consumer<String> setRemark() {
        return s -> {
            remark = s;
            isCanCommit();
        };
    }

    public void isCanCommit(){
        isCanCommit(startFoot, count, startFoot, money);
    }
}
