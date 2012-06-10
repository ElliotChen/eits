package tw.com.dsc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptUtils {
	private static final Logger logger = LoggerFactory.getLogger(EncryptUtils.class);
	private static final String PASSWORD = "eits_zyxel";

	private static ThreadLocal<Cipher> encripty = new ThreadLocal<Cipher>();
	private static ThreadLocal<Cipher> decripty = new ThreadLocal<Cipher>();

	public static final String encrypt(HttpServletRequest request, String accountId) throws BadPaddingException {
		Cipher enCipher = getEncriptyCipher();
		if (null == enCipher) {
			logger.error("No Encripty Cipher Instance");
			throw new BadPaddingException("No Cipher Instance");
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, 6);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String server = request.getServerName();
		String source = sdf.format(cal.getTime()) + "____" + accountId + "____" + server;
		logger.debug("Token is [{}]", source);
		String result = "";
		try {
			result = Base64.encodeBase64String(enCipher.doFinal(source.getBytes()));
		} catch (Exception e) {
			logger.error("encode failed");
			e.printStackTrace();
			throw new BadPaddingException("encode failed");
		}

		return result;
	}

	public static final String decrypt(String source) throws BadPaddingException {
		Cipher deCipher = getDecriptyCipher();
		if (null == deCipher) {
			logger.error("No Decripty Cipher Instance");
			throw new BadPaddingException("No Cipher Instance");
		}
		byte[] base64 = Base64.decodeBase64(source);
		String result = "";
		try {
			result = new String(deCipher.doFinal(base64));
			logger.debug(result);
		} catch (IllegalBlockSizeException e) {
			throw new BadPaddingException("decode failed");
		}

		return result;
	}

	public static final Cipher getDecriptyCipher() {
		try {
			if (null == decripty.get()) {
				DESKeySpec key = new DESKeySpec(PASSWORD.getBytes());
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey genKey = keyFactory.generateSecret(key);
				Cipher deCipher = Cipher.getInstance("DES");
				deCipher.init(Cipher.DECRYPT_MODE, genKey);
				decripty.set(deCipher);
			}
		} catch (Exception e) {
			logger.error("Init DES Cipher Failed!");
			e.printStackTrace();
		}
		
		return decripty.get();
	}
	
	public static final Cipher getEncriptyCipher() {
		try {
			if (null == encripty.get()) {
				DESKeySpec key = new DESKeySpec(PASSWORD.getBytes());
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey genKey = keyFactory.generateSecret(key);
				Cipher enCipher = Cipher.getInstance("DES");
				enCipher.init(Cipher.ENCRYPT_MODE, genKey);
				encripty.set(enCipher);
			}
		} catch (Exception e) {
			logger.error("Init DES Cipher Failed!");
			e.printStackTrace();
		}
		
		return encripty.get();
	}
}
