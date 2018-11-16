package com.cpigeon.book.module.menu.service.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.ServiceModel;
import com.cpigeon.book.model.entity.ServiceEntity;
import com.cpigeon.book.model.entity.ServiceInfoEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/29.
 */

public class OpenServiceViewModel extends BaseViewModel {
    public MutableLiveData<List<ServiceEntity>> mDataNotServiceList = new MutableLiveData<>();
    public MutableLiveData<List<ServiceEntity>> mDataOpenServiceList = new MutableLiveData<>();

    public double balance;
    public double score;

    public void getServiceInfo() {
        submitRequestThrowError(ServiceModel.getServiceInfo(), r -> {
            if (r.isOk()) {
                balance = r.data.getYu();
                score = r.data.getGb();
                mDataOpenServiceList.setValue(r.getData().getServices());
                mDataNotServiceList.setValue(r.getData().getServices_no());
            } else throw new HttpErrorException(r);
        });
    }
}
