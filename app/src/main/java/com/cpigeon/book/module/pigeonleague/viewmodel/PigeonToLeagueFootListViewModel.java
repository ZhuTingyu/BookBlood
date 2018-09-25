package com.cpigeon.book.module.pigeonleague.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.Lists;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.model.LeagueModel;
import com.cpigeon.book.model.entity.LeagueStatEntity;

/**
 * Created by Zhu TingYu on 2018/9/25.
 */

public class PigeonToLeagueFootListViewModel extends BaseViewModel{

    public MutableLiveData<LeagueStatEntity> mDataLeague = new MutableLiveData<>();

    public void getLeagueEntity(){
        submitRequestThrowError(LeagueModel.getLeagueStatEntity(StringUtil.emptyString()),r -> {
            if(r.isOk()){
                mDataLeague.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
