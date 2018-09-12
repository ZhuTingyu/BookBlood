package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BloodBookModel;
import com.cpigeon.book.model.entity.BloodBookEntity;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class BookViewModel extends BaseViewModel {

    public String foodId;
    public String pigeonId;

    public MutableLiveData<BloodBookEntity> mBookLiveData = new MutableLiveData<>();

    public void getBloodBook() {
        submitRequestThrowError(BloodBookModel.getBloodBook4(foodId, pigeonId), r -> {
            if (r.isOk()) {
                mBookLiveData.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
