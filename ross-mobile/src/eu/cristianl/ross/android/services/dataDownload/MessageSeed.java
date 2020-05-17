package eu.cristianl.ross.android.services.dataDownload;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageSeed implements Parcelable {
	public static final int TYPE_PROGRESS = 1;
	public static final int TYPE_END = 2;

	private int mType;
	private int mProgress;
	private String mText;

	public MessageSeed(int type, int progress, String text) {
		mType = type;
		mProgress = progress;
		mText = text;
	}

	public MessageSeed(Parcel in) {
		mType = in.readInt();
		mProgress = in.readInt();
		mText = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mType);
		dest.writeInt(mProgress);
		dest.writeString(mText);
	}

	public static final Parcelable.Creator<MessageSeed> CREATOR = new Parcelable.Creator<MessageSeed>() {

		@Override
		public MessageSeed createFromParcel(Parcel source) {
			return new MessageSeed(source);
		}

		@Override
		public MessageSeed[] newArray(int size) {
			return new MessageSeed[size];
		}
	};

	public int getType() {
		return mType;
	}

	public int getProgress() {
		return mProgress;
	}

	public String getText() {
		return mText;
	}

}
