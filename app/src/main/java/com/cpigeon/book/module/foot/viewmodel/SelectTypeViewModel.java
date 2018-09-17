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
    public static final String TYPE_EYE = "1";//眼砂
    public static final String TYPE_FOOT_RING = "2";//足环类型
    public static final String TYPE_PIGEON = "3";//信鸽类型
    public static final String TYPE_COLOR_FEATHER = "4";//信鸽羽色
    public static final String TYPE_PIGEON_BLOOD = "5";//信鸽血统
    public static final String TYPE_SEX = "6";//信鸽性别
    public static final String TYPE_PIGEON_IMG = "7";//信鸽图片类型
    public static final String TYPE_FOOT_SOURCE = "8";//足环来源
    public static final String TYPE_PIGEON_SOURCE = "9";//信鸽来源
    public static final String STATE_FOOT_RING = "10";//足环状态
    public static final String STATE_STATE = "11";//信鸽状态
    public static final String STATE_MEDICATE = "12";//用药后状态
    public static final String STATE_TRAIN = "13";//训鸽状态
    public static final String STATE_PLAY_ORG = "14";//赛事组织


    public String selectType;
    public String selectTypes;
    public List<String> whichIds;

    public MutableLiveData<List<SelectTypeEntity>> mSelectTypeLiveData = new MutableLiveData<>();
    public MutableLiveData<SelectTypeEntity> mSelectTypeList = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Sex = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_FeatherColor = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_EyeSand = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Foot_Source = new MutableLiveData<>();//足环来源
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Pigeon_Source = new MutableLiveData<>();//信鸽来源
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Play_Org = new MutableLiveData<>();//赛事组织
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Lineage = new MutableLiveData<>();//血统
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_State = new MutableLiveData<>();//信鸽状态
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Medicate = new MutableLiveData<>();//用药后的状态
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_ImgType = new MutableLiveData<>();//图片类型


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
                mSelectType_EyeSand.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取  血统
    public void getSelectType_lineage() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_PIGEON_BLOOD), r -> {
            if (r.isOk()) {
                mSelectType_Lineage.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取  状态
    public void getSelectType_State() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.STATE_STATE), r -> {
            if (r.isOk()) {
                mSelectType_State.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取  信鸽图片类型
    public void getSelectType_ImgType() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_PIGEON_IMG), r -> {
            if (r.isOk()) {
                mSelectType_ImgType.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取足环来源
    public void getSelectType_Source() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_FOOT_SOURCE), r -> {
            if (r.isOk()) {
                mSelectType_Foot_Source.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取 信鸽来源
    public void getSelectType_PigeonSource() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_PIGEON_SOURCE), r -> {
            if (r.isOk()) {
                mSelectType_Pigeon_Source.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取 赛事组织
    public void getSelectType_PigeonPlay_Org() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.STATE_PLAY_ORG), r -> {
            if (r.isOk()) {
                mSelectType_Play_Org.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取 用药后的状态
    public void getSelectTypem__Medicate() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.STATE_MEDICATE), r -> {
            if (r.isOk()) {
                mSelectType_Play_Org.setValue(r.data);
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
