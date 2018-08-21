package com.cpigeon.book.module.foot.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.FootAdminModel;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 足环管理
 * Created by Administrator on 2018/8/7.
 */

public class FootAdminSingleViewModel extends BaseViewModel {

    public MutableLiveData<String> oneStartHintStr = new MutableLiveData<>();

    public String foodId;
    public String countryId = "2"; //默认中国id
    public String money;
    public String footNumber;
    public String footType;//足环类型
    public String footSource;//足环来源
    public String remark;//备注
    public List<SelectTypeEntity> mSelectTypes;
    public MutableLiveData<FootEntity> mFootLiveData = new MutableLiveData<>();
    public MutableLiveData<String> mdelectR = new MutableLiveData<>();

    public FootAdminSingleViewModel(Activity activity) {
        foodId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
    }


    //添加足环（单个）
    public void addFoot() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_Add(countryId, footNumber, money, footType, footSource, remark), r -> {
            if (r.isOk()) {
                normalResult.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    //获取单个足环详细
    public void getFootById() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_Select(foodId), r -> {
            if (r.isOk()) {
                mFootLiveData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //修改足环（单个）
    public void modifyFootNumber() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_Edit(foodId, countryId, footNumber, money.replace("元", "")
                , footType, footSource, remark), r -> {
            if (r.isOk()) {
                normalResult.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    //删除足环（单个）
    public void deleteFoot() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_Delete(foodId), r -> {
            if (r.isOk()) {
                mdelectR.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    public Consumer<String> setFootNumber() {
        return s -> {
            this.footNumber = s;
            isCanCommit();
        };
    }

    public Consumer<String> setMoney() {
        return s -> {
            this.money = s;
            isCanCommit();
        };
    }


    public Consumer<String> setFootSource() {
        return s -> {
            this.footSource = s;
            isCanCommit();
        };

    }

    public Consumer<String> setRemark() {
        return s -> {
            this.remark = s;
        };
    }

    public void isCanCommit() {
        isCanCommit(footNumber, footType, footSource, money);
    }

    /*

    public MutableLiveData<DetailsSingleFootEntity> footDetailsData = new MutableLiveData<>();

    //添加足环号段
    public void getTXGP_FootRing_AddSectionData() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_AddSection(segment_startFoot,
                segment_endFoot, segment_typeId, segment_stateId, segment_source, segment_money,
                segment_usenum, segment_remark), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }*/

}
