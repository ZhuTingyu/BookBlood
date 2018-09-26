package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * Created by Administrator on 2018/9/3.
 */

public class BreedPigeonDetailsViewModel extends BaseViewModel {

    public MutableLiveData<PigeonEntity> mBreedPigeonData = new MutableLiveData<>();
    public MutableLiveData<String> mDataAddApplyR = new MutableLiveData<>();
    public MutableLiveData<String> mDataCancelShareR = new MutableLiveData<>();


    public String footId;
    public String pigeonId;
    public String pUid = UserModel.getInstance().getUserId();

    public BreedPigeonDetailsViewModel(Activity activity) {
        footId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA_2);
        pigeonId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
        String uid = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA_3);
        if(StringUtil.isStringValid(uid)){
            pUid = uid;
        }
    }

    //获取 鸽子  详情
    public void getPigeonDetails() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_GetInfo(pigeonId, pUid), r -> {
            if (r.isOk()) {
                mBreedPigeonData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });

    }

    public void addApplyShareHall() {
        submitRequestThrowError(BreedPigeonModel.applyAddShareHall(pigeonId, footId), r -> {
            if(r.isOk()){
                mDataAddApplyR.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }

    public void cancelApplyShareHall() {
        submitRequestThrowError(BreedPigeonModel.cancelAddShareHall(pigeonId, footId), r -> {
            if(r.isOk()){
                mDataCancelShareR.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }
}
