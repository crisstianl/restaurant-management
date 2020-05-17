package eu.cristianl.ross.utils;

import java.math.BigDecimal;
import java.util.Collection;

import eu.cristianl.ross.logging.AppLogger;

public class Utils {
	// Maximum admitted quantity between two floats considered "equals"
	private static final double FLOAT_EPSILON = 1E-6;

	private static final String NUMBER_FORMAT = "%1$.2f";
	private static final String CURRENCY_FORMAT = "%1$.2f %2$s";

	/**
	 * Checks if a string is to be considered empty or blank (i.e. it is
	 * {@code null} or its length is 0
	 * 
	 * @param str
	 *            the string to check
	 * @return true if {@code str} is {@code null} or its length is 0, false
	 *         otherwise
	 */
	public static boolean isEmptyOrBlank(String str) {
		return (str == null) || (str.trim().length() == 0);
	}

	/**
	 * Checks if two floats can be considered "equals".<br>
	 * If {@link Float#equals(Object)} returns <code>true</code> also this functions
	 * returns <code>true</code>, otherwise a comparison based on an epsilon
	 * tolerance will be used. (i.e. |value1 - value2| < epsilon) <br>
	 * 
	 * @param value1
	 *            first float
	 * @param value2
	 *            second float
	 * @return true if can be considered "equals", false otherwise
	 */
	public static boolean equals(float value1, float value2) {
		if (Float.valueOf(value1).equals(value2)) {
			return true;
		}

		return (Math.abs(value1 - value2) < FLOAT_EPSILON);
	}

	/**
	 * Checks if two objects can be considered equals paying attention to
	 * {@code null} objects.<br>
	 * {@code null} is equals only to {@code null}, not {@code null} objects are
	 * compared using the {@link Object#equals(Object)} method, if
	 * {@code value1}=={@code value2} returns {@code true}
	 * 
	 * @param value1
	 *            first object
	 * @param value2
	 *            second object
	 * @return true if {@code value1} is equal to {@code value2}, false otherwise
	 * 
	 * @see Object#equals(Object)
	 */
	public static boolean equals(Object value1, Object value2) {
		if (value1 == value2) {
			return true;
		}

		if (value1 != null) {
			return value1.equals(value2);
		}

		return false;
	}

	/**
	 * Checks if two strings can be considered equals paying attention to
	 * {@code null} objects This method considers that null strings end empty
	 * strings are equal
	 * 
	 * @param value1
	 *            first object
	 * @param value2
	 *            second object
	 * @return true if {@code value1} is equal to {@code value2}, false otherwise
	 */
	public static boolean equalsStrings(String value1, String value2) {

		if ((value1 == null && "".equals(value2)) || ("".equals(value1) && value2 == null))
			return true;

		return equals(value1, value2);
	}

	/**
	 * Find in a array a specific item using the iteration algorithm O(n)
	 * 
	 * @param array
	 *            an array of objects not null
	 * @param item
	 *            an object to be found in array, can be null, should implement
	 *            equals
	 * @return false if the array is null or empty or if the item is not in array,
	 *         true otherwise
	 */
	public static <T> boolean contains(T[] array, T item) {
		if (array != null && array.length > 0) {
			for (final T i : array) {
				if (i == item || item != null && item.equals(i))
					return true;
			}
		}

		return false;
	}

	/**
	 * Check if two collections have the same elements, not necessary in the same
	 * order or the same instance
	 * 
	 * @return <b>FALSE</b>:</br>
	 *         -both or one of the parameter is null</br>
	 *         -if their size differs</br>
	 *         -if their elements does not match
	 */
	public static <T> boolean equals(Collection<T> c1, Collection<T> c2) {
		// If both or one of the collection is null return false
		if (c1 == null || c2 == null) {
			return false;
		} else if (c1.size() != c2.size()) {
			// If their size is different then they don't match
			return false;
		} else if (c1.equals(c2)) {
			// If their size match and also their instances
			return true;
		} else {
			// Both are not null, have the same size, and are distinct instances
			return c1.containsAll(c2);
		}
	}

	/** Check if a collection is null or has no elements */
	public static <T> boolean isEmptyOrNull(Collection<T> c) {
		return c == null || c.isEmpty();
	}

	/** Check if an array is null or has no elements */
	public static <T> boolean isEmptyOrNull(T[] array) {
		return array == null || array.length == 0;
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

	public static String format(float value) {
		return String.format(NUMBER_FORMAT, value);
	}

	public static String formatCurrency(float value, String currency) {
		return String.format(CURRENCY_FORMAT, value, currency);
	}

	/** Round number to the specified decimals, e.g. round(1.23456) = 1.235 */
	public static float round(float value, int digits) {
		BigDecimal decimal = new BigDecimal(value);
		return decimal.setScale(digits, BigDecimal.ROUND_HALF_EVEN).floatValue();
	}
}
