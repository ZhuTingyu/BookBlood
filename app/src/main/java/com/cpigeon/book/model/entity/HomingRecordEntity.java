package com.cpigeon.book.model.entity;

import com.base.entity.ExpendEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class HomingRecordEntity extends ExpendEntity {

    public int order;
    public List<HomingRecordExpandEntity> mHomingRecordExpandEntity;

    @Override
    public  List<HomingRecordExpandEntity> getRace() {
        return mHomingRecordExpandEntity;
    }
}
