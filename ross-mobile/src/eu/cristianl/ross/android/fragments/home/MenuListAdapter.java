package eu.cristianl.ross.android.fragments.home;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.adapters.ListAdapter;

public class MenuListAdapter extends ListAdapter<MenuListAdapter.MenuItem> {

	private final Context mContext;

	MenuListAdapter(Context context) {
		this(context, null);
	}

	public MenuListAdapter(Context context, List<MenuListAdapter.MenuItem> items) {
		super(items);
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_home_adapter, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		MenuItem item = getItem(position);

		holder.mImageView.setBackgroundResource(item.mImageId);
		holder.mTextView.setText(item.mTextId);

		return convertView;
	}

	@Override
	protected int internalCompare(MenuItem arg0, MenuItem arg1) {
		return 0;
	}

	@Override
	protected List<MenuItem> internalGetItems() {
		return null;
	}

	public abstract static class MenuItem {
		private int mTextId;
		private int mImageId;

		public MenuItem(int textId, int imageId) {
			mTextId = textId;
			mImageId = imageId;
		}

		public abstract void perform();
	}

	private static class ViewHolder {
		private ImageView mImageView;
		private TextView mTextView;

		private ViewHolder(View parent) {
			mImageView = (ImageView) parent.findViewById(R.id.fragment_home_adapter_icon);
			mTextView = (TextView) parent.findViewById(R.id.fragment_home_adapter_title);
		}
	}
}
