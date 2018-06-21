package com.sanshang.li.mybaseframwork.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * Created by li on 2018/6/20.
 * WeChat 18571658038
 * author LiWei
 */

public class DateUtils {

    public static final DateFormat SDF = new SimpleDateFormat("HH:mm");
    public static final DateFormat SDF1 = new SimpleDateFormat("HH:mm:ss");
    public static final DateFormat DF = new SimpleDateFormat("MM月dd日");
    public static final DateFormat DATE_SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final DateFormat DATE_SDF1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final DateFormat DATE_LONG = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
    public static final DateFormat DATE_LONG1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
    public static final DateFormat DATE_LONG2 = new SimpleDateFormat("yyyy.MM.dd");
    public static final DateFormat DATE_LONG3 = new SimpleDateFormat("yyyy年MM月dd日");
    public static final DateFormat DATE_LONG_SHOW = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DATE_LONG_SHOW1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final DateFormat DATE_LONG_DIY = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
    public static final DateFormat DATE_LONG_DIY_OTHER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String dateToString(DateFormat df, Date date) {
        return df.format(date);
    }

    /**
     * 将长整形时间转换为字符串日期
     * @param longDate
     * @param str
     * @return
     */
    public static String parseLongToString(long longDate, DateFormat str) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(longDate);
        return str.format(calendar.getTime());
    }

    /**
     * 解析 字符串为日期
     * @param format
     * @param time
     * @return
     */
    public static Date parseStringDate(DateFormat format,String time) {

        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将当期日期转为字符串
     * @return
     */
    public static String parseDateToString() {
        return DATE_SDF.format(new Date());
    }

    /**
     * 将当期日期转为字符串
     * @return
     */
    public static String parseDateToString(Date date) {
        return DATE_LONG2.format(new Date());
    }

    public static String parseTimeToString(Date date) {

        return DATE_SDF.format(date);
    }

    /**
     * 转换日期
     * @param timesamp
     * @return
     */
	/*public static String getDay(Date timesamp) {

		if(timesamp == null){
			return "未";
		}
		String result = "未";
		long time =  timesamp.getTime();

		long longTemp = System.currentTimeMillis()- time;

		int temp = (int) (longTemp / (1000 * 60 * 60 * 24));

		switch (temp) {
		case 0:
			result = "今天 " + dateToString(SDF,timesamp);
			break;
		case 1:
			result = "昨天 "+ dateToString(SDF,timesamp);
			break;
		case 2:
			result = "前天 "+ dateToString(SDF,timesamp);
			break;

		default:
			result = temp + "天前 "+ dateToString(SDF,timesamp);
			break;
		}
		return result;
	}*/

    /**
     * 转换日期
     * @param timesamp
     * @return
     */
    public static String getDay(Date timesamp) {
        if(timesamp == null){
            return "未";
        }
        String result = "未";
        long time =  timesamp.getTime();

        long longTemp = System.currentTimeMillis()- time;

        int temp = (int) (longTemp / (1000 * 60 * 60 * 24));

        switch (temp) {
            case 0:
                result = "今天 " + dateToString(SDF,timesamp);
                break;
            case 1:
                result = "昨天 "+ dateToString(SDF,timesamp);
                break;
            case 2:
                result = "前天 "+ dateToString(SDF,timesamp);
                break;

            default:
                result = temp + "天前 "+ dateToString(SDF,timesamp);
                break;
        }
        return result;
    }

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date = format.parse("2013-11-11 18:35:35");
        System.out.println(format(date));
    }

    public static String format(Date date) {

        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }
}
