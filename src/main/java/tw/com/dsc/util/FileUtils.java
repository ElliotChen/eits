package tw.com.dsc.util;

import org.apache.commons.io.FilenameUtils;

public abstract class FileUtils {
	public static String getExtension(String fileName) {
		return FilenameUtils.getExtension(fileName);
	}
}
