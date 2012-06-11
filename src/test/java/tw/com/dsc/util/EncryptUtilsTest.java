package tw.com.dsc.util;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

public class EncryptUtilsTest {

	@Test
	public void testEncrypt() throws Exception {
		HttpServletRequest mock = Mockito.mock(HttpServletRequest.class);
		Mockito.when(mock.getServerName()).thenReturn("192.168.1.13");
		
		String token = EncryptUtils.encrypt(mock, "l3_admin");
		System.out.println(token);
	}

}
