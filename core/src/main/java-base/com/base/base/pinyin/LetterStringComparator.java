package com.base.base.pinyin;

import com.base.entity.LetterSortEntity;
import com.base.util.utility.StringUtil;

import java.util.Comparator;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class LetterStringComparator implements Comparator<String> {

    public int compare(String o1, String o2) {
        if(StringUtil.isStringValid(o1) && StringUtil.isStringValid(o2)){
            if (o1.equals("@")
                    || o2.equals("#")) {
                return -1;
            } else if (o1.equals("#")
                    || o2.equals("@")) {
                return 1;
            } else {
                return o1.compareTo(o2);
            }
        }else {
            return -1;
        }

    }
}
