package eu.cristianl.ross.android.services.orderService;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.dal.DocStatusDal;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.dal.OrderRowDal;
import eu.cristianl.ross.entities.DocStatus;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.DateUtils;
import eu.cristianl.ross.webclients.OrderUploadClient;

public class OrderUploadService extends Service {
	public static final String ACTION = "eu.cristianl.ross.android.services.dataUpload.OrderUploadService.ACTION";

	public static final String INTENT_SEED_ID = "INTENT_SEED_ID";
	public static final String MESSAGE_KEY = "MESSAGE_KEY";

	private static final int MAX_THREADS = 10;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final String sessionId = RossApplication.getInstance().getSessionId();
		final String deviceKey = RossApplication.getInstance().getDeviceKey();

		final OrderSeed seed = intent.getParcelableExtra(INTENT_SEED_ID);
		final LinkedList<String> orders = seed.getOrderIds();

		final int ordersPerThread = orders.size() / MAX_THREADS;
		int remaining = orders.size() % MAX_THREADS;
		for (int i = 0; i < MAX_THREADS; i++) {
			// Add a surplus of 1 and decrement the remaining
			int surplus = 0;
			if (remaining-- > 0) {
				surplus = 1;
			}
			int count = ordersPerThread + surplus;
			if (count <= 0) {
				break;
			}

			UploadWorker worker = new UploadWorker(sessionId, deviceKey, poll(orders, count));
			worker.start();
		}

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

	private static String[] poll(Queue<String> orders, int count) {
		String[] retArray = new String[count];
		for (int i = 0; i < retArray.length; i++) {
			retArray[i] = orders.poll();
		}

		return retArray;
	}

	private void notifyJobDone(String orderId) {
		Intent intent = new Intent(ACTION);
		intent.putExtra(MESSAGE_KEY, orderId);
		sendBroadcast(intent);
	}

	private class UploadWorker extends Thread {
		private final String mSessionId;
		private final String mDeviceKey;
		private final String[] mOrderIds;

		private UploadWorker(String sessionId, String deviceKey, String[] orderIds) {
			mSessionId = sessionId;
			mDeviceKey = deviceKey;
			mOrderIds = orderIds;
		}

		@Override
		public void run() {
			final DocStatus sendStatus = DocStatusDal.getDal().getStatusSubmitted();

			for (String orderId : mOrderIds) {
				try {
					// Query data
					final Order order = OrderDal.getDal().getOrder(orderId);
					if (order == null) {
						continue;
					}

					// query order rows
					final List<OrderRow> orderRows = OrderRowDal.getDal().query(orderId);
					if (orderRows == null || orderRows.isEmpty()) {
						continue;
					}

					order.setDocStatus(sendStatus);
					order.setLastChangeDate(DateUtils.now());

					// send data to server
					OrderUploadClient client = new OrderUploadClient(mSessionId, mDeviceKey);
					client.upload(order, orderRows);

					if (client.getResponse() != null && client.getResponse().isSuccess()) {
						OrderDal.getDal().update(order); // Set status "S" also in db
						// Notify views
						notifyJobDone(order.getId());
					} else {
						// leave it with status "P", will be sent next time
					}
				} catch (Exception e) {
					AppLogger.error(e, e.getMessage());
				}
			}
		}
	}

}
