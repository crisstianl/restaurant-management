package eu.cristianl.ross.webclients;

import org.json.JSONException;
import org.json.JSONObject;

import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.webclients.RestfulClient.HttpMethod;

public class LoginClient implements RestfulClient.ICallback {
	private static final String URL = Resources.getServerAddr() + "/services/login";

	private final Object LOCKER = new Object();

	private Response mResponse = null;

	public void login(String username, String password) {
		final RestfulClient client = new RestfulClient(HttpMethod.POST, URL);

		client.addHeader("Content-Type", "application/x-www-form-urlencoded");
		client.addFormParam("name", username);
		client.addFormParam("password", password);

		client.execute(this);
	}

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

	public static class Response {
		private final int mStatus;

		private String mSessionId;
		private long mSessionTime;

		private String mEmployeeId;
		private String mEmployeeUsername;
		private String mEmployeeName;

		private Response(int status, String message) throws JSONException {
			mStatus = status;
			if (status == 200) { // success
				final JSONObject json = new JSONObject(message);
				mSessionId = json.getString("session_id");
				mSessionTime = Long.parseLong(json.getString("session_time"));
				mEmployeeId = json.getString("employee_id");
				mEmployeeUsername = json.getString("employee_username");
				mEmployeeName = json.getString("employee_name");
			}
		}

		public boolean isSuccess() {
			return mStatus == 200;
		}

		public String getEmployeeId() {
			return mEmployeeId;
		}

		public String getEmployeeName() {
			return mEmployeeName;
		}

		public String getEmployeeUsername() {
			return mEmployeeUsername;
		}

		public String getSessionId() {
			return mSessionId;
		}

		public long getSessionTime() {
			return mSessionTime;
		}
	}
}
