package com.cpigeon.book.module.score.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.ScoreModel;
import com.cpigeon.book.model.entity.LeagueDetailsEntity;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/10/20 0020.
 */

public class ScoreViewModel extends BaseViewModel {


    public MutableLiveData<List<LeagueDetailsEntity>> mDataFristLeague = new MutableLiveData<>();


    public PigeonEntity mPigeonEntity;

    public String score;


    //获取  修改鸽子评分
    public void getZGW_Users_GetLogData() {
        submitRequestThrowError(ScoreModel.getTXGP_Pigeon_UpdateScore(mPigeonEntity.getPigeonID(), mPigeonEntity.getFootRingID(), score), r -> {
            if (r.isOk()) {
//              hintDialog(r.msg);
//                EventBus.getDefault().post(new PigeonAddEvent());
            } else throw new HttpErrorException(r);
        });
    }

}
