package com.cpigeon.book.module.feedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.BaseFragment;
import com.base.base.BaseViewModel;
import com.base.entity.RestHintInfo;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.DrugUseCaseModel;
import com.cpigeon.book.model.entity.DrugUseCaseEntity;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.service.EventBusService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class DrugUseCaseViewModel extends BaseViewModel {

    public PigeonEntity mPigeonEntity;

    public FeedPigeonEntity mFeedPigeonEntity;

    public int typePag = 0; //0添加    1   编辑


    //疾病名称
    public List<SelectTypeEntity> mIllnessNameData;
    public String illnessName;
    //药品名称
    public List<SelectTypeEntity> mDrugNameData;
    public String drugName;
    //用药日期
    public String drugUseTime;
    //    //记录日期
//    public String recordTime;
    //用药后状态
    public List<SelectTypeEntity> mSelectTypes_drugAfterStatus;
    public String drugAfterStatus;
    //    //是否副作用
//    public String isHaveAfterResult;
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


    // 用药情况 添加
    public void getTXGP_PigeonDrug_AddData() {
        submitRequestThrowError(DrugUseCaseModel.getTXGP_PigeonDrug_Add(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                illnessName,//
                drugName,
                drugAfterStatus,
                "",//isHaveAfterResult,
                bodyTemp,
                drugUseTime,
                "",//  recordTime,
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

    // 用药情况 修改
    public void getTXGP_PigeonDrug_EditData() {
        submitRequestThrowError(DrugUseCaseModel.getTXGP_PigeonDrug_Edit(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                mFeedPigeonEntity.getViewID(),
                illnessName,//
                drugName,
                drugAfterStatus,
                "",//isHaveAfterResult,
                bodyTemp,
                drugUseTime,
                "",//  recordTime,
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


    public MutableLiveData<DrugUseCaseEntity> mDrugUseCaseDetails = new MutableLiveData<>();


    // 用药情况 详情
    public void getTXGP_PigeonDrug_SelectData() {
        submitRequestThrowError(DrugUseCaseModel.getTXGP_PigeonDrug_Select(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                mFeedPigeonEntity.getViewID()
        ), r -> {
            if (r.isOk()) {
                mDrugUseCaseDetails.postValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    // 用药情况 删除
    public void getTXGP_PigeonDrug_DeleteData() {
        submitRequestThrowError(DrugUseCaseModel.getTXGP_PigeonDrug_Delete(
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
            getTXGP_PigeonDrug_EditData();
        } else {
            isCanCommit(drugName, drugUseTime, drugAfterStatus);
        }
    }

}
