package com.tiny.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateSupportUtils {
	
	public final static DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
	public final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	public final static DateFormat moniteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
	public final static DateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	public final static DateFormat timeFormat = new SimpleDateFormat("HH:mm"); 
	public final static DateFormat dateCompactFormat = new SimpleDateFormat("yyyyMMdd"); 
	
	public static String date2strCompact(Date date){
		return date==null ? null : dateCompactFormat.format(date);
	}
	
	public static Date str2dateCompact(String str){
		try{
			return dateCompactFormat.parse(str);
		}catch(Exception e){
		}
		return null;
	}
	
	public static String month2str(Date date){
		return date==null ? null : monthFormat.format(date);
	}
	
	public static Date str2month(String str){
		try{
			return monthFormat.parse(str);
		}catch(Exception e){
		}
		return null;
	}
	
	public static String date2str(Date date){
		return date==null ? null : dateFormat.format(date);
	}
	
	public static Date str2date(String str){
		try{
			return dateFormat.parse(str);
		}catch(Exception e){
		}
		return null;
	}
	
	
	public static String minute2str(Date date){
		return date==null ? null : moniteFormat.format(date);
	}
	
	public static Date str2minute(String str){
		try{
			return moniteFormat.parse(str);
		}catch(Exception e){
		}
		return null;
	}
	
	public static String second2str(Date date){
		return date==null ? null : secondFormat.format(date);
	}
	
	public static Date str2second(String str){
		try{
			return secondFormat.parse(str);
		}catch(Exception e){
		}
		return null;
	}
	
	public static String time2str(Date date){
		return date==null ? null : timeFormat.format(date);
	}
	
	public static Date str2time(String str){
		try{
			return timeFormat.parse(str);
		}catch(Exception e){
		}
		return null;
	}
	
	public static String convertDate2Str(Date date, String symbol) {
		SimpleDateFormat sf = new SimpleDateFormat(symbol);
		return sf.format(date);
	}	
	
	/**
	 * ���������
	 * @return
	 */
	public static Date getCurrentDate(){
		return DateSupportUtils.getCurrentDateTime(0,0,0).getTime();
	}
	
	/**
	 * ����һ��
	 * @return
	 */
	public static Date getMonthOfFirstDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1); 
		cal.set(Calendar.HOUR_OF_DAY, 0); 
		cal.set(Calendar.MINUTE, 0); 
		cal.set(Calendar.SECOND, 0); 
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * �ϸ��µĽ���
	 * @return
	 */
	public static Date getLastMonthDate() {
		Calendar cal = DateSupportUtils.getCurrentDateTime(0, 0, 0);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}
	
	/**
	 * ����ļ��㼸ʱ����
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Calendar getCurrentDateTime(Integer hour, Integer minute, Integer second){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour); 
		cal.set(Calendar.MINUTE, minute); 
		cal.set(Calendar.SECOND, second); 
		cal.set(Calendar.MILLISECOND,0);
		return cal;
	}
	
	/**
	 * ����ǰ/�������
	 * @param days
	 * @return
	 */
	public static Date dateAdd(Integer days){
		Calendar cal = DateSupportUtils.getCurrentDateTime(0, 0, 0);
		cal.roll(Calendar.DAY_OF_YEAR, days); 
		return cal.getTime();
	}
	
	/**
	 * ����ǰ/��ļ��㼸ʱ����
	 * @param days
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date datetimeAdd(Integer days, Integer hour, Integer minute, Integer second){
		Calendar cal = DateSupportUtils.getCurrentDateTime(hour, minute, second);
		cal.roll(Calendar.DAY_OF_YEAR, days); 
		return cal.getTime();
	}
	
	/**
	 * ����ǰ/�������
	 * @param days
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date secondAdd(Integer second){
		Calendar cal = DateSupportUtils.getCurrentDateTime(0, 0, 0);
		cal.roll(Calendar.SECOND, second); 
		return cal.getTime();
	}
	
}
