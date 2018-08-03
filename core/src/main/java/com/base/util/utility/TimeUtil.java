package com.base.util.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static final String FORMAT_YYYYMM = "yyyy-MM";
    public static final String FORMAT_YYYY = "yyyy";
    public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static final String FORMAT_YYYYMMDD_ = "yyyy-MM-dd";

    public static final String FORMAT_YYYYMM01 = "yyyy-MM-01";
    public static final String FORMAT_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_HHMM = "HH:mm";
    public static final String FORMAT_YYYYHHMM_CHICESEC= "yyyy年MM月dd日";


    public static String format(long date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date(date));
    }

    public static long parse(String str, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            return format.parse(str).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static long getBeforeMonth(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.MONTH, -month);
        return calendar.getTimeInMillis();

    }
}
