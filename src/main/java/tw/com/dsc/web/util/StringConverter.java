package tw.com.dsc.web.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

public class StringConverter extends StrutsTypeConverter {
	private static final String STRING_NULL = "null";
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (null == values) {
			return null;
		}

		return skipNullString(values);
	}

	@Override
	public String convertToString(Map context, Object o) {
		if (null == o) {
			return null;
		}
		
		return skipNullString((String[]) o);
	}
	
	private String skipNullString(String[] values) {
		String value = values[0];
		if (values.length == 1) {
			if (StringUtils.isEmpty(value) || STRING_NULL.equalsIgnoreCase(value)) {
				value = null;
			}
		} else {
			for (int i = 1; i < values.length; i++) {
				value += "," + values[i];
			}
		}
		return value;
	}

}
