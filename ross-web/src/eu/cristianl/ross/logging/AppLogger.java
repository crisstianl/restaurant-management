package eu.cristianl.ross.logging;

import org.hibernate.annotations.common.util.impl.Log;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

/** Jboss logging */
public class AppLogger {
	private final Log mLogger;
	private static final AppLogger mInstance = new AppLogger();

	private AppLogger() {
		mLogger = LoggerFactory.make(AppLogger.class.getName());
	}

	public static void debug(String msg, Object... args) {
		mInstance.log(Level.DEBUG, msg, args);
	}

	public static void debug(Throwable throwable) {
		mInstance.log(Level.DEBUG, null, throwable);
	}

	public void debug(Throwable throwable, String msg, Object... args) {
		mInstance.log(Level.DEBUG, throwable, msg, args);
	}

	public static void info(String msg, Object... args) {
		mInstance.log(Level.INFO, msg, args);
	}

	public static void info(Throwable throwable) {
		mInstance.log(Level.INFO, null, throwable);
	}

	public static void info(Throwable throwable, String msg, Object... args) {
		mInstance.log(Level.INFO, throwable, msg, args);
	}

	public static void warn(String msg, Object... args) {
		mInstance.log(Level.WARNING, msg, args);
	}

	public static void warn(Throwable throwable) {
		mInstance.log(Level.WARNING, null, throwable);
	}

	public static void warn(Throwable throwable, String msg, Object... args) {
		mInstance.log(Level.WARNING, throwable, msg, args);
	}

	public static void error(String msg, Object... args) {
		mInstance.log(Level.ERROR, msg, args);
	}

	public static void error(Throwable throwable) {
		mInstance.log(Level.ERROR, null, throwable);
	}

	public static void error(Throwable throwable, String msg, Object... args) {
		mInstance.log(Level.ERROR, throwable, msg, args);
	}

	private void log(Level level, String msg, Object... args) {
		switch (level) {
		case DEBUG:
			mLogger.debugf(getSafeString(msg), args);
			break;
		case WARNING:
			mLogger.warnf(getSafeString(msg), args);
			break;
		case INFO:
			mLogger.infof(getSafeString(msg), args);
			break;
		case ERROR:
			mLogger.errorf(getSafeString(msg), args);
			break;
		case TRACE:
			mLogger.tracef(getSafeString(msg), args);
			break;
		case FATAL:
			mLogger.fatalf(getSafeString(msg), args);
			break;
		}
	}

	private void log(Level level, Throwable throwable, String msg, Object... args) {
		switch (level) {
		case DEBUG:
			mLogger.debugf(throwable, getSafeString(msg), args);
			break;
		case WARNING:
			mLogger.warnf(throwable, getSafeString(msg), args);
			break;
		case INFO:
			mLogger.infof(throwable, getSafeString(msg), args);
			break;
		case ERROR:
			mLogger.errorf(throwable, getSafeString(msg), args);
			break;
		case TRACE:
			mLogger.tracef(throwable, getSafeString(msg), args);
			break;
		case FATAL:
			mLogger.fatalf(throwable, getSafeString(msg), args);
			break;
		}
	}

	private String getSafeString(String text) {
		if (text == null) {
			return "NULL";
		}
		return text;
	}

	private static enum Level {
		INFO, DEBUG, ERROR, FATAL, TRACE, WARNING;
	}

}
