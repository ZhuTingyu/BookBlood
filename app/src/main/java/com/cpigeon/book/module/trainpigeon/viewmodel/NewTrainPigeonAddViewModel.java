package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.Lists;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/15.
 */

public class NewTrainPigeonAddViewModel extends BaseViewModel {

    public int pi = 1;
    int ps = 100000;

    public String footNumber;

    public List<PigeonEntity> mAllPigeon = null;
    public List<PigeonEntity> mSelectPigeon = Lists.newArrayList();
    public MutableLiveData<List<PigeonEntity>> mDataSelectPigeon = new MutableLiveData<>();
    public MutableLiveData<List<PigeonEntity>> mDataAllPigeon = new MutableLiveData<>();

    public void getPigeonList() {
        submitRequestThrowError(TrainPigeonModel.getAllPigeons(pi, ps, PigeonEntity.ID_MATCH_PIGEON
                , null, null, null, null, footNumber), r -> {
            if (r.isOk()) {
                mAllPigeon = r.data;
                mDataAllPigeon.setValue(mAllPigeon);
            } else throw new HttpErrorException(r);
        });
    }

    public void setSelectAll(List<PigeonEntity> notSelectAll) {
        mSelectPigeon.addAll(notSelectAll);
        mDataSelectPigeon.setValue(mSelectPigeon);
    }

    public int removeSelect(int removePosition) {
        PigeonEntity remove = mSelectPigeon.get(removePosition);
        mSelectPigeon.remove(removePosition);
        mDataSelectPigeon.setValue(mSelectPigeon);
        for (int i = 0, len = mAllPigeon.size(); i < len; i++) {
            PigeonEntity entity = mAllPigeon.get(i);
            if (entity.getFootRingNum().equals(remove.getFootRingNum())) {
                entity.setSelecte(false);
                return i;
            }
        }
        return -1;
    }

    public void setSelect(int selectPosition) {
        PigeonEntity entity = mAllPigeon.get(selectPosition);
        entity.setSelecte(true);
        mSelectPigeon.add(entity);
        mDataSelectPigeon.setValue(mSelectPigeon);
    }
}
