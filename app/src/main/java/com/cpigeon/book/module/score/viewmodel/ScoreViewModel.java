package com.cpigeon.book.module.score.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.ScoreModel;
import com.cpigeon.book.model.entity.LeagueDetailsEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonScoreItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/20 0020.
 */

public class ScoreViewModel extends BaseViewModel {


    public MutableLiveData<List<LeagueDetailsEntity>> mDataFristLeague = new MutableLiveData<>();
    public MutableLiveData<List<PigeonScoreItemEntity>> mDataScoreItem = new MutableLiveData<>();
    public MutableLiveData<String> mDataSetScoreR = new MutableLiveData<>();


    public PigeonEntity mPigeonEntity;


    public List<Integer> mListScore;

    public int AllScore;
    public String scoreId;//：评分项目ID
    public String score;//：分数字
    public String allScore;//：总评分


    public void getPigeonScoreItem() {
        submitRequestThrowError(ScoreModel.getPigeonScoreItem(mPigeonEntity.getPigeonID()), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mDataScoreItem.setValue(r.data);
                mListScore = new ArrayList<>(r.data.size());
            } else throw new HttpErrorException(r);
        });
    }

    public void setPigeonItemScore() {
        submitRequestThrowError(ScoreModel.setPigeonItemScore(
                mPigeonEntity.getPigeonID(),
                scoreId,
                score,
                allScore
        ), r -> {
            if (r.isOk()) {
                mDataSetScoreR.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

}
