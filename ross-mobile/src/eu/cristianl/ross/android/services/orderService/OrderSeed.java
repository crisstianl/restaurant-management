package eu.cristianl.ross.android.services.orderService;

import java.util.Collection;
import java.util.LinkedList;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderSeed implements Parcelable {

	private LinkedList<String> mOrderIds = new LinkedList<String>();

	private String mDocStatus = null;

	public OrderSeed(String orderId) {
		mOrderIds.add(orderId);
	}

	public OrderSeed(Collection<String> orderIds, String docStatus) {
		mOrderIds.addAll(orderIds);
		mDocStatus = docStatus;
	}

	public OrderSeed(Parcel in) {
		final int size = in.readInt();
		for (int i = 0; i < size; i++) {
			mOrderIds.add(in.readString());
		}

		mDocStatus = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mOrderIds.size());
		for (String orderId : mOrderIds) {
			dest.writeString(orderId);
		}

		if (mDocStatus != null) {
			dest.writeString(mDocStatus);
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public LinkedList<String> getOrderIds() {
		return mOrderIds;
	}

	public String[] getOrderIdArray() {
		return mOrderIds.toArray(new String[mOrderIds.size()]);
	}

	public String getDocStatus() {
		return mDocStatus;
	}

	public static final Parcelable.Creator<OrderSeed> CREATOR = new Parcelable.Creator<OrderSeed>() {

		@Override
		public OrderSeed createFromParcel(Parcel source) {
			return new OrderSeed(source);
		}

		@Override
		public OrderSeed[] newArray(int size) {
			return new OrderSeed[size];
		}
	};

}
