package com.cpigeon.book.module.feedpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.GrowthReportModel;
import com.cpigeon.book.model.entity.GrowthReportEntity;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * 成长记录
 * Created by Zhu TingYu on 2018/8/31.
 */

public class GrowthReportViewModel extends BaseViewModel {

    public PigeonEntity mPigeonEntity;

    public int pi = 1;
    public int ps = 15;

    public MutableLiveData<List<GrowthReportEntity>> mGrowthReportListData = new MutableLiveData<>();

    //获取  养鸽记录  列表
    public void getTXGP_Pigeon_SelectGrowAllData() {
        submitRequestThrowError(GrowthReportModel.getTXGP_Pigeon_SelectGrowAll(String.valueOf(pi),
                String.valueOf(ps),
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID()), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mGrowthReportListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
