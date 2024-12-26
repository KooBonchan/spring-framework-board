package com.company.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtil {
	public static String decodeImagePath(String imagePath) {
		return imagePath.replace("_SLASH_", File.separator);
	}
	public static String decodeRealFileName(String realFileName) {
		return realFileName.replace("_DOT_", ".");
	}
	
	public static String encodeImagePath(String imagePath) {
		return imagePath.replace(File.separator, "_SLASH_");
	}
	public static String encodeRealFileName(String realFileName) {
		return realFileName.replace(".", "_DOT_");
	}
	public static String getPathByDate() {
		return new SimpleDateFormat("yy-MM-dd")
				.format(new Date())
				.replace("-", File.separator);
	}
}
