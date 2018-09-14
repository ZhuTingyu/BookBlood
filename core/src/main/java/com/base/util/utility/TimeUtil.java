package com.base.util.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public static final String FORMAT_YYYYMM = "yyyy-MM";
    public static final String FORMAT_YYYY = "yyyy";
    public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static final String FORMAT_YYYYMMDD_ = "yyyy-MM-dd";

    public static final String FORMAT_YYYYMM01 = "yyyy-MM-01";
    public static final String FORMAT_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_HHMMSS = "HH:mm:ss";
    public static final String FORMAT_HHMM = "HH:mm";
    public static final String FORMAT_YYYYHHMM_CHICESEC = "yyyy年MM月dd日";


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

    public static long getBeforeMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.MONTH, -month);
        return calendar.getTimeInMillis();

    }

    /**
     * 获取当前月份 总共天数
     *
     * @return
     */
    public static int getDayOfMonth() {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        return day;
    }


    /**
     * 根据日期获得星期
     */
    public static int getWeekOfDate(String dates)  {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //获取周几
        Date date = null;
        try {
            date = sdf.parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

//        return weekDaysCode[intWeek];
        return intWeek;
    }


}
