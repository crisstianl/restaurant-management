package eu.cristianl.ross.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import eu.cristianl.ross.android.utils.SystemUtils;

public class ConnectionUtils {
	public static final int NO_CONNECTION = -1;

	/** Return the network connection type if online, -1 otherwise */
	public static int getNetworkConnection() {
		final ConnectivityManager cm = SystemUtils.getConnectivityManager();
		final NetworkInfo network = cm.getActiveNetworkInfo();
		if (network != null && network.isConnected()) {
			switch (network.getType()) {
			case ConnectivityManager.TYPE_WIFI:
				return ConnectivityManager.TYPE_WIFI;
			case ConnectivityManager.TYPE_MOBILE:
				return ConnectivityManager.TYPE_MOBILE;
			case ConnectivityManager.TYPE_WIMAX:
				return ConnectivityManager.TYPE_WIMAX;
			}
		}

		return NO_CONNECTION;
	}
}
