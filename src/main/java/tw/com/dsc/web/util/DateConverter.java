package tw.com.dsc.web.util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import tw.com.dsc.util.DateUtils;

public class DateConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (null == values || StringUtils.isEmpty(values[0])) {
			return null;
		}
		
		if(values.length > 1) {
			
		}
		return DateUtils.pareseDate(values[0]);
	}

	@Override
	public String convertToString(Map context, Object o) {
		if (null == o) {
			return null;
		}
		return DateUtils.formatDate((Date) o);
	}

}
