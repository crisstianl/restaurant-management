package eu.cristianl.ross.logging;

import com.j256.ormlite.logger.Log.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

/** ORMLite logger */
public class AppLogger {
	private final Logger mLogger;
	private static final AppLogger mInstance = new AppLogger();

	private AppLogger() {
		mLogger = LoggerFactory.getLogger(AppLogger.class);
	}

	public static void debug(String msg, Object... args) {
		mInstance.log(Level.DEBUG, msg, args);
	}

	public static void debug(Throwable throwable, Object... args) {
		mInstance.log(Level.DEBUG, throwable, args);
	}

	public void debug(Throwable throwable, String msg, Object... args) {
		mInstance.log(Level.DEBUG, throwable, msg, args);
	}

	public static void info(String msg, Object... args) {
		mInstance.log(Level.INFO, msg, args);
	}

	public static void info(Throwable throwable, Object... args) {
		mInstance.log(Level.INFO, throwable, args);
	}

	public static void info(Throwable throwable, String msg, Object... args) {
		mInstance.log(Level.INFO, throwable, msg, args);
	}

	public static void warn(String msg, Object... args) {
		mInstance.log(Level.WARNING, msg, args);
	}

	public static void warn(Throwable throwable, Object... args) {
		mInstance.log(Level.WARNING, throwable, args);
	}

	public static void warn(Throwable throwable, String msg, Object... args) {
		mInstance.log(Level.WARNING, throwable, msg, args);
	}

	public static void error(String msg, Object... args) {
		mInstance.log(Level.ERROR, msg, args);
	}

	public static void error(Throwable throwable, Object... args) {
		mInstance.log(Level.ERROR, throwable, args);
	}

	public static void error(Throwable throwable, String msg, Object... args) {
		mInstance.log(Level.ERROR, throwable, msg, args);
	}

	private void log(Level level, String msg, Object... args) {
		switch (level) {
		case DEBUG:
			mLogger.debug(getSafeString(msg), args);
			break;
		case WARNING:
			mLogger.warn(getSafeString(msg), args);
			break;
		case INFO:
			mLogger.info(getSafeString(msg), args);
			break;
		case ERROR:
			mLogger.error(getSafeString(msg), args);
			break;
		case TRACE:
			mLogger.trace(getSafeString(msg), args);
			break;
		case FATAL:
			mLogger.fatal(getSafeString(msg), args);
			break;
		}
	}

	private void log(Level level, Throwable throwable, Object... args) {
		switch (level) {
		case DEBUG:
			mLogger.debug(throwable, getSafeString(null), args);
			break;
		case WARNING:
			mLogger.warn(throwable, getSafeString(null), args);
			break;
		case INFO:
			mLogger.info(throwable, getSafeString(null), args);
			break;
		case ERROR:
			mLogger.error(throwable, getSafeString(null), args);
			break;
		case TRACE:
			mLogger.trace(throwable, getSafeString(null), args);
			break;
		case FATAL:
			mLogger.fatal(throwable, getSafeString(null), args);
			break;
		}
	}

	private void log(Level level, Throwable throwable, String msg, Object... args) {
		switch (level) {
		case DEBUG:
			mLogger.debug(throwable, getSafeString(msg), args);
			break;
		case WARNING:
			mLogger.warn(throwable, getSafeString(msg), args);
			break;
		case INFO:
			mLogger.info(throwable, getSafeString(msg), args);
			break;
		case ERROR:
			mLogger.error(throwable, getSafeString(msg), args);
			break;
		case TRACE:
			mLogger.trace(throwable, getSafeString(msg), args);
			break;
		case FATAL:
			mLogger.fatal(throwable, getSafeString(msg), args);
			break;
		}
	}

	private String getSafeString(String text) {
		if (text == null) {
			return "NULL";
		}
		return text;
	}

}
