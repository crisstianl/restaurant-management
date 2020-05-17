package eu.cristianl.ross.utils;

import java.io.InputStream;

public class FileUtils {

	private static final String SQL_INSERTS = "/resources/sql-scripts/z01_inserts.sql";

	public static InputStream openInserts() {
		return FileUtils.class.getClassLoader().getResourceAsStream(SQL_INSERTS);
	}
}
