package com.cpigeon.book.model.entity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class BloodBookEntity {
    private List<BreedPigeonEntity> four;
    private List<BreedPigeonEntity> three;
    private List<BreedPigeonEntity> one;
    private List<BreedPigeonEntity> two;

    public List<BreedPigeonEntity> getFour() {
        return four;
    }

    public void setFour(List<BreedPigeonEntity> four) {
        this.four = four;
    }

    public List<BreedPigeonEntity> getThree() {
        return three;
    }

    public void setThree(List<BreedPigeonEntity> three) {
        this.three = three;
    }

    public List<BreedPigeonEntity> getOne() {
        return one;
    }

    public void setOne(List<BreedPigeonEntity> one) {
        this.one = one;
    }

    public List<BreedPigeonEntity> getTwo() {
        return two;
    }

    public void setTwo(List<BreedPigeonEntity> two) {
        this.two = two;
    }
}
