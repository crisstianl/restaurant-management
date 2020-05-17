package eu.cristianl.ross.api;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import eu.cristianl.ross.dal.UserSessionDal;
import eu.cristianl.ross.entities.UserSession;
import eu.cristianl.ross.logging.AppLogger;

public class OrderNotificationService implements Runnable {
	private static final String KEY = "AIzaSyAaSbFPupbjk40uxveDbFBLb5uaQ9SvAJ4";
	private static final String URL = "https://fcm.googleapis.com/fcm/send";

	private String mOrderId;
	private String mOrderStatusId;
	private String mEmployeeId;

	public OrderNotificationService(String employeeId, String orderId, String orderStatusId)
			throws IllegalArgumentException {
		if (employeeId == null || orderId == null || orderStatusId == null) {
			throw new IllegalArgumentException();
		}
		this.mEmployeeId = employeeId;
		this.mOrderId = orderId;
		this.mOrderStatusId = orderStatusId;
	}

	public final void post() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		PostMethod method = null;
		try {
			final UserSession session = UserSessionDal.I().getSession(mEmployeeId);
			if (session == null || session.getDeviceKey() == null || session.getDeviceKey().isEmpty()) {
				return;
			}

			// Headers
			method = new PostMethod(URL);
			method.addRequestHeader("Authorization", "key=" + KEY);
			method.addRequestHeader("Content-Type", "application/json");

			// Body
			method.setRequestEntity(getRequestBody(session.getDeviceKey()));

			// execute
			HttpClientParams params = new HttpClientParams();
			params.setParameter(HttpClientParams.SO_TIMEOUT, new Long(5000));
			final HttpClient client = new HttpClient(params);

			final int httpCode = client.executeMethod(method);
			AppLogger.info("HTTP {0} : {1}", httpCode, new String(method.getResponseBody()));
		} catch (Exception e) {
			AppLogger.error(e, "Failed to send notification");
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	private StringRequestEntity getRequestBody(String deviceKey) throws JSONException, IOException {
		JSONObject payload = new JSONObject();
		payload.put("order-id", mOrderId);
		payload.put("order-status", mOrderStatusId);

		JSONObject json = new JSONObject();
		json.put("to", deviceKey);
		json.put("data", payload);

		return new StringRequestEntity(json.toString(), "application/json", "utf-8");
	}
}
