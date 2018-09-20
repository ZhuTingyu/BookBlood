package com.cpigeon.book.module.breeding.viewmodel;

import android.util.Log;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PairingModel;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;
import com.cpigeon.book.module.breeding.adapter.OffspringInfoAdapter;
import com.cpigeon.book.service.EventBusService;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/9/20 0020.
 */

public class PairingNestViewModel extends BaseViewModel {


    public PairingNestInfoEntity mPairingNestInfoEntity;
    //子代鸽子id
    public String pigeonidstr;
    //子代足环ID
    public String footidstr;
    //修改子代信息
    public OffspringInfoAdapter mOffspringInfoAdapter;


    //窝次信息 删除
    public void getTXGP_PigeonBreedNest_DeleteData() {
        submitRequestThrowError(PairingModel.getTXGP_PigeonBreedNest_Delete(
                mPairingNestInfoEntity.getPigeonBreedID(),
                mPairingNestInfoEntity.getPigeonBreedNestID()), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
                EventBus.getDefault().post(EventBusService.PAIRING_INFO_REFRESH);
            } else throw new HttpErrorException(r);
        });
    }


    //窝次信息 修改
    public void getTXGP_PigeonBreedNest_UpdateData() {
        setIdStr(mOffspringInfoAdapter);
        submitRequestThrowError(PairingModel.getTXGP_PigeonBreedNest_Update(
                mPairingNestInfoEntity.getPigeonBreedNestID(),
                mPairingNestInfoEntity.getPigeonBreedID(),
                mPairingNestInfoEntity.getPigeonBreedTime(),
                mPairingNestInfoEntity.getLayEggTime(),
                mPairingNestInfoEntity.getInseEggCount(),
                mPairingNestInfoEntity.getFertEggCount(),
                mPairingNestInfoEntity.getEggWeather(),
                mPairingNestInfoEntity.getEggTemperature(),
                mPairingNestInfoEntity.getEggHumidity(),
                mPairingNestInfoEntity.getEggDirection(),
                mPairingNestInfoEntity.getOutShellTime(),
                mPairingNestInfoEntity.getOutShellCount(),
                pigeonidstr,
                footidstr,
                mPairingNestInfoEntity.getOutWeather(),
                mPairingNestInfoEntity.getOutTemperature(),
                mPairingNestInfoEntity.getOutHumidity(),
                mPairingNestInfoEntity.getOutDirection(),
                ""
        ), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
                EventBus.getDefault().post(EventBusService.PAIRING_INFO_REFRESH);
            } else throw new HttpErrorException(r);
        });
    }


    public void setIdStr(OffspringInfoAdapter mOffspringInfoAdapter) {

        pigeonidstr = "";
        footidstr = "";

        try {
            int size = mOffspringInfoAdapter.getData().size();
            for (int i = 0; i < size; i++) {
                pigeonidstr += mOffspringInfoAdapter.getData().get(i).getPigeonID() + ",";
                footidstr += mOffspringInfoAdapter.getData().get(i).getFootRingID() + ",";
            }

            Log.d("sdfasdfa", "setIdStr: " + pigeonidstr);

            pigeonidstr = pigeonidstr.substring(0, pigeonidstr.length() - 1);
            footidstr = footidstr.substring(0, footidstr.length() - 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("sdfasdfa", "setIdStr: " + pigeonidstr);
    }


}
