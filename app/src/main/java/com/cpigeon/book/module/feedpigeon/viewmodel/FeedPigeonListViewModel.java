package com.cpigeon.book.module.feedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.FeedPigeonModel;
import com.cpigeon.book.model.PairingModel;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.FeedPigeonStatistical;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class FeedPigeonListViewModel extends BaseViewModel {


    public PigeonEntity mPigeonEntity;

    public int pi = 1;
    public int ps = 50;

    public MutableLiveData<List<FeedPigeonEntity>> mFeedPigeonListData = new MutableLiveData<>();
    public MutableLiveData<FeedPigeonStatistical> mFeedPigeonStatistical = new MutableLiveData<>();

    //获取  养鸽记录  列表
    public void getTXGP_Pigeon_SelectRecordData() {
        submitRequestThrowError(FeedPigeonModel.getTXGP_Pigeon_SelectRecord(String.valueOf(pi),
                String.valueOf(ps),
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID()), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mFeedPigeonListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取 养鸽记录 统计
    public void getTXGP_Pigeon_SelectIDCountData() {

        submitRequestThrowError(FeedPigeonModel.getTXGP_Pigeon_SelectIDCount(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID()), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mFeedPigeonStatistical.setValue(r.data);
            } else throw new HttpErrorException(r);
        });

    }

}
