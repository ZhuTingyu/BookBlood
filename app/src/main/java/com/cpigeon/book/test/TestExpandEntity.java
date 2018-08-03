package com.cpigeon.book.test;

import com.base.entity.ExpendEntity;
import com.base.entity.RaceEntity;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/3/5.
 */

public class TestExpandEntity extends ExpendEntity{
    public String a;
    public List<RaceEntity> race;

    @Override
    public List<RaceEntity> getRace() {
        return race;
    }
}
