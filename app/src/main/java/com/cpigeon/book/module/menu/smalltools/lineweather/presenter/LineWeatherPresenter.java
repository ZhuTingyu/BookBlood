package com.cpigeon.book.module.menu.smalltools.lineweather.presenter;


import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.Lists;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.AssociationListEntity;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.GetGongPengListEntity;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.GetSiFangDiEntity;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.UllageToolEntity;
import com.cpigeon.book.module.menu.smalltools.lineweather.view.viewdeo.ILineWeatherView;

import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/5/7.
 */

public class LineWeatherPresenter extends BaseViewModel {
    public MutableLiveData<List<GetGongPengListEntity>> mGetGongPengData = new MutableLiveData<>();
    public String gpStr;

    //获取公棚坐标信息
    public void getTool_GetGongPengInfo() {
        submitRequestThrowError(ILineWeatherView.getTool_GetGongPengInfoData(gpStr), r -> {
            if (r.isOk()) {
                mGetGongPengData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取协会信息
    public void getAssociationInfo(String str, String c, String p, Consumer<List<AssociationListEntity>> consumer) {
        submitRequestThrowError(ILineWeatherView.getAssociationInfoData(str, p, c).map(r -> {
            if (r.isOk()) {
                if (r.status) {
                    return r.data;
                } else {
                    return Lists.newArrayList();
                }
            } else {
                throw new HttpErrorException(r);
            }
        }), consumer);
    }


    public MutableLiveData<List<GetSiFangDiEntity>> mGetSiFangDiData = new MutableLiveData<>();


    public String sfdStr;

    //获取司放地信息
    public void getTool_GetSiFangDi() {
        submitRequestThrowError(ILineWeatherView.getTool_GetSiFangDiData(sfdStr), r -> {
            if (r.isOk()) {
                mGetSiFangDiData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    public MutableLiveData<UllageToolEntity> mUllageToolData = new MutableLiveData<>();
    public Map<String, String> body;

    //获取空距
    public void getKongJuData() {
        submitRequestThrowError(ILineWeatherView.getKongju(body), r -> {
            if (r.isOk()) {
                mUllageToolData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }
}
