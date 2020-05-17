package eu.cristianl.ross.utils;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import eu.cristianl.ross.logging.AppLogger;

public class Utils {

	public static boolean isEmptyOrBlank(String text) {
		return (text == null) || (text.length() == 0);
	}

	public static boolean isEmptyOrNull(Collection<?> collection) {
		return (collection == null) || (collection.isEmpty());
	}

	public static <T> boolean isEmptyOrNull(T[] array) {
		return (array == null) || (array.length == 0);
	}

	public static String getClientIp(HttpServletRequest request) {
		String retValue = request.getHeader("X-FORWARDED-FOR");
		if (isEmptyOrBlank(retValue)) {
			retValue = request.getRemoteAddr();
		}
		return retValue;
	}

	public static int tryReadInt(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			AppLogger.error(e, "Failed to parse int {}", value);
		}
		return defaultValue;
	}

	public static float tryReadFloat(String value, float defaultValue) {
		try {
			if (!isEmptyOrBlank(value)) {
				return Float.parseFloat(value);
			}
		} catch (Exception e) {
			AppLogger.error(e, "Failed to parse float {}", value);
		}
		return defaultValue;
	}
}
