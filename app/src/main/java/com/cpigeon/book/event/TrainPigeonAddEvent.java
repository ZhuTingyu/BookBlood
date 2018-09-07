package com.cpigeon.book.event;

import com.cpigeon.book.model.entity.BreedPigeonEntity;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class TrainPigeonAddEvent {
    BreedPigeonEntity mPigeonEntity;

    public TrainPigeonAddEvent(BreedPigeonEntity pigeonEntity) {
         mPigeonEntity = pigeonEntity;
    }
}
