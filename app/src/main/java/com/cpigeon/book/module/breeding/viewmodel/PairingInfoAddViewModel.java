package com.cpigeon.book.module.breeding.viewmodel;

import com.base.base.BaseViewModel;
import com.base.entity.RestHintInfo;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PairingModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.service.EventBusService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/9/11.
 */

public class PairingInfoAddViewModel extends BaseViewModel {

    public PigeonEntity mPigeonEntity;

    //配偶环号  配对的足环号码
    public String pairingFoot;

    //性别
    public String sex;

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
    //是否是平台配对（1和2）   是否相亲配对
    public String bitpair = "2";
    //配对备注
    public String reamrk;

    public void isCanCommit() {
        isCanCommit(pairingFoot, pairingTime, featherColor, lineage);
    }

    public void getTXGP_PigeonBreed_AddData() {

        submitRequestThrowError(PairingModel.getTXGP_PigeonBreed_Add(mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID(),
                pairingFoot,
                lineage,
                featherColor,
                sex,
                pairingTime,
                weather,
                temper,
                hum,
                dir,
                bitpair,
                reamrk), r -> {
            if (r.isOk()) {
                EventBus.getDefault().post(EventBusService.PAIRING_INFO_REFRESH);
                showHintClosePage.setValue(new RestHintInfo.Builder().message(r.msg).isClosePage(true).cancelable(false).build());
            } else throw new HttpErrorException(r);
        });
    }

}
