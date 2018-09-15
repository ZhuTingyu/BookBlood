package com.cpigeon.book.event;

import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class TrainPigeonAddEvent {
    PigeonEntity mPigeonEntity;

    public TrainPigeonAddEvent(PigeonEntity pigeonEntity) {
         mPigeonEntity = pigeonEntity;
    }
}
