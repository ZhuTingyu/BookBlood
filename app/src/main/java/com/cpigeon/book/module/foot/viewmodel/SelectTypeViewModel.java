package com.cpigeon.book.module.foot.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.Lists;
import com.cpigeon.book.model.PigeonPublicModel;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;


/**
 * 不同模块 公用的接口 如： 羽色 ，眼砂  雌雄，血统
 * Created by Administrator on 2018/8/9.
 */
public class SelectTypeViewModel extends BaseViewModel {

    public static final String TIME = "10000";
    public static final String TYPE_EYE = "1";
    public static final String TYPE_FOOT_RING = "2";
    public static final String TYPE_PIGEON = "3";
    public static final String TYPE_COLOR_FEATHER = "4";
    public static final String TYPE_PIGEON_BLOOD = "5";
    public static final String TYPE_PIGEON_SOURCE = "9";
    public static final String TYPE_SEX = "6";
    public static final String STATE_FOOT_RING = "10";


    public String selectType;
    public String selectTypes;
    public List<String> whichIds;

    public MutableLiveData<List<SelectTypeEntity>> mSelectTypeLiveData = new MutableLiveData<>();
    public MutableLiveData<SelectTypeEntity> mSelectTypeList = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Sex = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_FeatherColor = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Foot_Source = new MutableLiveData<>();

    public void setSelectType(String type) {
        selectType = type;
    }

    public void setSelectType(String... type) {
        whichIds = Lists.newArrayList(type);
        selectTypes = Lists.appendStringByList(whichIds);
    }


    //获取  足环，种赛鸽的类型，状态，来源，羽色，血统，眼沙，性别
    public void getSelectType() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(selectType), r -> {
            if (r.isOk()) {
                mSelectTypeLiveData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取  性别
    public void getSelectType_Sex() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_SEX), r -> {
            if (r.isOk()) {
                mSelectType_Sex.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取  羽色
    public void getSelectType_FeatherColor() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_COLOR_FEATHER), r -> {
            if (r.isOk()) {
                mSelectType_FeatherColor.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取  眼砂
    public void getSelectType_eyeSand() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_EYE), r -> {
            if (r.isOk()) {
                mSelectType_FeatherColor.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取足环来源
    public void getSelectType_Source() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_PIGEON_SOURCE), r -> {
            if (r.isOk()) {
                mSelectType_Foot_Source.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }



    public void getSelectTypes() {
        submitRequestThrowError(PigeonPublicModel.getSelectMushType(selectTypes), r -> {
            if (r.isOk()) {
                mSelectTypeLiveData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


}
