package com.cpigeon.book.module.feedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.DrugUseCaseModel;
import com.cpigeon.book.model.FeedPigeonModel;
import com.cpigeon.book.model.UseVaccineModel;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.FeedPigeonStatistical;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class DrugUseCaseViewModel extends BaseViewModel {

    public PigeonEntity mPigeonEntity;

    //病情记录
    public String illnessRecord;
    //药品名称
    public String drugName;
    //用药日期
    public String drugUseTime;
    //记录日期
    public String recordTime;
    //用药后状态
    public List<SelectTypeEntity> mSelectTypes_drugAfterStatus;
    public String drugAfterStatus;
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


    // 疫苗注射 添加
    public void getTXGP_PigeonDrug_AddData() {
        submitRequestThrowError(DrugUseCaseModel.getTXGP_PigeonDrug_Add(
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                illnessRecord,
                drugName,
                drugAfterStatus,
                isHaveAfterResult,
                bodyTemp,
                drugUseTime,
                recordTime,
                weather,
                temper,
                hum,
                dir,
                remark
        ), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });

    }

    public void isCanCommit() {
        isCanCommit(illnessRecord, drugName, drugUseTime, recordTime, drugAfterStatus, isHaveAfterResult);
    }

}
