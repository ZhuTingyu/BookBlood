package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.model.BloodBookModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.BloodBookEntity;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class BookViewModel extends BaseViewModel {

    public String foodId;
    public String pigeonId;
    public boolean isNeedMatch = false;
    public boolean isGoodPigeon = false;
    public String sexId;
    public String sonFootId;
    public String sonPigeonId;


    public BloodBookEntity mBloodBookEntity;

    public MutableLiveData<BloodBookEntity> mBookLiveData = new MutableLiveData<>();
    public MutableLiveData<String> mDataAddParentR = new MutableLiveData<>();
    public String pUid = UserModel.getInstance().getUserId();

    public BookViewModel(Activity activity) {
        foodId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
        pigeonId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA_2);
        String uid = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA_3);
        if (StringUtil.isStringValid(uid)) {
            pUid = uid;
        }
    }

    public BookViewModel() {}


    //获取 血统书  四代
    public void getBloodBook() {
        submitRequestThrowError(BloodBookModel.getBloodBook4(pUid, foodId, pigeonId, isNeedMatch, isGoodPigeon), r -> {
            if (r.isOk()) {
                mBloodBookEntity = r.data;
                mBookLiveData.setValue(mBloodBookEntity);
            } else throw new HttpErrorException(r);
        });
    }

    public void addParent(){
        submitRequestThrowError(BloodBookModel.addParent(pigeonId, foodId, sexId, sonFootId, sonPigeonId), r -> {
            if(r.isOk()){
                mDataAddParentR.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }
}
