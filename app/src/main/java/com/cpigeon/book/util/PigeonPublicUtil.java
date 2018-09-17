package com.cpigeon.book.util;

import android.widget.ImageView;

import com.cpigeon.book.R;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class PigeonPublicUtil {


    public static void setPigeonSexImg(String sex, ImageView imgSex) {
        if (sex.equals("雌")) {
            imgSex.setImageResource(R.mipmap.ic_female);
        } else if (sex.equals("雄")) {
            imgSex.setImageResource(R.mipmap.ic_male);
        } else {
            imgSex.setImageResource(R.mipmap.ic_sex_no);
        }
    }

}
