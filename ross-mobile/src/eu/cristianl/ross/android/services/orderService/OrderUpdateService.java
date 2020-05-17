package eu.cristianl.ross.android.services.orderService;

import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.webclients.OrderUpdateClient;

public class OrderUpdateService extends Service {
	public static final String ACTION = "eu.cristianl.ross.android.services.dataUpload.OrderUpdateService.ACTION";
	public static final String INTENT_SEED_ID = "INTENT_SEED_ID";
	public static final int ACTION_GET = 0;
	public static final int ACTION_PUT = 1;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final String sessionId = RossApplication.getInstance().getSessionId();
		final int action = intent.getIntExtra("ACTION", 0);
		final OrderSeed seed = intent.getParcelableExtra(INTENT_SEED_ID);

		final Thread worker;
		if (action == ACTION_GET) {
			worker = new SyncStatusWorker(sessionId, seed.getOrderIdArray(), seed.getDocStatus());
		} else if (action == ACTION_PUT) {
			worker = new UpdateStatusWorker(sessionId, seed.getOrderIdArray(), seed.getDocStatus());
		} else {
			throw new IllegalArgumentException("invalid action");
		}

		worker.start();

		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void notifyJobDone() {
		super.sendBroadcast(new Intent(ACTION));
	}

	private class SyncStatusWorker extends Thread {
		private final String mSessionId;
		private final String[] mOrderIds;
		private final String mDocStatus;

		private SyncStatusWorker(String sessionId, String[] orderIds, String docStatus) {
			mSessionId = sessionId;
			mOrderIds = orderIds;
			mDocStatus = docStatus;
		}

		@Override
		public void run() {
			try {
				final OrderUpdateClient client = new OrderUpdateClient(mSessionId, mOrderIds);
				client.getStatus();

				if (client.getResponse() != null && client.getResponse().isSuccess()) {
					for (Map.Entry<String, String> entry : client.getResponse().getOrders().entrySet()) {
						if (!mDocStatus.equals(entry.getValue())) { // status changed
							OrderDal.getDal().updateOrderStatus(entry.getKey(), entry.getValue());
						}
					}
				}

				notifyJobDone();
			} catch (Exception e) {
				AppLogger.error(e, e.getMessage());
			}
		}
	}

	private class UpdateStatusWorker extends Thread {
		private final String mSessionId;
		private final String[] mOrderIds;
		private final String mDocStatus;

		private UpdateStatusWorker(String mSessionId, String[] mOrderIds, String mDocStatus) {
			this.mSessionId = mSessionId;
			this.mOrderIds = mOrderIds;
			this.mDocStatus = mDocStatus;
		}

		@Override
		public void run() {
			try {
				final OrderUpdateClient client = new OrderUpdateClient(mSessionId, mOrderIds);
				client.updateStatus(mDocStatus);

				if (client.getResponse() != null && client.getResponse().isSuccess()) { // success
					for (String id : mOrderIds) {
						OrderDal.getDal().updateOrderStatus(id, mDocStatus);
					}
				}

				notifyJobDone();
			} catch (Exception e) {
				AppLogger.error(e, e.getMessage());
			}
		}
	}
}
