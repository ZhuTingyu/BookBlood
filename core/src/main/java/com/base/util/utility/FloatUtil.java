package com.base.util.utility;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class FloatUtil {
    private final static float EPSILON = 0.00000001F;

    public static boolean compareFloats(float f1, float f2) {
        return Math.abs(f1 - f2) <= EPSILON;
    }

    /**
     * 保留小数点后几位
     * @param d
     * @param count 保留的位数
     * @return
     */

    public static String doubleFormat(double d, int count) {
        NumberFormat nf = NumberFormat.getNumberInstance();


        // 保留两位小数
        nf.setMaximumFractionDigits(count);


        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);


        return nf.format(d);
    }
}
