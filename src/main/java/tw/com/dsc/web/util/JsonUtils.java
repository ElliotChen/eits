package tw.com.dsc.web.util;

import org.codehaus.jackson.map.ObjectMapper;

public abstract class JsonUtils {
	
	public static <T> String toJson(T entity, String sEcho) {
		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(Feature., false);
		String result = "Fail";
		try {
			result = mapper.writeValueAsString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
