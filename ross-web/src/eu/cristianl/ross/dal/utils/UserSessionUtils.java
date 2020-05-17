package eu.cristianl.ross.dal.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class UserSessionUtils {
	public static final int SESSION_TIME = 8;
	private static final int SESSION_ID_LENGTH = 256;

	/**
	 * Generate an unique session string using user id, user name and a random
	 * string of 10 characters.
	 * 
	 * @return string or throws Exception
	 */
	public static String generateSessionId(String userId, String userName) throws Exception {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			stream.write(userId.getBytes());
			stream.write(userName.getBytes("UTF-8"));
			stream.write(generateRandomString(10).getBytes());

			final StringBuilder retValue = new StringBuilder();
			byte[] digest = hash(stream.toByteArray());
			for (int i = 0; i < digest.length && i < SESSION_ID_LENGTH; i++) {
				retValue.append(Integer.toHexString(0xFF & digest[i]));
			}
			return retValue.toString();
		} catch (UnsupportedEncodingException e) {
			throw e;
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** Return start time + 8 hours */
	public static Date generateSessionEndTime(Date startTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		calendar.add(Calendar.HOUR_OF_DAY, SESSION_TIME);
		return calendar.getTime();
	}

	private static String generateRandomString(int length) {
		final String ALPHABETH = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random rnd = new Random();

		StringBuilder retValue = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			retValue.append(ALPHABETH.charAt(rnd.nextInt(ALPHABETH.length())));
		}
		return retValue.toString();
	}

	private static byte[] hash(byte[] input) throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("SHA-256").digest(input);
	}
}
