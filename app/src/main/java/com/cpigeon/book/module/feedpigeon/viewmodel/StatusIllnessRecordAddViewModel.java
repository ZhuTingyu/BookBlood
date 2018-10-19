package com.cpigeon.book.module.feedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.BaseFragment;
import com.base.base.BaseViewModel;
import com.base.entity.RestHintInfo;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.StatusIllnessRecordAddModel;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.model.entity.StatusIllnessRecordEntity;
import com.cpigeon.book.service.EventBusService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class StatusIllnessRecordAddViewModel extends BaseViewModel {


    public PigeonEntity mPigeonEntity;

    public FeedPigeonEntity mFeedPigeonEntity;

    public int typePag = 0; //0添加    1   编辑

    //疾病名称
    public  List<SelectTypeEntity>  mIllnessNameData;
    public String illnessName;
    //症状
    public String illnessSymptom;
    //生病日期
    public String illnessTime;
    //体温
    public String bodyTemperature;

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

    public MutableLiveData<Object> mVaccineAdd = new MutableLiveData<>();

    // 病情记录 添加
    public void getTXGP_PigeonVaccine_AddData() {
        submitRequestThrowError(StatusIllnessRecordAddModel.getTXGP_PigeonDisease_Add(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                illnessName,
                illnessSymptom,
                weather,
                temper,
                bodyTemperature,
                illnessTime,
                hum,
                dir,
                remark
        ), r -> {
            if (r.isOk()) {
                EventBus.getDefault().post(EventBusService.FEED_PIGEON_DETAILS_REFRESH);
                hintDialog(r.msg);
                mVaccineAdd.setValue(r);
            } else throw new HttpErrorException(r);
        });

    }

    //病情记录  修改
    public void getTXGP_PigeonDisease_EditData() {
        submitRequestThrowError(StatusIllnessRecordAddModel.getTXGP_PigeonDisease_Edit(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                mFeedPigeonEntity.getViewID(),
                illnessName,
                illnessSymptom,
                weather,
                temper,
                bodyTemperature,
                illnessTime,
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


    public MutableLiveData<StatusIllnessRecordEntity> mStatusIllnessRecordDetails = new MutableLiveData<>();

    //病情记录  详情
    public void getTXGP_PigeonDisease_SelectData() {
        submitRequestThrowError(StatusIllnessRecordAddModel.getTXGP_PigeonDisease_Select(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                mFeedPigeonEntity.getViewID()
        ), r -> {
            if (r.isOk()) {
                mStatusIllnessRecordDetails.postValue(r.data);
            } else throw new HttpErrorException(r);
        });

    }


    //病情记录  删除
    public void getTXGP_Delete_PigeonDiseaseData() {
        submitRequestThrowError(StatusIllnessRecordAddModel.getTXGP_Delete_PigeonDisease(
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
            mBaseFragment.setProgressVisible(true);//加载框
            getTXGP_PigeonDisease_EditData();
        } else {
            isCanCommit(illnessName, illnessSymptom, illnessTime);
        }
    }

}
