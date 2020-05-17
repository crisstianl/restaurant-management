package eu.cristianl.ross.dal.database;

import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.type.StringType;

import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.Utils;

public class DatabaseHelper {
	private static final String HIBERNATE_CONFIGURATION_FILE = "/resources/hibernate.xml";

	private static DatabaseHelper mInstance;
	private final SessionFactory mSessionFactory;

	private DatabaseHelper() {
		Configuration configuration = new Configuration().configure(HIBERNATE_CONFIGURATION_FILE);
		ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		mSessionFactory = configuration.buildSessionFactory(registry);
	}

	public static synchronized void create() {
		mInstance = new DatabaseHelper();
	}

	public static synchronized void destroy() {
		mInstance.mSessionFactory.close();
		mInstance = null;
	}

	public static DatabaseHelper getInstance() {
		return mInstance;
	}

	public Session beginTransaction() {
		Session retValue = mSessionFactory.openSession();
		retValue.beginTransaction();
		return retValue;
	}

	public void endTransaction(Session session) {
		session.getTransaction().commit();
		session.close();
	}

	public void cancelTransaction(Session session) {
		session.getTransaction().rollback();
		session.close();
	}

	public AbstractEntityPersister getClassMetadata(Class<?> clazz) {
		return (AbstractEntityPersister) mSessionFactory.getClassMetadata(clazz);
	}

	public String getTableName(Class<?> clazz) {
		return clazz.getAnnotation(Table.class).name();
	}

	public Class<?> getEntityClass(String tableName) {
		Map<String, ClassMetadata> classesMetadata = mSessionFactory.getAllClassMetadata();
		for (Map.Entry<String, ClassMetadata> entry : classesMetadata.entrySet()) {
			try {
				Class<?> clazz = Class.forName(entry.getKey());
				String tableAnnot = getTableName(clazz);
				if (!Utils.isEmptyOrBlank(tableAnnot) && tableAnnot.equals(tableName)) {
					return clazz;
				}
			} catch (Exception e) {
				AppLogger.error(e, "Failed create class from name %1$s", entry.getKey());
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public String[] getTableColumns(String table) {
		List<String> results = null;
		Session session = null;
		try {
			session = beginTransaction();
			SQLQuery query = session.createSQLQuery(QueryBuilder.selectTableColumnsInfo(table));
			query.addScalar("COLUMN_NAME", StringType.INSTANCE);
			results = query.list();
			endTransaction(session);
		} catch (Exception e) {
			cancelTransaction(session);
			AppLogger.error(e, "Failed to retrieve table columns");
		}

		return results.toArray(new String[] {});
	}

	@SuppressWarnings("unchecked")
	public List<Object> getTableData(String table, String[] columns) {
		List<Object> results = null;
		Session session = null;
		try {
			session = beginTransaction();
			SQLQuery query = session.createSQLQuery(new QueryBuilder(table, columns).toString());
			results = query.list();
			endTransaction(session);
		} catch (Exception e) {
			cancelTransaction(session);
			AppLogger.error(e, "Failed to retrieve table data");
		}
		return results;
	}
}
