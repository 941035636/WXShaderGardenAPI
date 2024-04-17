package com.jt.www.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    // 时间元素
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String WEEK = "week";
    private static final String DAY = "day";
    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";
    private static final String SECOND = "second";

    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";

    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";

    public static final SimpleDateFormat format_time_format_yyyy_mm_dd_hh_mi_ss = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);

    public static final SimpleDateFormat format_date_format_yyyy_mm_dd = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);

    public static final SimpleDateFormat format_date_format_yyyy_mm = new SimpleDateFormat(DATE_FORMAT_YYYY_MM);

    public static String getAfterOrPreDate(String date, String node, int index) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        if (DAY.equals(node)) {
            // 日期加index天
            rightNow.add(Calendar.DAY_OF_YEAR, index);
        } else if (MONTH.equals(node)) {
            // 日期加index个月
            rightNow.add(Calendar.MONTH, index);
        } else if (YEAR.equals(node)) {
            // 日期加index年
            rightNow.add(Calendar.YEAR, index);
        } else {
            return "Wrong date format!";
        }

        return sdf.format(rightNow.getTime());
    }
    public static String getJxData(String type){
        StringBuilder stringBuilder=new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        if ("1".equals(type)) {
            calendar.add(Calendar.MONTH, -1);    //得到前一个月
        }else if ("2".equals(type)){
            calendar.add(Calendar.DATE, -1);    //得到前一天
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        stringBuilder.append(year);
        if (month<10){
            stringBuilder.append("-0"+month);
        }else {
            stringBuilder.append("-"+month);
        }
        if (day<10){
            stringBuilder.append("-0"+day);
        }else {
            stringBuilder.append("-"+day);
        }
        return stringBuilder.toString();
    }
    
    //给定日期月的第一天
  	public static String date2StringByMonthFirstDay(String date) {
  		try {
  			Date date1 = format_time_format_yyyy_mm_dd_hh_mi_ss.parse(date);
  			Calendar calendar = Calendar.getInstance();
  			calendar.setTime(date1);
  			calendar.set(Calendar.DAY_OF_MONTH,
  			calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
  			calendar.set(Calendar.HOUR_OF_DAY, 0);
  			calendar.set(Calendar.SECOND, 0);
  			calendar.set(Calendar.MINUTE, 0);
  			return format_time_format_yyyy_mm_dd_hh_mi_ss.format(calendar.getTime());
  		} catch (Exception e) {
  			e.getStackTrace();
  		}
  		return null;
  	}

	//给定日期年的第一天
	public static String date2StringByYearFirstDay(String date) {
		try {
			Date date1 = format_time_format_yyyy_mm_dd_hh_mi_ss.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			calendar.set(Calendar.DAY_OF_YEAR,
			calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			return format_time_format_yyyy_mm_dd_hh_mi_ss.format(calendar.getTime());
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	
	public static Date string2Date(String date) throws ParseException{
		return org.apache.commons.lang3.time.DateUtils.parseDate(date, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	}

	public static int DaysBetweenTwoDate(String firstString, String secondString) {
        SimpleDateFormat sdf;
        if (firstString.length() == 10)
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        else
		    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date firstDate = null;
		Date secondDate = null;
		try {
			firstDate = sdf.parse(firstString);
			secondDate = sdf.parse(secondString);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
	}

}
