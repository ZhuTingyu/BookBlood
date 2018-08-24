package com.cpigeon.book.module.menu.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.RevisePsdModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/24.
 */

public class RevisePsdViewModel extends BaseViewModel {


    //  修改密码 已知密码
    public void getZGW_Users_GetLogData() {
        submitRequestThrowError(RevisePsdModel.getZGW_Users_UpdatePWD(modifyOriginalPsd, modifyNewPsd, modifyNewPsd2), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    String modifyOriginalPsd;
    String modifyNewPsd;
    String modifyNewPsd2;

    public Consumer<String> setModifyOriginalPsd() {
        return s -> {
            modifyOriginalPsd = s;
            isCanCommit();
        };
    }


    public Consumer<String> setModifyNewPsd() {
        return s -> {
            modifyNewPsd = s;
            isCanCommit();
        };
    }

    public Consumer<String> setModifyNewPsd2() {
        return s -> {
            modifyNewPsd2 = s;
            isCanCommit();
        };
    }

    public void isCanCommit() {
        isCanCommit(modifyOriginalPsd, modifyNewPsd, modifyNewPsd2);
    }


}
