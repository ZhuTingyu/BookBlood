package com.cpigeon.book.module.trainpigeon;

import com.cpigeon.book.model.entity.FlyBackRecordEntity;

import java.util.Comparator;

/**
 * Created by Zhu TingYu on 2018/9/20.
 */

public class FlyBackRecordComparator implements Comparator<FlyBackRecordEntity> {
    @Override
    public int compare(FlyBackRecordEntity o1, FlyBackRecordEntity o2) {
        return (int) ((o2.getFraction() * 1000) - (o1.getFraction() * 1000));
    }
}
