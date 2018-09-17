package com.cpigeon.book.module.feedpigeon.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.FeedPigeonModel;
import com.cpigeon.book.model.UseVaccineModel;
import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class UseVaccineViewModel extends BaseViewModel {

    public PigeonEntity mPigeonEntity;

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
                weather,
                temper,
                bodyTemperature,
                injectionTiem,
                injectionWhy,
                remark
        ), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });

    }

    public void isCanCommit() {
        isCanCommit(vaccineName, injectionTiem, bodyTemperature, injectionWhy);
    }

}
