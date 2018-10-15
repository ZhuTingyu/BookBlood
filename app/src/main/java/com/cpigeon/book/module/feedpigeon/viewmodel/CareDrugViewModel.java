package com.cpigeon.book.module.feedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.BaseFragment;
import com.base.base.BaseViewModel;
import com.base.entity.RestHintInfo;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.CareDrugModel;
import com.cpigeon.book.model.entity.CareDrugEntity;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.service.EventBusService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/9/18 0018.
 */

public class CareDrugViewModel extends BaseViewModel {

    public PigeonEntity mPigeonEntity;
    public FeedPigeonEntity mFeedPigeonEntity;

    public int typePag = 0; //0添加    1   编辑

    //保健品名称
    public List<SelectTypeEntity> mCareDrugNameSelect;
    public String careDrugName;
    public String careDrugNameId;
    //保健品功能
    public String careDrugFunction;
    //使用效果
    public String useEffect;

    //使用时间
    public String useTime;
    //记录时间
    public String recordTime;


    //是否副作用
    public String isHaveAfterResult;
    //体温
    public String bodyTemp;
    //配对天气
    public String weather;
    //配对气温
    public String temper;
    //配对湿度
    public String hum;
    //配对风向
    public String dir;
    //备注
    public String remark;

    // 保健品记录 添加
    public void getTXGP_PigeonHealth_AddData() {
        submitRequestThrowError(CareDrugModel.getTXGP_PigeonHealth_Add(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                careDrugNameId,
                careDrugFunction,

                useEffect,
                isHaveAfterResult,

                useTime,
                recordTime,
                bodyTemp,

                weather,
                temper,
                hum,
                dir,
                remark
        ), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
                EventBus.getDefault().post(EventBusService.FEED_PIGEON_DETAILS_REFRESH);
            } else throw new HttpErrorException(r);
        });
    }


    // 保健品记录 修改
    public void getTXGP_PigeonHealth_UpdateData() {
        submitRequestThrowError(CareDrugModel.getTXGP_PigeonHealth_Update(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                mFeedPigeonEntity.getViewID(),
                careDrugNameId,
                careDrugFunction,

                useEffect,
                isHaveAfterResult,

                useTime,
                recordTime,
                bodyTemp,

                weather,
                temper,
                hum,
                dir,
                remark
        ), r -> {
            if (r.isOk()) {
                EventBus.getDefault().post(EventBusService.FEED_PIGEON_DETAILS_REFRESH);
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    public MutableLiveData<CareDrugEntity> mCareDrugDetails = new MutableLiveData<>();

    // 保健品记录 详情
    public void getTXGP_PigeonHealth_SelectData() {
        submitRequestThrowError(CareDrugModel.getTXGP_PigeonHealth_Select(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                mFeedPigeonEntity.getViewID()
        ), r -> {
            if (r.isOk()) {
                mCareDrugDetails.postValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    // 保健品记录 删除
    public void getTXGP_PigeonHealth_DeleteData() {
        submitRequestThrowError(CareDrugModel.getTXGP_PigeonHealth_Delete(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                mFeedPigeonEntity.getViewID()
        ), r -> {
            if (r.isOk()) {
                EventBus.getDefault().post(EventBusService.FEED_PIGEON_DETAILS_REFRESH);
                showHintClosePage.setValue(new RestHintInfo.Builder().message(r.msg).cancelable(false).isClosePage(true).build());
            } else throw new HttpErrorException(r);
        });
    }

    private BaseFragment mBaseFragment;

    public void setmBaseFragment(BaseFragment mBaseFragment) {
        this.mBaseFragment = mBaseFragment;
    }

    public void isCanCommit() {

        if (typePag == 1) {
            //编辑
            mBaseFragment.setProgressVisible(true);//加载框
            getTXGP_PigeonHealth_UpdateData();
        } else {
            isCanCommit(careDrugName, careDrugFunction, recordTime, useTime);
        }
    }
}
