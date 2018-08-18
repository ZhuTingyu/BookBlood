package com.cpigeon.book.model.entity;

import com.base.entity.LetterSortEntity;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class AssEntity extends LetterSortEntity {

    String PigeonISOCID;
    String ISOCName;

    @Override
    public String getContent() {
        return ISOCName;
    }

    public String getPigeonISOCID() {
        return PigeonISOCID;
    }

    public void setPigeonISOCID(String pigeonISOCID) {
        PigeonISOCID = pigeonISOCID;
    }

    public String getISOCName() {
        return ISOCName;
    }

    public void setISOCName(String ISOCName) {
        this.ISOCName = ISOCName;
    }
}
