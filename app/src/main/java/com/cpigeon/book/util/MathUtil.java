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

    public static String toChinese(String string) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";

        try {
            int n = string.length();
            for (int i = 0; i < n; i++) {

                int num = string.charAt(i) - '0';

                if (i != n - 1 && num != 0) {
                    result += s1[num] + s2[n - 2 - i];
                } else {
                    result += s1[num];
                }
                System.out.println("  "+result);
            }

            System.out.println("----------------");
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }


}
