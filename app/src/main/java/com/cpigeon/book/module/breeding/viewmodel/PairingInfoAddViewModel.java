package com.cpigeon.book.module.breeding.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PairingModel;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/11.
 */

public class PairingInfoAddViewModel extends BaseViewModel {

    public BreedPigeonEntity mBreedPigeonEntity;

    //配偶环号
    public String pairingFoot;

    //母足环号码
    public String wofootnum;
    //父足环号码
    public String menfootnum;
    //羽色
    public List<SelectTypeEntity> mSelectTypes_FeatherColor;
    public String featherColor = "";

    //血统
    public List<SelectTypeEntity> mSelectTypes_Lineage;
    public String lineage = "";

    //配对时间
    public String pairingTime;
    //配对天气
    public String weather;
    //配对气温
    public String temper;
    //配对湿度
    public String hum;
    //配对风向
    public String dir;
    //是否是平台配对（1和2）
    public String reamrk;
    //配对备注
    public String bitpair;

    public void isCanCommit() {
        isCanCommit(pairingFoot, pairingTime, featherColor, lineage);
    }

    public void getTXGP_PigeonBreed_AddData() {

        if (mBreedPigeonEntity.getPigeonSexName().equals("雌")) {
            //
            wofootnum = mBreedPigeonEntity.getFootRingNum();
            menfootnum = pairingFoot;
        } else {
            wofootnum = pairingFoot;
            menfootnum = mBreedPigeonEntity.getFootRingNum();
        }

        submitRequestThrowError(PairingModel.getTXGP_PigeonBreed_Add(wofootnum, menfootnum, pairingTime, weather, temper, hum, dir, bitpair, reamrk), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

}
