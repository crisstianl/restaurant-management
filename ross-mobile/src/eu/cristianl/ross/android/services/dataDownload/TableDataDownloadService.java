package eu.cristianl.ross.android.services.dataDownload;

import java.io.BufferedReader;
import java.io.StringReader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.dal.database.DatabaseHelper;
import eu.cristianl.ross.entities.tables.CategoryTable;
import eu.cristianl.ross.entities.tables.ContactTable;
import eu.cristianl.ross.entities.tables.ContactTagTable;
import eu.cristianl.ross.entities.tables.CurrencyTable;
import eu.cristianl.ross.entities.tables.DocStatusTable;
import eu.cristianl.ross.entities.tables.ItemTable;
import eu.cristianl.ross.entities.tables.ItemTagTable;
import eu.cristianl.ross.entities.tables.TagTable;
import eu.cristianl.ross.entities.tables.UnitTable;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.webclients.TableDataClient;

public class TableDataDownloadService extends Service {
	public static final String ACTION = "eu.cristianl.ross.android.services.dataDownload.TableDataDownloadService.ACTION";
	public static final String MESSAGE_KEY = "NOTIFY_PROGRESS_MESSAGE";

	private static final String THREAD_GROUP_A = "DownloadWorkersGroupA";
	private static final String THREAD_GROUP_B = "DownloadWorkersGroupB";

	// Tables that are independent and have no foreign keys to other tables
	private static final String[] TABLES_G1 = { UnitTable.TABLE_NAME, CurrencyTable.TABLE_NAME,
			DocStatusTable.TABLE_NAME, CategoryTable.TABLE_NAME, TagTable.TABLE_NAME };

	// Tables that have foreign keys
	private static final String[] TABLES_G2 = { ContactTable.TABLE_NAME, ContactTagTable.TABLE_NAME,
			ItemTable.TABLE_NAME, ItemTagTable.TABLE_NAME };

	/** Threads downloading data from server */
	private ThreadGroup mWorkersGroupA;
	private ThreadGroup mWorkersGroupB;

	/** Processed tables */
	private volatile int mJobsDone;

	/** Total number of tables */
	private volatile int mJobsCount;

	@Override
	public void onCreate() {
		super.onCreate();
		mJobsCount = TABLES_G1.length + TABLES_G2.length;
		mWorkersGroupA = new ThreadGroup(THREAD_GROUP_A);
		mWorkersGroupB = new ThreadGroup(THREAD_GROUP_B);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final String sessionId = RossApplication.getInstance().getSessionId();

		DownloadWorker teamA = new DownloadWorker(mWorkersGroupA, sessionId, TABLES_G1);
		teamA.start();

		DownloadWorker teamB = new DownloadWorker(mWorkersGroupB, sessionId, TABLES_G2);
		teamB.start();

		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mWorkersGroupA.destroy();
		mWorkersGroupB.destroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void progress(String messageToUser, int percent) {
		if (percent == 100) {
			synchronized (this) {
				mJobsDone++;
			}
		}
		float progress = (float) mJobsDone / (float) mJobsCount * 100F;
		broadcastMessage(new MessageSeed(MessageSeed.TYPE_PROGRESS, (int) progress, messageToUser));

		if (mJobsCount == mJobsDone) {
			broadcastMessage(new MessageSeed(MessageSeed.TYPE_END, 100, "Completed..."));
		}
	}

	private void broadcastMessage(MessageSeed message) {
		Intent intent = new Intent(ACTION);
		intent.putExtra(MESSAGE_KEY, message);
		super.sendBroadcast(intent);
	}

	private void wakeGroupB() {
		Thread[] workers = new Thread[mWorkersGroupB.activeCount()];
		mWorkersGroupB.enumerate(workers);
		for (Thread worker : workers) {
			synchronized (worker) {
				worker.notify();
			}
		}
	}

	private class DownloadWorker extends Thread {
		private static final String THREAD_NAME = "TableDataDownloader";

		private final String mSessionId;
		private final String[] mTables;

		private DownloadWorker(ThreadGroup parentGroup, String sessionId, String[] tables) {
			super(parentGroup, THREAD_NAME);
			mSessionId = sessionId;
			mTables = tables;
		}

		@Override
		public void run() {
			// wait for thread A
			if (THREAD_GROUP_B.equals(getThreadGroup().getName())) {
				waitForGroupA();
			}

			for (String table : mTables) {
				progress("Clearing data from table \n\"" + table + "\"", 0);
				DatabaseHelper.getInstance().clearTable(table);

				progress("Downloading data from table \n\"" + table + "\"", 25);
				TableDataClient client = new TableDataClient(mSessionId);
				client.downloadData(table);

				progress("Inserting data in table \n\"" + table + "\"", 50);
				if (client.getResponse() != null && client.getResponse().isSuccess()) {
					processTable(table, client.getResponse().getMessage());
				}

				progress("Completed table \n\"" + table + "\"", 100);
			}

			// Last thread from GroupA should wake up GroupB threads
			if (getThreadGroup().activeCount() <= 1) {
				wakeGroupB();
			}
		}

		private void processTable(String table, String data) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new StringReader(data));

				StringBuilder statement = new StringBuilder();
				statement.append("INSERT into ").append(table);
				statement.append(" (").append(reader.readLine()).append(")").append(" VALUES (");
				String values = null;
				while ((values = reader.readLine()) != null) {
					DatabaseHelper.getInstance().insert(statement.toString() + values + ");");
				}
			} catch (Exception e) {
				AppLogger.error(e, e.getMessage());
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private synchronized void waitForGroupA() {
			try {
				this.wait();
			} catch (InterruptedException e) {
				AppLogger.error(e, "Thread sleep interupted");
			}
		}
	}
}
