/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.sms.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 
 * Module:
 * 
 * DateUtil.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class DateUtil {

	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

	public static final String yyyy_MM_dd_HHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMdd_HHmmss = "yyyyMMdd hh:mm:ss";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String HH_mm_ss = "HHmmss";
	public static final String yyMMdd_HHmmss = "yy/MM/dd HH:mm:ss";
	public static final String yyyy_MM_dd_HHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static final String YMD = "yyyy年MM月dd日";
	
	public static Map<String,ThreadLocal<SimpleDateFormat>> SDF_MAP = new HashMap<>();
	
	private static final Object LOCK_OBJECT = new Object();
	
//	static {
//		SDF_MAP = new HashMap<>();
//		addThreadLocal(yyyy_MM_dd_HHmmss);
//		addThreadLocal(yyyyMMdd_HHmmss);
//		addThreadLocal(yyyyMMddHHmmss);
//		addThreadLocal(yyyy_MM_dd);
//		addThreadLocal(yyyyMMdd);
//		addThreadLocal(HH_mm_ss);
//		addThreadLocal(yyMMdd_HHmmss);
//		addThreadLocal(yyyy_MM_dd_HHmmssSSS);
//		addThreadLocal(yyyyMMddHHmmssSSS);
//		addThreadLocal(YMD);
//	}

	/**
	 * 获取指定格式的当前时间字符串
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDateString(String pattern) {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	/**
	 * 获取指定时间,指定格式的日期字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 获取指定时间,指定格式的日期字符串
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getDateString(String dateStr, String pattern) {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		try {
			return sdf.format(sdf.parse(dateStr));
		} catch (ParseException e) {
			log.error("日期解析错误,日期:" + dateStr);
			return "";
		}
	}

	/**
	 * 获取当前时间的TimeStamp
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 获取指定时间,指定格式的日期字符串
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date stringToDate(String dateStr, String pattern) {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		Date date = new Date();
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			log.error("日期解析错误,日期:" + dateStr);
		}
		return date;
	}

	public static boolean isBeforeTime(Date before, Date afrer) {
		int compareToBefore = before.compareTo(afrer);
		if (compareToBefore < 0) {
			return true;
		}
		return false;
	}

	public static int intervalMinute(Date start, Date end) {
		Long l = end.getTime() - start.getTime();
		Long m = l / 1000 / 60;
		return m.intValue();
	}

	public static int intervalDay(Date start, Date end) {
		Long l = end.getTime() - start.getTime();
		Long m = l / 1000 / 60 / 60 / 24;
		return m.intValue();
	}

	/**
	 * 获取回话ID - 用户写日志
	 * 
	 * @author liuDaodao
	 * @return
	 */
	public static String getSessionId() {
		String sessionId = "";
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = getSimpleDateFormat("yyyyMMddhhmmsss");
			sessionId = simpleDateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionId;
	}
	
	private static SimpleDateFormat getSimpleDateFormat(String pattern) {
		ThreadLocal<SimpleDateFormat> thread = SDF_MAP.get(pattern);
		if(null == thread)
			thread = addThreadLocal(pattern);
		return thread.get();
	}
	
	private static ThreadLocal<SimpleDateFormat> addThreadLocal(String pattern) {
		ThreadLocal<SimpleDateFormat> thread = null;
		
		synchronized (LOCK_OBJECT) {
			thread = SDF_MAP.get(pattern);
			if(null == thread) {
				thread = new ThreadLocal<SimpleDateFormat>() {

					@Override
					protected SimpleDateFormat initialValue() {
						return new SimpleDateFormat(pattern);
					}
					
				};
				
				SDF_MAP.put(pattern, thread);
			}
		}
		return thread;
	}
	
	/**
	 * 验证时间格式是否是年月日时分秒格式为2018-12-11 12:12:11
	 * @param date
	 * @return
	 */
	public static boolean verifiDate(String date) {
		String REG_EXP_DATE = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$"; 
		Pattern pattern = Pattern.compile(REG_EXP_DATE);
	    Matcher matcher = pattern.matcher(date);
	    return matcher.matches();
	}
}
