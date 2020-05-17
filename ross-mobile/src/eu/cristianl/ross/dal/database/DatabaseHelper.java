package eu.cristianl.ross.dal.database;

import java.io.File;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import eu.cristianl.ross.entities.Category;
import eu.cristianl.ross.entities.Contact;
import eu.cristianl.ross.entities.ContactTag;
import eu.cristianl.ross.entities.Currency;
import eu.cristianl.ross.entities.DocStatus;
import eu.cristianl.ross.entities.Item;
import eu.cristianl.ross.entities.ItemTag;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.entities.Tag;
import eu.cristianl.ross.entities.Unit;
import eu.cristianl.ross.logging.AppLogger;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "ross.db";
	private static final int DATABASE_VERSION = 1;

	// Keep the order
	private static final Class<?>[] ENTITIES = { Currency.class, Category.class, DocStatus.class, Tag.class, Unit.class,
			Contact.class, ContactTag.class, Item.class, ItemTag.class, Order.class, OrderRow.class };

	private static DatabaseHelper mInstance = null;

	private SQLiteDatabase mDatabase;

	/**
	 * Singleton instance
	 * 
	 * @param context
	 *            ->the application main activity
	 */
	private DatabaseHelper(Context context) {
		super(context, getDatabaseFile(context), null, DATABASE_VERSION);
		mDatabase = getWritableDatabase();
	}

	/**
	 * Thread safe method,to create only one instance of the helper
	 * 
	 * @param context
	 *            ->the application main activity
	 */
	public static synchronized void createDatabase(Context context) {
		mInstance = new DatabaseHelper(context);
	}

	/** Thread safe method, close database connection and destroy variables */
	public static synchronized void destroyDatabase() {
		mInstance.mDatabase.close();
		mInstance.mDatabase = null;
		mInstance = null;
	}

	/**
	 * Thread safe,only one thread can access the instance
	 * 
	 * @return the helper singleton instance
	 */
	public static synchronized DatabaseHelper getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		createTables(connectionSource);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		AppLogger.info("Updating database from version {} to version {}", oldVersion, newVersion);
		dropTables(connectionSource);
		createTables(connectionSource);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	/** Get table name using @DatabaseTable annotation */
	public static String getTable(Class<?> entityClass) {
		return entityClass.getAnnotation(DatabaseTable.class).tableName();
	}

	public void clearTable(Class<?> entityClass) {
		try {
			mDatabase.beginTransaction();
			TableUtils.clearTable(getConnectionSource(), entityClass);
			mDatabase.setTransactionSuccessful();
		} catch (Exception e) {
			AppLogger.error(e, "Failed to delete data from table {}", entityClass.getSimpleName());
		} finally {
			mDatabase.endTransaction();
		}
	}

	public void clearTable(String table) {
		try {
			mDatabase.beginTransaction();
			mDatabase.execSQL("DELETE from " + table);
			mDatabase.setTransactionSuccessful();
		} catch (Exception e) {
			AppLogger.error(e, "Failed to delete data from table {}", table);
		} finally {
			mDatabase.endTransaction();
		}
	}

	public void insert(String statement) {
		try {
			mDatabase.beginTransaction();
			mDatabase.execSQL(statement);
			mDatabase.setTransactionSuccessful();
		} catch (Exception e) {
			AppLogger.error(e, "Failed to execute {}", statement);
		} finally {
			mDatabase.endTransaction();
		}
	}

	private void createTables(ConnectionSource connSource) {
		AppLogger.info("Creating database tables");
		try {
			for (Class<?> clazz : ENTITIES) {
				TableUtils.createTable(connSource, clazz);
			}
		} catch (Exception e) {
			AppLogger.error(e, "Failed to create table");
		}
	}

	private void dropTables(ConnectionSource connSource) {
		AppLogger.info("Droping database tables");
		try {
			mDatabase.beginTransaction();
			for (Class<?> clazz : ENTITIES) {
				TableUtils.dropTable(connSource, clazz, true);
			}
			mDatabase.setTransactionSuccessful();
		} catch (Exception e) {
			AppLogger.error(e, "Failed to drop table");
		} finally {
			mDatabase.endTransaction();
		}
	}

	private static String getDatabaseFile(Context context) {
		File dbfile = null;
		// sdcard mounted
		if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
			dbfile = new File(context.getExternalFilesDir(null), DATABASE_NAME);
		} else {
			// internal memory
			dbfile = new File(context.getFilesDir(), DATABASE_NAME);
		}
		return dbfile.getAbsolutePath();
	}
}
