package eu.cristianl.ross.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.dal.UserSessionDal;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.UserSession;
import eu.cristianl.ross.entities.tables.OrderRowTable;
import eu.cristianl.ross.entities.tables.OrderTable;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.DateUtils;
import eu.cristianl.ross.utils.Utils;

@Path("/order")
public class OrderService {

	@POST
	@Path("/status")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderStatus(String payload) {
		try {
			// parse incoming JSON data
			JSONObject jsonObject = new JSONObject(payload);

			// check session
			final String sessionId = jsonObject.getString("session-id");
			final UserSession userSession = UserSessionDal.I().getSession(sessionId);
			if (userSession == null) {
				return Response.status(404).entity("Session ID is invalid").build();
			} else if (userSession.getEndTime().before(DateUtils.now())) {
				return Response.status(400).entity("Session has expired").build();
			}

			// extract data
			final JSONArray jsonOrders = jsonObject.getJSONArray("orders");
			final List<String> orderIds = new ArrayList<String>();
			for (int i = 0; !jsonOrders.isNull(i); i++) {
				orderIds.add(jsonOrders.getString(i));
			}

			// query data
			final List<Order> orders = OrderDal.I().query(orderIds.toArray(new String[orderIds.size()]));
			if (orders == null || orders.isEmpty()) {
				return Response.status(404).entity("Invalid order ids").build();
			}

			// response
			final JSONArray results = new JSONArray();
			for (Order order : orders) {
				final JSONObject response = new JSONObject();
				response.put("id", order.getId());
				response.put("status", order.getDocStatus().getId());
				results.put(response);
			}
			return Response.status(200).entity(results.toString()).build();
		} catch (Exception e) {
			AppLogger.error(e, e.getMessage());
			return Response.status(500).entity("Invalid data").build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response saveOrder(String payload) {
		if (Utils.isEmptyOrBlank(payload)) {
			return Response.status(400).entity("Received null data").build();
		}

		// parse incoming JSON data
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(payload);
		} catch (Exception e) {
			AppLogger.error(e, e.getMessage());
			return Response.status(500).entity("Invalid data").build();
		}

		// Check session id for hackers
		try {
			final String sessionId = jsonObject.getString("session-id");
			final String deviceKey = jsonObject.optString("device-key", null);
			final UserSession userSession = UserSessionDal.I().getSession(sessionId);
			if (userSession == null) {
				return Response.status(404).entity("Session ID is invalid").build();

			} else if (userSession.getEndTime().before(DateUtils.now())) {
				return Response.status(400).entity("Session has expired").build();

			} else if (deviceKey != null) {
				userSession.setDeviceKey(deviceKey);
				UserSessionDal.I().updateSession(userSession);
			}
		} catch (Exception e) {
			AppLogger.error(e, e.getMessage());
			return Response.status(500).entity("Invalid data").build();
		}

		// insert order and order rows
		final List<String> inserts = new ArrayList<String>();
		try {
			parseOrder(jsonObject, inserts);
			parseOrderRows(jsonObject, inserts);

			// update database using native SQL insert statements
			int count = OrderDal.I().createOrders(inserts.toArray(new String[inserts.size()]));
			if (count == inserts.size()) {
				return Response.status(200).entity("OK").build();
			} else {
				return Response.status(500).entity("Failed to insert orders on server").build();
			}
		} catch (Exception e) {
			AppLogger.error(e, e.getMessage());
			return Response.status(500).entity("Invalid data").build();
		} finally {
			inserts.clear();
		}
	}

	@PUT
	@Path("/status")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateStatus(String payload) {
		try {
			// parse incoming JSON data
			JSONObject jsonObject = new JSONObject(payload);

			// check session
			final String sessionId = jsonObject.getString("session-id");
			final UserSession userSession = UserSessionDal.I().getSession(sessionId);
			if (userSession == null) {
				return Response.status(404).entity("Session ID is invalid").build();
			} else if (userSession.getEndTime().before(DateUtils.now())) {
				return Response.status(400).entity("Session has expired").build();
			}

			// extract data
			final JSONArray jsonOrders = jsonObject.getJSONArray("orders");
			for (int i = 0; !jsonOrders.isNull(i); i++) {
				final JSONObject json = jsonOrders.getJSONObject(i);
				OrderDal.I().updateOrderStatus(json.getString("id"), json.getString("status"));
			}

			// return
			return Response.status(200).build();
		} catch (Exception e) {
			AppLogger.error(e, e.getMessage());
			return Response.status(500).entity("Invalid data").build();
		}
	}

	private void parseOrder(JSONObject objectData, List<String> statements) throws JSONException {
		final JSONObject object = new JSONObject(objectData.getString("order"));

		// Insert into TABLE_NAME (...) VALUES (...);
		final StringBuilder statement = new StringBuilder();
		statement.append("INSERT INTO ").append(OrderTable.TABLE_NAME).append(" (");
		for (String column : OrderTable.COLUMNS) {
			statement.append(column).append(", ");
		}
		statement.delete(statement.length() - 2, statement.length()); // last ", "

		// values
		statement.append(") VALUES (");
		for (String column : OrderTable.COLUMNS) {
			final String value = object.optString(column, null);
			if (value != null) {
				statement.append("\'").append(value).append("\', ");
			} else {
				statement.append("NULL, ");
			}
		}
		statement.delete(statement.length() - 2, statement.length()); // last ", "
		statement.append(");");

		// statement list
		statements.add(statement.toString());
		statement.setLength(0); // clear
	}

	private void parseOrderRows(JSONObject objectData, List<String> statements) throws JSONException {
		final JSONArray array = new JSONArray(objectData.getString("order-rows"));

		// Insert into TABLE_NAME (...) VALUES (...);
		final StringBuilder columnsStatement = new StringBuilder();
		columnsStatement.append("INSERT INTO ").append(OrderRowTable.TABLE_NAME).append(" (");
		for (String column : OrderRowTable.COLUMNS) {
			columnsStatement.append(column).append(", ");
		}
		columnsStatement.delete(columnsStatement.length() - 2, columnsStatement.length()); // last ", "
		columnsStatement.append(") VALUES (");

		// values
		final StringBuilder statement = new StringBuilder();
		for (int i = 0; !array.isNull(i); i++) {
			final JSONObject object = array.getJSONObject(i);

			statement.append(columnsStatement);
			for (String column : OrderRowTable.COLUMNS) {
				String value = object.optString(column, null);
				if (value != null) {
					statement.append("\'").append(value).append("\', ");
				} else {
					statement.append("NULL, ");
				}
			}
			statement.delete(statement.length() - 2, statement.length()); // last ", "
			statement.append(");");

			// statement list
			statements.add(statement.toString());
			statement.setLength(0); // clear
		}
		columnsStatement.setLength(0); // clear
	}

}
