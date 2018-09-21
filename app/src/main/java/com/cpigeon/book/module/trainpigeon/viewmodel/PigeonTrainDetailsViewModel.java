package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.PigeonTrainDetailsEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/21.
 */

public class PigeonTrainDetailsViewModel extends BaseViewModel {

    public String footId;
    public String pigeonId;

    public MutableLiveData<List<PigeonTrainDetailsEntity>> mDataPigeonTrainDetails = new MutableLiveData<>();

    public PigeonTrainDetailsViewModel(Activity activity){
        footId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
        pigeonId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA_2);
    }

    public void getFootNumberTrainDetails(){
        submitRequestThrowError(TrainPigeonModel.getPigeonTrainDetails(footId, pigeonId), apiResponse -> {
            if(apiResponse.isOk()){
                listEmptyMessage.setValue(apiResponse.msg);
                mDataPigeonTrainDetails.setValue(apiResponse.data);
            }else throw new HttpErrorException(apiResponse);
        });
    }
}
