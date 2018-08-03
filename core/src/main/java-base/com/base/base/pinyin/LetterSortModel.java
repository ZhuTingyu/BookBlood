package com.base.base.pinyin;

import com.base.base.pinyin.CharacterParser;
import com.base.util.utility.StringUtil;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class LetterSortModel<T> {

    public List<LetterSortEntity> data;

    public void setData(List<LetterSortEntity> data) {
        if (data != null) {
            /*for (LetterSortEntity entity : data) {
                String pinyin = CharacterParser.getInstance().getSelling(entity.getUsername());
                String sortString = pinyin.substring(0, 1).toUpperCase();

                if (sortString.matches("[A-Z]")) {
                    entity.setSortLetters(sortString.toUpperCase());
                } else {
                    entity.setSortLetters("#");
                }
                mMembers.add(entity);
            }*/
        }
    }

    public class LetterSortEntity {

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
    }
}
