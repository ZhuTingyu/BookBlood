package com.base.base.pinyin;

import com.base.entity.LetterSortEntity;
import com.base.util.Lists;

import java.util.Collections;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class LetterSortModel<T extends LetterSortEntity> {

    List<String> mLetters = Lists.newArrayList();
    PinyinComparator pinyinComparator = new PinyinComparator();
    LetterStringComparator mLetterStringComparator = new LetterStringComparator();
    public LetterSortModel(){
    }


    public List<T> data;

    public void setData(List<T> data) {
        mLetters.clear();
        if (data != null) {
            for (LetterSortEntity entity : data) {
                String pinyin = CharacterParser.getInstance().getSelling(entity.getContent());
                String sortString = pinyin.substring(0, 1).toUpperCase();

                if (sortString.matches("[A-Z]")) {
                    String letter = sortString.toUpperCase();
                    entity.setLetter(letter);
                    if(!mLetters.contains(letter)){
                        mLetters.add(letter);
                    }
                } else {
                    entity.setLetter("#");
                }
            }
            Collections.sort(data , pinyinComparator);
            this.data = data;
        }
    }

    public List<T> getData() {
        return data;
    }

    public List<String> getLetters() {
        Collections.sort(mLetters , mLetterStringComparator);
        return mLetters;
    }
}
