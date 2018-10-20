package com.cpigeon.book.module.select.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/20.
 */

public class SelectParentFootRingViewModel extends BaseViewModel {
    public String mFootNumber;
    public String mSexId;
    public MutableLiveData<List<PigeonEntity>> mDataPigeon = new MutableLiveData<>();

    public void getPgieon(){
        submitRequestThrowError(BreedPigeonModel.getPigeonByFootNumber(mFootNumber, mSexId),r -> {
            if(r.isOk()){
                mDataPigeon.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
