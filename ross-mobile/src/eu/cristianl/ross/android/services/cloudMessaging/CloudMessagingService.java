package eu.cristianl.ross.android.services.cloudMessaging;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.android.widgets.Console;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.support.DocStatusType;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.Utils;

public class CloudMessagingService extends BroadcastReceiver {
	private static final String PROJECT_KEY = "530020023257";
	private static final String key = "AIzaSyAaSbFPupbjk40uxveDbFBLb5uaQ9SvAJ4";

	public static void register(Context context) {
		new RegisterClient(context).start();
	}

	public static void unregister(Context context) {
		new UnregisterClient(context).start();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) {
			handleRegistrationResponse(intent);

		} else if (action.equals("com.google.android.c2dm.intent.RECEIVE")) {
			handleServerData(intent);
		}
	}

	private void handleRegistrationResponse(Intent intent) {
		final String error = intent.getStringExtra("error");
		if (!Utils.isEmptyOrBlank(error)) {
			AppLogger.error("Registration failed with error {}", error);
			return;
		}

		final String unregistered = intent.getStringExtra("unregistered");
		if (!Utils.isEmptyOrBlank(unregistered)) {
			RossApplication.getInstance().setDeviceKey(null);
			return;
		}

		final String registrationId = intent.getStringExtra("registration_id");
		if (!Utils.isEmptyOrBlank(registrationId)) {
			RossApplication.getInstance().setDeviceKey(registrationId);
		}
	}

	private void handleServerData(Intent intent) {
		String orderId = intent.getStringExtra("order-id");
		String docStatusId = intent.getStringExtra("order-status");
		String message = null;

		if (DocStatusType.READY.getCode().equals(docStatusId)) {
			OrderDal.getDal().markOrderAsReady(new Order(orderId));
			message = Resources.getString(R.string.gcm_broadcast_receiver_ready_order);

		} else if (DocStatusType.CLOSED.getCode().equals(docStatusId)) {
			OrderDal.getDal().markOrderAsClosed(new Order(orderId));
			message = Resources.getString(R.string.gcm_broadcast_receiver_closed_order);
		}

		if (message != null) {
			Console.get(message).info();
		}
	}

	private static class RegisterClient extends Thread {

		private Context context;

		private RegisterClient(Context context) {
			super("CloudMessagingService");
			this.context = context;
		}

		@Override
		public void run() {
			final Intent intent = new Intent("com.google.firebase.INSTANCE_ID_EVENT");
			final PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, new Intent(), 0);
			intent.putExtra("app", pendingIntent);
			intent.putExtra("sender", PROJECT_KEY);
			this.context.startService(intent);
		}
	}

	private static class UnregisterClient extends Thread {

		private Context context;

		private UnregisterClient(Context context) {
			super("CloudMessagingService");
			this.context = context;
		}

		@Override
		public void run() {
			final Intent intent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
			final PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, new Intent(), 0);
			intent.putExtra("app", pendingIntent);
			this.context.startService(intent);
		}
	}

}
