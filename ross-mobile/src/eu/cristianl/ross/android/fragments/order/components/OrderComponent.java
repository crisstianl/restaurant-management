package eu.cristianl.ross.android.fragments.order.components;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import eu.cristianl.ross.android.fragments.order.OrderFragment;
import eu.cristianl.ross.android.fragments.order.components.itemdetails.ItemDetailsComponent;
import eu.cristianl.ross.android.fragments.order.components.items.ItemsComponent;
import eu.cristianl.ross.android.fragments.order.components.sync.SyncComponent;

public abstract class OrderComponent implements TabHost.TabContentFactory {
	protected OrderFragment mParentFragment;

	private String mTag;

	public OrderComponent(String tag, OrderFragment parentFragment) {
		mTag = tag;
		mParentFragment = parentFragment;
	}

	protected View inflateLayout(int layoutId) {
		return LayoutInflater.from(mParentFragment.getActivity()).inflate(layoutId, null);
	}

	public String getTag() {
		return mTag;
	}

	public abstract void onComponentSelected(Object arg);

	public abstract String getTitle();

	public static class Factory {
		private OrderFragment mParentFragment;

		public Factory(OrderFragment parentFragment) {
			mParentFragment = parentFragment;
		}

		public void addComponent(TabHost tabhost, String tag) {
			OrderComponent component = getByTag(tag);
			if (component == null) {
				return;
			}

			TabHost.TabSpec tab = tabhost.newTabSpec(tag).setIndicator(component.getTitle()).setContent(component);
			tabhost.addTab(tab);
		}

		private OrderComponent getByTag(String tag) {
			if (tag.equals(ItemsComponent.TAG)) {
				return new ItemsComponent(mParentFragment);

			} else if (tag.equals(ItemDetailsComponent.TAG)) {
				return new ItemDetailsComponent(mParentFragment);

			} else if (tag.equals(SyncComponent.TAG)) {
				return new SyncComponent(mParentFragment);

			} else {
				return null;
			}

		}
	}
}
