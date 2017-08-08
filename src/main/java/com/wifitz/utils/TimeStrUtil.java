package com.wifitz.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class TimeStrUtil 
{
	private static final int SECONDS_PER_HOUR = 3600;
	private static final int SECONDS_PER_MINUTE = 60;
	
	private static Logger logger = Logger.getLogger(TimeStrUtil.class);
	
	/**
	 * @param timeStr "2013-10-25 10:03:25"
	 * @return  isDaily? "2013-10-25" : "2013-10-25 10:00:00"
	 * @throws Exception
	 */
	public static String roundUpTime(String timeStr, int MinuteInterval, boolean isDaily) throws Exception
	{
		if(isDaily)
		{
			return timeStr.substring(0, 10);
		}
		
		int minuteStart = timeStr.indexOf(":");
		int minuteEnd = timeStr.lastIndexOf(":");
		String minuteStr = timeStr.substring(minuteStart + 1, minuteEnd);
		int minute = Integer.parseInt(minuteStr);
		minute = minute / MinuteInterval * MinuteInterval;
		if(minute < 10) 
		{
			minuteStr = "0" + Integer.toString(minute);
		}
		else 
		{
			minuteStr = Integer.toString(minute);
		}
		
		return timeStr.substring(0, minuteStart + 1) + minuteStr + ":00";
	}

	/**
	 * @param timeStr 2013-10-28 12:05:00
	 * return 12*3600 + 5*60 + 0,  the result will be between [0, 24*60*60)
	 */
	public static int getSeconds(String timeStr)
	{
		int timeBeginner = timeStr.indexOf(" ");
		int minuteBeginner = timeStr.indexOf(":");
		int secondBeginner = timeStr.lastIndexOf(":");
		
		String hour = timeStr.substring(timeBeginner + 1 , minuteBeginner);
		String minute = timeStr.substring(minuteBeginner + 1, secondBeginner);
		String second = timeStr.substring(secondBeginner + 1);
		
		return Integer.parseInt(hour) * SECONDS_PER_HOUR 
				+ Integer.parseInt(minute) * SECONDS_PER_MINUTE 
				+ Integer.parseInt(second);
	}
	
	public static String getTimeStr(int seconds)
	{
		int totalMinutes = seconds / 60;
		int totalHours = totalMinutes / 60;
		
		int sec = seconds - totalMinutes * 60;
		int minute = totalMinutes - totalHours * 60;
		
		return toFormatTimeStr(totalHours) + ":" + toFormatTimeStr(minute) + ":" + toFormatTimeStr(sec);
	}

	/**
	 * return a string with two characters
	 */
	private static String toFormatTimeStr(int i)
	{
		String str = "00";
		if (i >= 10)
		{
			str = Integer.toString(i);
		}
		else
		{
			str = "0" + Integer.toString(i);
		}
		
		return str;
	}
	
	/**
	 * 
	 * @param strOutputFormatString output format string
	 * @param strBaseDate base date for day offset
	 * @param strBaseFormatString base date format string
	 * @param dayOffset day offset
	 * @return formattedDateString 
	 */
	public static String getFormatDate(String strOutputFormatString, String strBaseDate, String strBaseFormatString, long dayOffset)
	{
		String strOutputFormatDate = null;
		try
		{
			Date baseDate = new Date();
			if (null != strBaseDate && null != strBaseFormatString)
			{
				SimpleDateFormat baseDateFormat = new SimpleDateFormat(strBaseFormatString);
				baseDate = baseDateFormat.parse(strBaseDate);
			}
			
			Date outputDate = new Date(baseDate.getTime() + dayOffset * 86400 * 1000);
			SimpleDateFormat outputDateFormat = new SimpleDateFormat(strOutputFormatString);
			strOutputFormatDate = outputDateFormat.format(outputDate);
		}
		catch (Exception e)
		{
			logger.error("Exception occur while call getFormatDate. Details [" + e.getMessage() + "].");
		}
		
		return strOutputFormatDate;
	}
	
	public static String getFormatDate(String strOutputFormatString, long dayOffset)
	{
		return getFormatDate(strOutputFormatString, null, null, dayOffset);
	}
	
	private TimeStrUtil() {}
	/**
	 * 将时间转换成路径   形如  2017_5_24.17   那么这个文件的存储绝对路径      chen/hour/2017/5/24/chen_2017_5_24.17
	 * @param time
	 * @return
	 */
	public static String changeTime2Path(String time,String timehold) {
		String path = "default/";
		String times = time.split("\\.")[0];
	
		path = times.replaceAll("_", "/");
		if (!timehold.equals("hour")) {
			int index = path.lastIndexOf("/");
			path = path.substring(0, index>0?index:0);
		}
		return path+"/";
		
	}
}
