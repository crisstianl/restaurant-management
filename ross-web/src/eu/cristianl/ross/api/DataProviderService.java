package eu.cristianl.ross.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.cristianl.ross.dal.UserSessionDal;
import eu.cristianl.ross.dal.database.DatabaseHelper;
import eu.cristianl.ross.entities.UserSession;
import eu.cristianl.ross.entities.tables.BaseCurrencyTable;
import eu.cristianl.ross.entities.tables.CategoryTable;
import eu.cristianl.ross.entities.tables.ContactTable;
import eu.cristianl.ross.entities.tables.CurrencyTable;
import eu.cristianl.ross.entities.tables.DocStatusTable;
import eu.cristianl.ross.entities.tables.ItemTable;
import eu.cristianl.ross.entities.tables.OrderRowTable;
import eu.cristianl.ross.entities.tables.OrderTable;
import eu.cristianl.ross.entities.tables.TagTable;
import eu.cristianl.ross.entities.tables.UnitTable;
import eu.cristianl.ross.entities.views.ContactTagView;
import eu.cristianl.ross.entities.views.ItemTagView;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.DateUtils;
import eu.cristianl.ross.utils.Utils;

@Path("/data")
public class DataProviderService {
	private static final String[] ALLOWED_TABLES = { TagTable.TABLE_NAME, CategoryTable.TABLE_NAME,
			UnitTable.TABLE_NAME, DocStatusTable.TABLE_NAME, BaseCurrencyTable.TABLE_NAME, CurrencyTable.TABLE_NAME,
			ContactTable.TABLE_NAME, ContactTagView.VIEW_NAME, ItemTable.TABLE_NAME, ItemTagView.VIEW_NAME,
			OrderTable.TABLE_NAME, OrderRowTable.TABLE_NAME };

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {
		return Response.status(200).entity("Hello").build();
	}

	@POST
	@Path("/{table}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTableData(@PathParam("table") String table, @FormParam("sessionId") String sessionId) {
		try {
			if (Utils.isEmptyOrBlank(table) || Utils.isEmptyOrBlank(sessionId)) {
				return Response.status(400).build();
			}

			// Check session id for hackers
			Response response = checkSessionId(sessionId);
			if (response.getStatus() != 200) {
				return response;
			}

			// Check table permission
			response = checkTablePermission(table);
			if (response.getStatus() != 200) {
				return response;
			}

			return queryTable(table);
		} catch (Exception e) {
			AppLogger.error(e, "Failed to query table");
			return Response.status(500).build();
		}
	}

	private Response checkSessionId(String sessionId) {
		UserSession userSession = UserSessionDal.I().getSession(sessionId);
		if (userSession == null) {
			return Response.status(404).entity("Session ID is invalid").build();
		}
		if (userSession.getEndTime().before(DateUtils.now())) {
			return Response.status(400).entity("Session has expired").build();
		}

		return Response.status(200).build();
	}

	private Response checkTablePermission(String table) {
		for (String allowedTable : ALLOWED_TABLES) {
			if (table.equals(allowedTable)) {
				return Response.status(200).build();
			}
		}
		return Response.status(401).entity("Not allowed").build();
	}

	private Response queryTable(final String tableName) {
		final String[] columns = DatabaseHelper.getInstance().getTableColumns(tableName);
		if (Utils.isEmptyOrNull(columns)) {
			return Response.status(404).entity("No such table").build();
		}

		final List<Object> rows = DatabaseHelper.getInstance().getTableData(tableName, columns);
		if (Utils.isEmptyOrNull(rows)) {
			return Response.status(404).entity("Table is empty").build();
		}

		return Response.status(200).entity(serializeResults(columns, rows)).build();
	}

	/**
	 * Serialize table data in CSV format, as follow: </br>
	 * - first line contains the table columns.</br>
	 * - next lines contains the row values in the order matching the columns.
	 */
	private static String serializeResults(String[] columns, List<Object> rows) {
		final StringBuilder retValue = new StringBuilder();
		// First line contains the columns
		for (String column : columns) {
			retValue.append(column).append(",");
		}
		retValue.delete(retValue.length() - 1, retValue.length()); // last ","

		// Following lines contains the results
		for (Object row : rows) {
			retValue.append("\n");
			final Object[] rowValues = (Object[]) row;
			for (Object rowValue : rowValues) {
				retValue.append("\'").append(toString(rowValue)).append("\',");
			}
			retValue.delete(retValue.length() - 1, retValue.length()); // last ","
		}

		return retValue.toString();
	}

	private static String toString(Object value) {
		return value != null ? value.toString() : "NULL";
	}

}
