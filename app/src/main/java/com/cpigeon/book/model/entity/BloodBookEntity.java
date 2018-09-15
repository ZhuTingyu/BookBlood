package com.cpigeon.book.model.entity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class BloodBookEntity {
    private List<PigeonEntity> four;
    private List<PigeonEntity> three;
    private List<PigeonEntity> one;
    private List<PigeonEntity> two;

    public List<PigeonEntity> getFour() {
        return four;
    }

    public void setFour(List<PigeonEntity> four) {
        this.four = four;
    }

    public List<PigeonEntity> getThree() {
        return three;
    }

    public void setThree(List<PigeonEntity> three) {
        this.three = three;
    }

    public List<PigeonEntity> getOne() {
        return one;
    }

    public void setOne(List<PigeonEntity> one) {
        this.one = one;
    }

    public List<PigeonEntity> getTwo() {
        return two;
    }

    public void setTwo(List<PigeonEntity> two) {
        this.two = two;
    }
}
