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
    public static final String TYPE_SEX = "4";//信鸽性别
    public static final String TYPE_PIGEON_IMG = "5";//信鸽图片类型
    public static final String TYPE_FOOT_SOURCE = "6";//足环来源
    public static final String TYPE_PIGEON_SOURCE = "7";//信鸽来源
    public static final String STATE_FOOT_RING = "8";//足环状态
    public static final String STATE_STATE = "9";//信鸽状态
    public static final String STATE_MEDICATE = "10";//用药后状态
    public static final String STATE_TRAIN = "11";//训鸽状态
    public static final String VACCINE_NAME = "12";//疫苗名称
    public static final String VACCINE_REASON = "13";//疫苗注射原因
    public static final String STATE_PLAY_ORG = "14";//赛事组织
    public static final String TYPE_COLOR_FEATHER = "15";//信鸽羽色
    public static final String TYPE_PIGEON_BLOOD = "16";//信鸽血统
    public static final String LINESS_NAME = "17";//病症名称
    public static final String DRUG_NAME = "18";//药品名称
    public static final String CARE_DRUG_NAME = "19";//保健品名称
    public static final String TYPE_DEATH_REASON = "20";//死亡原因


    public String selectType;
    public String selectTypes;
    public List<String> whichIds;

    public MutableLiveData<List<SelectTypeEntity>> mSelectTypeLiveData = new MutableLiveData<>();
    public MutableLiveData<SelectTypeEntity> mSelectTypeList = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Sex = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Foot_Ring = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_FeatherColor = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_EyeSand = new MutableLiveData<>();
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Foot_Source = new MutableLiveData<>();//足环来源
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Pigeon_Source = new MutableLiveData<>();//信鸽来源
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Play_Org = new MutableLiveData<>();//赛事组织
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Lineage = new MutableLiveData<>();//血统
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_State = new MutableLiveData<>();//信鸽状态
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_Medicate = new MutableLiveData<>();//用药后的状态
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_ImgType = new MutableLiveData<>();//图片类型
    public MutableLiveData<List<SelectTypeEntity>> mSelectType_PigeonType = new MutableLiveData<>();//信鸽类型
    public MutableLiveData<List<SelectTypeEntity>> mVaccineReason = new MutableLiveData<>();//疫苗注射原因
    public MutableLiveData<List<SelectTypeEntity>> mVaccineName = new MutableLiveData<>();//疫苗名称
    public MutableLiveData<List<SelectTypeEntity>> mLinessName = new MutableLiveData<>();//疾病名称
    public MutableLiveData<List<SelectTypeEntity>> mCareDrugName = new MutableLiveData<>();//保健品名称
    public MutableLiveData<List<SelectTypeEntity>> mDrugNameData = new MutableLiveData<>();//药品名称
    public MutableLiveData<List<SelectTypeEntity>> mDeathReason = new MutableLiveData<>();//死亡原因


    private String key;

    public void setSelectType(String type) {
        selectType = type;
    }

    public void setSelectType(String... type) {
        whichIds = Lists.newArrayList(type);
        selectTypes = Lists.appendStringByList(whichIds);
    }


    //获取  足环，种赛鸽的类型，状态，来源，羽色，血统，眼沙，性别
    public void getSelectType() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(selectType,key), r -> {
            if (r.isOk()) {
                mSelectTypeLiveData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //足环类型
    public void getSelectType_Foot_Ring() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_FOOT_RING,key), r -> {
            if (r.isOk()) {
                mSelectType_Foot_Ring.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取  性别
    public void getSelectType_Sex() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_SEX,key), r -> {
            if (r.isOk()) {
                mSelectType_Sex.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取  羽色
    public void getSelectType_FeatherColor() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_COLOR_FEATHER,key), r -> {
            if (r.isOk()) {
                mSelectType_FeatherColor.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取  眼砂
    public void getSelectType_eyeSand() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_EYE,key), r -> {
            if (r.isOk()) {
                mSelectType_EyeSand.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取  血统
    public void getSelectType_lineage() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_PIGEON_BLOOD,key), r -> {
            if (r.isOk()) {
                mSelectType_Lineage.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取  状态
    public void getSelectType_State() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.STATE_STATE,key), r -> {
            if (r.isOk()) {
                mSelectType_State.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取  信鸽图片类型
    public void getSelectType_ImgType() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_PIGEON_IMG,key), r -> {
            if (r.isOk()) {
                mSelectType_ImgType.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取足环来源
    public void getSelectType_Source() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_FOOT_SOURCE,key), r -> {
            if (r.isOk()) {
                mSelectType_Foot_Source.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取 信鸽来源
    public void getSelectType_PigeonSource() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_PIGEON_SOURCE,key), r -> {
            if (r.isOk()) {
                mSelectType_Pigeon_Source.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取 赛事组织
    public void getSelectType_PigeonPlay_Org() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.STATE_PLAY_ORG,key), r -> {
            if (r.isOk()) {
                mSelectType_Play_Org.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取 用药后的状态
    public void getSelectTypem_Medicate() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.STATE_MEDICATE,key), r -> {
            if (r.isOk()) {
                mSelectType_Medicate.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取 信鸽类型
    public void getPigeonType() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_PIGEON,key), r -> {
            if (r.isOk()) {
                mSelectType_PigeonType.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取 疫苗注射原因
    public void getVaccineReason() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.VACCINE_REASON,key), r -> {
            if (r.isOk()) {
                mVaccineReason.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取 疫苗名称
    public void getVaccineName() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.VACCINE_NAME,key), r -> {
            if (r.isOk()) {
                mVaccineName.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取 病症名称
    public void getLiness_Name() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.LINESS_NAME,key), r -> {
            if (r.isOk()) {
                mLinessName.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取 保健品名称
    public void getCareDrugName() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.CARE_DRUG_NAME,key), r -> {
            if (r.isOk()) {
                mCareDrugName.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取 药品名称
    public void getDrug_Name() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.DRUG_NAME,key), r -> {
            if (r.isOk()) {
                mDrugNameData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取 死亡原因
    public void getDeathReason() {
        submitRequestThrowError(PigeonPublicModel.getTXGP_Type_Select(SelectTypeViewModel.TYPE_DEATH_REASON,key), r -> {
            if (r.isOk()) {
                mDeathReason.setValue(r.data);
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
