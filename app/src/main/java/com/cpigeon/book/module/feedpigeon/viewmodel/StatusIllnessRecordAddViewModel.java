package com.cpigeon.book.module.feedpigeon.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.StatusIllnessRecordAddModel;
import com.cpigeon.book.model.UseVaccineModel;
import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class StatusIllnessRecordAddViewModel extends BaseViewModel {


    public PigeonEntity mPigeonEntity;

    //疾病名称
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

    // 疫苗注射 添加
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
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });

    }

    public void isCanCommit() {
        isCanCommit(illnessName, illnessSymptom, illnessTime);
    }

}
