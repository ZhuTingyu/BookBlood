package com.cpigeon.book.module.pigeonleague.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.LeagueModel;
import com.cpigeon.book.model.entity.LeagueDetailsEntity;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/25.
 */

public class PigeonMatchDetailsViewModel extends BaseViewModel {

    PigeonEntity mPigeonEntity;
    public MutableLiveData<List<LeagueDetailsEntity>> mDataLeague = new MutableLiveData<>();

    public PigeonMatchDetailsViewModel(Activity activity){
        mPigeonEntity = (PigeonEntity) activity.getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
    }

    public void getDetails(){
        submitRequestThrowError(LeagueModel.getLeagueDetails(mPigeonEntity.getPigeonID()
                , mPigeonEntity.getFootRingID()),r -> {
            if(r.isOk()){
                listEmptyMessage.setValue(r.msg);
                mDataLeague.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
