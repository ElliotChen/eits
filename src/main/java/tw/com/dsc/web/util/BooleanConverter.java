package tw.com.dsc.web.util;

import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

public class BooleanConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (null == values || StringUtils.isEmpty(values[0])) {
			return null;
		}
		
		return BooleanUtils.toBoolean(values[0]);
	}

	@Override
	public String convertToString(Map context, Object o) {
		if (null == o || !(o instanceof Boolean)) {
			return null;
		}
		
		return BooleanUtils.toStringTrueFalse((Boolean)o);
	}

}
