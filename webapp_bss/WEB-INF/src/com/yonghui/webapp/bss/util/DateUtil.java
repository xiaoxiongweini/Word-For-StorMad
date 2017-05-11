
package com.yonghui.webapp.bss.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


public class DateUtil {

	/** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	public static final String dtLong = "yyMMddHHmmss";

	/** 完整时间 yyyy-MM-dd HH:mm:ss */
	public static final String simple = "yyyy-MM-dd HH:mm:ss";

	/** 年月日(无下划线) yyyyMMdd */
	public static final String dtShort = "yyyyMMdd";


	public  static String getDateFormatter(long time){
		Date date=new Date(time);
		DateFormat df=new SimpleDateFormat(simple);
		return df.format(date);
	}
	public  static String getDateFormatterVdt(long time){
		Date date=new Date(time);
		DateFormat df=new SimpleDateFormat(dtShort);
		return df.format(date);
	}
	public static long getTime(String time) {
		DateFormat df=new SimpleDateFormat(simple);
		try {
			return df.parse(time).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static String getDate(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtShort);
		return df.format(date);
	}

	/**
	 * 产生随机的三位数
	 * @return
	 */
	public static String getThree(){
		Random rad=new Random();
		return rad.nextInt(1000)+"";
	}
	
	/**
	 * 
	 * <b>功能：</b>获取今年第一天的时间戳<br>
	 * <b>日期：</b>2016年6月14日<br>
	 * <b>作者：</b>rush<br>
	 *
	 * @return
	 */
	public static long getThisYearFirstDayTime() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		SimpleDateFormat df = new SimpleDateFormat(simple);
		String newTime = year + "-01-01 00:00:00";
		try {
			return df.parse(newTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}