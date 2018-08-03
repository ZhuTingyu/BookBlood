package com.base.util;

import android.util.Log;

import static com.base.util.utility.FloatUtil.doubleFormat;

/**
 * Created by Zhu TingYu on 2018/3/5.
 */

public class LocationFormatUtils {

    /**
     * 安捷格式转换为gps格式
     */
    public static double Aj2GPSLocation(double ajLocationValue) {
        int du = (int) ajLocationValue;
        double temp = (ajLocationValue - du) * 100;//需要转换的部分（分）
        int fen = (int) temp;
        temp = (temp - fen) * 100;//秒
        return du + (double) fen / 60 + temp / 3600;
    }

    /**
     * gps格式转换为安捷格式
     */
    public static String GPS2AjLocation(double gpsLocationValue) {
        int du = (int) gpsLocationValue;
        double temp = (gpsLocationValue - du) * 60;//需要转换的部分（分）
        int fen = (int) temp;
        temp = (temp - fen) * 60;//秒
        return doubleFormat(du + (double) fen / 100 + temp / 10000, 6);
    }


    /**
     * 经纬度转化为度分秒
     */
    public static String loLaToDMS(double lola) {
        return strToDMS(GPS2AjLocation(lola));
    }

    /**
     * 通过字符串 获取 度
     */
    public static String strToD(String str) {
        String strDms = "";
        strDms = ((str.split("\\."))[0]).toString();//度
        return strDms;
    }

    /**
     * 通过字符串 获取 分
     */
    public static String strToM(String str) {
        String strDms = "";
        Log.d("GPSFormatUtils", "strToDMs: " + str);
        String[] srr = (str.split("\\."));

        if (srr.length != 2) {
            return "";
        }

        byte[] strByte = srr[1].getBytes();

        if (strByte != null) {
            switch (strByte.length) {
                case 0:
                    break;
                case 1:
                    strDms = "" + (strByte[0] - 48);
                    break;
                default:
                    strDms = "" + (strByte[0] - 48) + (strByte[1] - 48);
            }
        }
        return strDms;
    }


    /**
     * 通过字符串 获取秒 有小数点
     */
    public static String strToS(String str) {

        String s = "";

        String strDms = "";
        strDms = ((str.split("\\."))[0]).toString() + "°";//度

        Log.d("GPSFormatUtils", "strToDMs: " + str);
        String[] srr = (str.split("\\."));

        if (srr.length != 2) {
            return "";
        }

        byte[] strByte = srr[1].getBytes();

        if (strByte != null) {
            switch (strByte.length) {
                case 0:
                    break;
                case 1:
                    strDms = strDms + (strByte[0] - 48) + "'";
                    break;
                case 2:
                    strDms = strDms + (strByte[0] - 48) + (strByte[1] - 48) + "'";

                    break;
                case 3:
                    s = "0" + (strByte[2] - 48) + ".00";
                    break;
                case 4:
                    s = (strByte[2] - 48) + "" + (strByte[3] - 48) + ".00";
                    break;

                case 5:
                    s = (strByte[2] - 48) + "" + (strByte[3] - 48) + "." + (strByte[4] - 48) + "0";
                    break;
                case 6:
                    s = (strByte[2] - 48) + "" + (strByte[3] - 48) + "." + (strByte[4] - 48) + (strByte[5] - 48) + "";
                    break;
                default:
                    s = (strByte[2] - 48) + "" + (strByte[3] - 48) + "." + (strByte[4] - 48) + (strByte[5] - 48) + "";
            }
        }
        return s;
    }


    /**
     * 通过字符串，获取 度分秒
     */
    public static String strToDMS(String str) {

        String strDms = "";
        strDms = ((str.split("\\."))[0]).toString() + "°";//度

        Log.d("GPSFormatUtils", "strToDMs: " + str);
        String[] srr = (str.split("\\."));

        if (srr.length != 2) {
            return str + "°";
        }

        byte[] strByte = srr[1].getBytes();

        if (strByte != null) {
            switch (strByte.length) {
                case 0:
                    break;
                case 1:
                    strDms = strDms + "0" + (strByte[0] - 48) + "\'00.00\"";
                    break;
                case 2:
                    strDms = strDms + (strByte[0] - 48) + (strByte[1] - 48) + "\'" + "'00.00\"";

                    break;
                case 3:
                    strDms = strDms + (strByte[0] - 48) + (strByte[1] - 48) + "\'0" + (strByte[2] - 48) + ".00\"";
                    break;
                case 4:
                    strDms = strDms + (strByte[0] - 48) + (strByte[1] - 48) + "\'" + (strByte[2] - 48) + (strByte[3] - 48) + ".00\"";
                    break;

                case 5:
                    strDms = strDms + (strByte[0] - 48) + (strByte[1] - 48) + "\'" + (strByte[2] - 48) + (strByte[3] - 48) + "." + (strByte[4] - 48) + "0\"";
                    break;
                case 6:
                    strDms = strDms + (strByte[0] - 48) + (strByte[1] - 48) + "\'" + (strByte[2] - 48) + (strByte[3] - 48) + "." + (strByte[4] - 48) + (strByte[5] - 48) + "\"";
                    break;
                default:
                    strDms = strDms + (strByte[0] - 48) + (strByte[1] - 48) + "\'" + (strByte[2] - 48) + (strByte[3] - 48) + "." + (strByte[4] - 48) + (strByte[5] - 48) + "\"";
            }
        }
        return strDms;
    }


}
