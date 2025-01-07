package com.company.util;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.log4j.Log4j;

@Log4j
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
	
	public static boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch(Exception ignored) {
			log.error("non-image file is stored: " + file.toString());
		}
		return false;
	}
}
