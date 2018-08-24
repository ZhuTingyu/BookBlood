package com.cpigeon.book.util;

/**
 * Created by Administrator on 2017/12/21.
 */

public class OrderUtil {

    /**
     * 添加手续费
     *
     * @param fee  金额
     * @param rate 费率
     * @return
     */
    public static double getTotalFee(double fee, double rate) {
        double f = fee * rate;//手续费
        return fee + (f <= 0.01 ? 0.01f : f);
    }

    /**
     * 添加手续费
     *
     * @param fee  金额
     * @param rate 费率
     * @return
     */
    public static double getHandlingCharge(double fee, double rate) {
        double f = fee * rate;//手续费
        return Double.valueOf(String.format("%.2f", (f <= 0.01 ? 0.01f : f)));
    }

}
