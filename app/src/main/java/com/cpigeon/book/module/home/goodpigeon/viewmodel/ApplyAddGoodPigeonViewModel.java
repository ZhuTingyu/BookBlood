package com.cpigeon.book.module.home.goodpigeon.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.GoodPigeonModel;

/**
 * Created by Zhu TingYu on 2018/9/28.
 */

public class ApplyAddGoodPigeonViewModel extends BaseViewModel {
    public String foodId;
    public String pigeonId;

    public void apllyAddGoodPigeon(){

        if(!StringUtil.isStringValid(foodId)){
            error(R.string.text_pleas_choose_pigeon);
            return;
        }

        submitRequestThrowError(GoodPigeonModel.applyAddGoodPigeon(foodId,pigeonId),r -> {
            if(r.isOk()){
                normalResult.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }
}
