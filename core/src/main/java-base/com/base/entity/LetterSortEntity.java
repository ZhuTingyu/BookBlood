package com.base.entity;

import com.base.base.pinyin.getContentLetter;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class LetterSortEntity<T> implements getContentLetter {

    public String letter;

    public T data;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String getContent() {
        return "";
    }
}
