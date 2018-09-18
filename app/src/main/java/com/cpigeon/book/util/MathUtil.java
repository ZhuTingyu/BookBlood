package com.cpigeon.book.util;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by Administrator on 2018/9/12.
 */

public class MathUtil {


    public static String doubleformat(double d, int pointCount) {
        NumberFormat nf = NumberFormat.getNumberInstance();


        // 保留两位小数
        nf.setMaximumFractionDigits(pointCount);


        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);


        return nf.format(d);
    }

}
