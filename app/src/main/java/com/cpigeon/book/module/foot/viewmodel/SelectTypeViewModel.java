package com.cpigeon.book.module.foot.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.Lists;
import com.base.util.utility.LogUtil;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.model.PigeonPublicModel;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

import io.reactivex.functions.Consumer;

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
    public static final String STATE_FOOT_RING = "10";


    public String selectType;
    public String selectTypes;
    public List<String> whichIds;

    public MutableLiveData<List<SelectTypeEntity>> mSelectTypeLiveData = new MutableLiveData<>();

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

    public void getSelectTypes() {
        submitRequestThrowError(PigeonPublicModel.getSelectMushType(selectTypes), r -> {
            if (r.isOk()) {
                mSelectTypeLiveData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


}
