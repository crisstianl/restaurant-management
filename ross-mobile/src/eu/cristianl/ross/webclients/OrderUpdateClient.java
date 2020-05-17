package eu.cristianl.ross.webclients;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.webclients.RestfulClient.HttpMethod;

public class OrderUpdateClient implements RestfulClient.ICallback {
	private static final String URL = Resources.getServerAddr() + "/services/order/status";

	private final Object LOCKER = new Object();

	private final String mSessionId;
	private final String[] mOrderIds;

	private Response mResponse;

	public OrderUpdateClient(String sessionId, String[] orderIds) {
		this.mSessionId = sessionId;
		this.mOrderIds = orderIds;
	}

	public void getStatus() throws JSONException {
		final RestfulClient client = new RestfulClient(HttpMethod.POST, URL);

		client.addHeader("Content-Type", "application/json");
		client.addJsonParam("session-id", mSessionId);
		client.addJsonParam("orders", serializeOrders());
		client.execute(this);
	}

	public void updateStatus(String newDocStatus) throws JSONException {
		final RestfulClient client = new RestfulClient(HttpMethod.PUT, URL);

		client.addHeader("Content-Type", "application/json");
		client.addJsonParam("session-id", mSessionId);
		client.addJsonParam("orders", serializeOrderStatus(newDocStatus));
		client.execute(this);
	}

	@Override
	public void handleResponse(int status, String message) {
		try {
			mResponse = new Response(status, message);
		} catch (JSONException e) {
			AppLogger.error(e, "Failed to parse server response");
		} finally {
			releaseLock();
		}
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

	private JSONArray serializeOrders() throws JSONException {
		final JSONArray array = new JSONArray();
		for (String id : mOrderIds) {
			array.put(id);
		}

		return array;
	}

	private JSONArray serializeOrderStatus(String docStatus) throws JSONException {
		final JSONArray array = new JSONArray();
		for (String id : mOrderIds) {
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("status", docStatus);
			array.put(json);
		}
		return array;
	}

	public static class Response {
		private final int mStatus;
		private Map<String, String> mOrders;

		private Response(int httpStatus, String message) throws JSONException {
			mStatus = httpStatus;
			if (httpStatus == 200 && message != null && !message.isEmpty()) {
				final JSONArray jsonArray = new JSONArray(message);
				mOrders = new HashMap<String, String>(jsonArray.length());
				for (int i = 0; !jsonArray.isNull(i); i++) {
					final JSONObject json = jsonArray.getJSONObject(i);
					mOrders.put(json.getString("id"), json.getString("status"));
				}
			}
		}

		public boolean isSuccess() {
			return this.mStatus == 200;
		}

		public Map<String, String> getOrders() {
			return this.mOrders;
		}
	}

}
