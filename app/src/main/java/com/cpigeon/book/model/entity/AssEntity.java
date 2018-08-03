package com.cpigeon.book.model.entity;

import com.base.base.pinyin.LetterSortModel;
import com.base.entity.LetterSortEntity;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class AssEntity extends LetterSortEntity {

    public AssEntity(String name){
        this.name = name;
    }

    String id;
    String name;

    @Override
    public String getContent() {
        return name;
    }
}
