package tw.com.dsc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DateUtils {
	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	private static ThreadLocal<SimpleDateFormat> defaultDateFormat = new ThreadLocal<SimpleDateFormat>();
	
	private static ThreadLocal<SimpleDateFormat> defaultDateTimeFormat = new ThreadLocal<SimpleDateFormat>();
	
	private static ThreadLocal<SimpleDateFormat> activeDateFormat = new ThreadLocal<SimpleDateFormat>();
	
	private static ThreadLocal<SimpleDateFormat> yearMonthFormat = new ThreadLocal<SimpleDateFormat>();
	public static final SimpleDateFormat getDefaultDateFormat() {
		if (null == defaultDateFormat.get()) {
			defaultDateFormat.set(new SimpleDateFormat("yyyy/MM/dd"));
		}
		
		return defaultDateFormat.get();
	}
	
	public static final SimpleDateFormat getDefaultDateTimeFormat() {
		if (null == defaultDateTimeFormat.get()) {
			defaultDateTimeFormat.set(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
		}
		
		return defaultDateTimeFormat.get();
	}
	
	public static final Date pareseDate(String date) {
		Date result = null;
		try {
			result = getDefaultDateFormat().parse(date);
		} catch (ParseException e) {
			logger.error("Can't parse {} to Date", date);
		}
		
		return result;
	}
	
	public static final Date pareseDateTime(String date) {
		Date result = null;
		try {
			result = getDefaultDateFormat().parse(date);
		} catch (ParseException e) {
			logger.error("Can't parse {} to Date", date);
		}
		
		return result;
	}
	
	public static final String formatDate(Date date) {
		return getDefaultDateFormat().format(date);
	}
	
	public static final SimpleDateFormat getActiveDateFormat() {
		if (null == activeDateFormat.get()) {
			activeDateFormat.set(new SimpleDateFormat("yyyyMMdd"));
		}
		
		return activeDateFormat.get();
	}
	public static final Date pareseActiveDate(String date) {
		Date result = null;
		try {
			result = getActiveDateFormat().parse(date);
		} catch (ParseException e) {
			logger.error("Can't parse {} to Date", date);
		}
		
		return result;
	}
	public static final String formatActiveDate(Date date) {
		return getActiveDateFormat().format(date);
	}
	
	public static final SimpleDateFormat getYearMonthFormat() {
		if (null == yearMonthFormat.get()) {
			yearMonthFormat.set(new SimpleDateFormat("yyyy/MM"));
		}
		
		return yearMonthFormat.get();
	}
	public static final Date pareseYearMonth(String date) {
		Date result = null;
		try {
			result = getYearMonthFormat().parse(date);
		} catch (ParseException e) {
			logger.error("Can't parse {} to Date", date);
		}
		
		return result;
	}
	public static final String formatYearMonth(Date date) {
		return getYearMonthFormat().format(date);
	}
	
	public static final Date begin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	public static final Date end(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
}
