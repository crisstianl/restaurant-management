package eu.cristianl.ross.webclients;

import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.webclients.RestfulClient.HttpMethod;

public class TableDataClient implements RestfulClient.ICallback {
	private static final String URL = Resources.getServerAddr() + "/services/data/";

	private final Object LOCKER = new Object();

	private final String mSessionId;

	private Response mResponse;

	public TableDataClient(String sessionId) {
		this.mSessionId = sessionId;
	}

	public void downloadData(String table) {
		final RestfulClient client = new RestfulClient(HttpMethod.POST, URL + table);

		client.addHeader("Content-Type", "application/x-www-form-urlencoded");
		client.addFormParam("sessionId", mSessionId);

		client.execute(this);
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

	public static class Response {
		private final int mStatus;
		private String mMessage;

		private Response(int status, String message) {
			this.mStatus = status;
			this.mMessage = message;
		}

		public boolean isSuccess() {
			return mStatus == 200;
		}

		public String getMessage() {
			return mMessage;
		}
	}
}
