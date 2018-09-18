package com.cpigeon.book.module.feedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.BaseFragment;
import com.base.base.BaseViewModel;
import com.base.entity.RestHintInfo;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.FeedPigeonModel;
import com.cpigeon.book.model.UseVaccineModel;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.UseVaccineEntity;
import com.cpigeon.book.service.EventBusService;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class UseVaccineViewModel extends BaseViewModel {

    public PigeonEntity mPigeonEntity;
    public FeedPigeonEntity mFeedPigeonEntity;

    public int typePag = 0; //0添加    1   编辑

    //疫苗名称
    public String vaccineName;
    //注射日期
    public String injectionTiem;
    //体温
    public String bodyTemperature;
    //注射原因
    public String injectionWhy;

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


    // 疫苗注射 添加
    public void getTXGP_PigeonVaccine_AddData() {
        submitRequestThrowError(UseVaccineModel.getTXGP_PigeonVaccine_Add(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                vaccineName,
                bodyTemperature,
                injectionTiem,
                injectionWhy,
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

    // 疫苗注射 修改
    public void getTXGP_PigeonVaccine_UpdateData() {
        submitRequestThrowError(UseVaccineModel.getTXGP_PigeonVaccine_Update(
                mFeedPigeonEntity.getViewID(),
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                vaccineName,
                weather,
                temper,
                bodyTemperature,
                injectionTiem,
                injectionWhy,
                remark
        ), r -> {
            if (r.isOk()) {
                EventBus.getDefault().post(EventBusService.FEED_PIGEON_DETAILS_REFRESH);
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    public MutableLiveData<UseVaccineEntity> mUseVaccineDetails = new MutableLiveData<>();


    // 疫苗注射 详情
    public void getTXGP_PigeonVaccine_Select() {
        submitRequestThrowError(UseVaccineModel.getTXGP_PigeonVaccine_Select(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                mFeedPigeonEntity.getViewID()
        ), r -> {
            if (r.isOk()) {
                mUseVaccineDetails.postValue(r.data);
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
            getTXGP_PigeonVaccine_UpdateData();
        } else {
            //添加
            isCanCommit(vaccineName, injectionTiem, bodyTemperature, injectionWhy);
        }

    }


    // 疫苗注射 删除
    public void getTXGP_PigeonVaccine_DeleteData() {
        submitRequestThrowError(UseVaccineModel.getTXGP_PigeonVaccine_Delete(
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


}
