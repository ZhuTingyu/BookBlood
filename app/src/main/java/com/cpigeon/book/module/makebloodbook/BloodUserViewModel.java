package com.cpigeon.book.module.makebloodbook;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BloodBookModel;
import com.cpigeon.book.model.LoginModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.BloodUserCountEntity;

import io.reactivex.functions.Consumer;

public class BloodUserViewModel extends BaseViewModel {


    public MutableLiveData<BloodUserCountEntity> count = new MutableLiveData<>();

    public void getBloodNum() {
        submitRequestThrowError(BloodBookModel.getBloodNum(), r -> {
            if (r.isOk()) {
                count.setValue(r.data);
                normalResult.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

}
