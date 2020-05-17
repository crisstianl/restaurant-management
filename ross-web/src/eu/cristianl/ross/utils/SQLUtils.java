package eu.cristianl.ross.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

import org.hibernate.Session;

import eu.cristianl.ross.dal.database.DatabaseHelper;
import eu.cristianl.ross.logging.AppLogger;

public class SQLUtils {
	public static final String COMMENT_START = "<!--";
	public static final String COMMENT_END = "-->";
	public static final String SQL = ".sql";

	private static final String THREAD_GROUP_NAME = "SQLUtils.workers";
	private static final int MAX_STATEMENTS_IN_MEMORY = 10;

	private Queue<String> mStatements = new LinkedList<String>();

	private volatile boolean mJobDone = false;
	private final Object LOCKER = new Object();

	private SQLUtils() {
	}

	public static void insertTestData() {
		final InputStream is = FileUtils.openInserts();

		// create 2 threads: reader and inserter
		final SQLUtils sqlUtils = new SQLUtils();
		ThreadGroup sqlGroup = new ThreadGroup(THREAD_GROUP_NAME);
		sqlUtils.new AsyncSqlReader(sqlGroup, is).start(); // reads from file
		sqlUtils.new AsyncSqlExecutor(sqlGroup).start(); // executes SQL statements
	}

	private String poll() {
		synchronized (LOCKER) {
			while (mStatements.isEmpty()) {
				try {
					LOCKER.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String retValue = mStatements.poll();
			LOCKER.notify();
			return retValue;
		}
	}

	private void add(String statement) {
		synchronized (LOCKER) {
			while (mStatements.size() >= MAX_STATEMENTS_IN_MEMORY) {
				try {
					LOCKER.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			mStatements.add(statement);
			LOCKER.notify();
		}
	}

	private class AsyncSqlExecutor extends Thread {
		private static final String NAME = "ASYNC_SQL_EXECUTOR";

		private AsyncSqlExecutor(ThreadGroup group) {
			super(group, NAME);
		}

		@Override
		public void run() {
			Session session = null;
			try {
				session = DatabaseHelper.getInstance().beginTransaction();
				while (!mJobDone || !mStatements.isEmpty()) {
					session.createSQLQuery(poll()).executeUpdate();
				}
				DatabaseHelper.getInstance().endTransaction(session);
			} catch (Exception e) {
				DatabaseHelper.getInstance().cancelTransaction(session);
				AppLogger.error(e, "Failed to execute insert");
			}
		}
	}

	private class AsyncSqlReader extends Thread {
		private static final String NAME = "ASYNC_SQL_READER";
		private InputStream mSqlStream;

		private AsyncSqlReader(ThreadGroup group, InputStream sqlStream) {
			super(group, NAME);
			mSqlStream = sqlStream;
		}

		@Override
		public void run() {
			final String SEMICOLON = ";";
			StringBuilder statement = new StringBuilder();
			BufferedReader reader = null;
			String line = null;
			boolean comment = false;

			try {
				reader = new BufferedReader(new InputStreamReader(mSqlStream, "UTF8"));
				while ((line = reader.readLine()) != null) {
					// Empty line
					if (line.length() == 0) {
						continue;
					}

					// Comment token
					if (comment) {
						if (line.endsWith(COMMENT_END)) {
							comment = false;
						}
						continue;
					}

					if (line.startsWith(COMMENT_START)) {
						comment = true;
						if (line.endsWith(COMMENT_END)) {
							comment = false;
						}
						continue;
					}

					statement.append(line);

					if (line.endsWith(SEMICOLON)) {
						add(statement.toString());
						statement = new StringBuilder();
					}
				}
			} catch (IOException e) {
				AppLogger.error(e, "Failed to read line from file");
			} catch (Exception e) {
				AppLogger.error(e, "Failed to open file");
			} finally {
				mJobDone = true;
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					reader = null;
				}
				if (mSqlStream != null) {
					try {
						mSqlStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					mSqlStream = null;
				}
			}
		}
	}
}
