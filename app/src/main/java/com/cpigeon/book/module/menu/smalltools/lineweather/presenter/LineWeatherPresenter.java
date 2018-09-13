package com.cpigeon.book.module.menu.smalltools.lineweather.presenter;


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


    //获取公棚坐标信息
    public void getTool_GetGongPengInfo(String str, Consumer<List<GetGongPengListEntity>> consumer) {
        submitRequestThrowError(ILineWeatherView.getTool_GetGongPengInfoData(str).map(r -> {
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


    //获取司放地信息
    public void getTool_GetSiFangDi(String str, Consumer<List<GetSiFangDiEntity>> consumer) {
        submitRequestThrowError(ILineWeatherView.getTool_GetSiFangDiData(str).map(r -> {
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


    //获取司放地信息
    public void getKongJuData(Map<String, String> body, Consumer<UllageToolEntity> consumer) {
        submitRequestThrowError(ILineWeatherView.getKongju(body).map(r -> {
            if (r.isOk()) {
                if (r.status) {
                    return r.data;
                } else {
                    return null;
                }
            } else {
                throw new HttpErrorException(r);
            }
        }), consumer);
    }
}
