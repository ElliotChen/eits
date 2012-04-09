package tw.com.dsc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DateUtils {
	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	private static ThreadLocal<SimpleDateFormat> defaultDateFormat = new ThreadLocal<SimpleDateFormat>();
	
	private static ThreadLocal<SimpleDateFormat> defaultDateTimeFormat = new ThreadLocal<SimpleDateFormat>();
	
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
	
	public static final String formatDate(Date date) {
		return getDefaultDateFormat().format(date);
	}
}
