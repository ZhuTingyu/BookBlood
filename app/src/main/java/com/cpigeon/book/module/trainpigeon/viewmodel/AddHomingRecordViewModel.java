package com.cpigeon.book.module.trainpigeon.viewmodel;

import com.base.base.BaseViewModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class AddHomingRecordViewModel extends BaseViewModel {
    String footNumber;
    String homingTime;

    public Consumer<String> setFootNumber() {
        return s -> {
            footNumber = s;
            isCanCommit();
        };
    }

    public Consumer<String> setHomingTime() {
        return s -> {
            homingTime = s;
            isCanCommit();
        };
    }

    public void isCanCommit(){
        isCanCommit(footNumber, homingTime);
    }
}
