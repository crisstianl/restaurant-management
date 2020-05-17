package eu.cristianl.ross.webclients;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.entities.tables.OrderRowTable;
import eu.cristianl.ross.entities.tables.OrderTable;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.DateUtils;
import eu.cristianl.ross.webclients.RestfulClient.HttpMethod;

public class OrderUploadClient implements RestfulClient.ICallback {
	private static final String URL = Resources.getServerAddr() + "/services/order";

	private final Object LOCKER = new Object();

	private final String mSessionId;
	private final String mDeviceKey;

	private Response mResponse;

	public OrderUploadClient(String sessionId, String deviceKey) {
		this.mSessionId = sessionId;
		this.mDeviceKey = deviceKey;
	}

	public void upload(Order order, List<OrderRow> orderRows) throws JSONException {
		final RestfulClient client = new RestfulClient(HttpMethod.PUT, URL);

		client.addHeader("Content-Type", "application/json");
		client.addJsonParam("session-id", mSessionId);
		client.addJsonParam("device-key", mDeviceKey);
		client.addJsonParam("order", serializeOrder(order));
		client.addJsonParam("order-rows", serializeOrderRows(orderRows));

		client.execute(this);
	}

	@Override
	public void handleResponse(int status, String message) {
		mResponse = new Response(status, message);
		releaseLock();
	}

	private void releaseLock() {
		synchronized (LOCKER) {
			try {
				LOCKER.notify();
			} catch (IllegalMonitorStateException e) {
				AppLogger.error(e, "Failed to release lock since there was none");
			}
		}
	}

	/** Waits for server response for 5 seconds, then returns */
	public Response getResponse() {
		if (mResponse == null) {
			synchronized (LOCKER) {
				try {
					LOCKER.wait(RestfulClient.CONNECTION_TIMEOUT);
				} catch (InterruptedException e) {
					AppLogger.error(e, "Thread sleep interrupted");
				}
			}
		}
		return mResponse;
	}

	private static String serializeOrder(Order order) throws JSONException {
		final JSONObject object = new JSONObject();

		object.put(OrderTable.ID, order.getId());
		object.put(OrderTable.CONTACT_ID, order.getContact().getId());
		object.put(OrderTable.EMPLOYEE_ID, order.getEmployeeId());
		object.put(OrderTable.EMPLOYEE_NAME, order.getEmployeeName());
		object.put(OrderTable.TOTAL, order.getTotal());
		object.put(OrderTable.DISCOUNT, order.getDiscount());
		object.put(OrderTable.CURRENCY, order.getCurrency().getId());
		object.put(OrderTable.CREATION_DATE, DateUtils.getDatabaseTimestampString(order.getCreationDate()));
		object.put(OrderTable.LAST_CHANGE_DATE, DateUtils.getDatabaseTimestampString(order.getLastChangeDate()));
		object.put(OrderTable.DOC_STATUS_ID, order.getDocStatus().getId());
		object.put(OrderTable.NUMBER, order.getNumber());

		return object.toString();
	}

	private static String serializeOrderRows(List<OrderRow> orderRows) throws JSONException {
		final JSONArray array = new JSONArray();
		for (OrderRow orderRow : orderRows) {
			JSONObject object = new JSONObject();

			object.put(OrderRowTable.ID, orderRow.getId());
			object.put(OrderRowTable.ORDER_ID, orderRow.getOrder().getId());
			object.put(OrderRowTable.ITEM_ID, orderRow.getItem().getId());
			object.put(OrderRowTable.TOTAL, orderRow.getTotal());
			object.put(OrderRowTable.DISCOUNT, orderRow.getDiscount());
			object.put(OrderRowTable.QUANTITY, orderRow.getQuantity());
			object.put(OrderRowTable.CURRENCY_ID, orderRow.getCurrency().getId());
			if (orderRow.getUnit() != null) {
				object.put(OrderRowTable.UNIT_ID, orderRow.getUnit().getId());
			}

			array.put(object);
		}
		return array.toString();
	}

	public static class Response {
		private final int mStatus;

		private Response(int httpStatus, String message) {
			mStatus = httpStatus;
		}

		public boolean isSuccess() {
			return mStatus == 200;
		}
	}
}
