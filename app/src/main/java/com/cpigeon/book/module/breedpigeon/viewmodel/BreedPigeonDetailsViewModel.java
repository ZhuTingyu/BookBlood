package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * Created by Administrator on 2018/9/3.
 */

public class BreedPigeonDetailsViewModel extends BaseViewModel {

    public MutableLiveData<PigeonEntity> mBreedPigeonData = new MutableLiveData<>();


    public String footId;
    public String pigeonId;

    public BreedPigeonDetailsViewModel(Activity activity) {
        footId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA_2);
        pigeonId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
    }

    //获取 鸽子  详情
    public void getPigeonDetails() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_GetInfo(pigeonId, UserModel.getInstance().getUserId()), r -> {
            if (r.isOk()) {
                mBreedPigeonData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });

    }
}
