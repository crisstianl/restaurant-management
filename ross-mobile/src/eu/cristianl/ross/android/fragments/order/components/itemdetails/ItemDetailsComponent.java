package eu.cristianl.ross.android.fragments.order.components.itemdetails;

import android.view.View;
import android.widget.TextView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.fragments.order.OrderFragment;
import eu.cristianl.ross.android.fragments.order.components.OrderComponent;
import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.android.widgets.NumberPicker;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.dal.OrderRowDal;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.utils.Utils;

public class ItemDetailsComponent extends OrderComponent {
	public static final String TAG = "ItemDetailsComponent";

	public ItemDetailsComponent(OrderFragment parentFragment) {
		super(TAG, parentFragment);
	}

	@Override
	public View createTabContent(String tag) {
		View retView = inflateLayout(R.layout.fragment_order_item_details_component);

		ViewHolder.mCodeView = (TextView) retView.findViewById(R.id.fragment_order_item_details_code);

		ViewHolder.mTitleView = (TextView) retView.findViewById(R.id.fragment_order_item_details_title);

		ViewHolder.mDescriptionView = (TextView) retView.findViewById(R.id.fragment_order_item_details_description);

		ViewHolder.mCategoryView = (TextView) retView.findViewById(R.id.fragment_order_item_details_category);

		ViewHolder.mPriceView = (TextView) retView.findViewById(R.id.fragment_order_item_details_price);

		ViewHolder.mDiscountView = (TextView) retView.findViewById(R.id.fragment_order_item_details_discount);

		ViewHolder.mTotalView = (TextView) retView.findViewById(R.id.fragment_order_item_details_total);

		setupQuantityPicker(retView);

		return retView;
	}

	@Override
	public void onComponentSelected(Object arg) {
		if (mParentFragment.getCurrentRow() != null) {
			refreshViews(mParentFragment.getCurrentRow());
		} else {
			resetViews();
		}
	}

	@Override
	public String getTitle() {
		return Resources.getString(R.string.fragment_order_item_details_tab);
	}

	private void refreshViews(OrderRow orderRow) {
		ViewHolder.mCodeView.setText(String.valueOf(orderRow.getItem().getId()));

		ViewHolder.mTitleView.setText(String.valueOf(orderRow.getItem().getTitle()));

		ViewHolder.mDescriptionView.setText(orderRow.getItem().getDescription());

		ViewHolder.mCategoryView.setText(orderRow.getItem().getCategory().getDescription());

		ViewHolder.mPriceView
				.setText(Utils.formatCurrency(orderRow.getItem().getPrice(), orderRow.getCurrency().getName()));

		ViewHolder.mDiscountView.setText(Utils.format(orderRow.getDiscount()));

		ViewHolder.mTotalView.setText(Utils.formatCurrency(orderRow.getTotal(), orderRow.getCurrency().getName()));

		ViewHolder.mQuantityPicker.setValue(orderRow.getQuantity());
	}

	private void refreshTotalView(OrderRow orderRow) {
		ViewHolder.mTotalView.setText(Utils.formatCurrency(orderRow.getTotal(), orderRow.getCurrency().getName()));
	}

	private void resetViews() {
		ViewHolder.mCodeView.setText(R.string.dictionary_not_available);
		ViewHolder.mTitleView.setText(R.string.dictionary_not_available);
		ViewHolder.mDescriptionView.setText(R.string.dictionary_not_available);
		ViewHolder.mCategoryView.setText(R.string.dictionary_not_available);
		ViewHolder.mPriceView.setText(R.string.dictionary_not_available);
		ViewHolder.mDiscountView.setText(R.string.dictionary_not_available);
		ViewHolder.mTotalView.setText(R.string.dictionary_not_available);
		ViewHolder.mQuantityPicker.setValue(0);
	}

	private void setupQuantityPicker(View parent) {
		ViewHolder.mQuantityPicker = (NumberPicker) parent.findViewById(R.id.fragment_order_item_details_quantity);
		ViewHolder.mQuantityPicker.requestFocus();

		ViewHolder.mQuantityPicker.setOnValueChangeListener(new NumberPicker.ValueChangeListener() {

			@Override
			public void onValueChanged(float value) {
				final OrderRow currentRow = mParentFragment.getCurrentRow();
				if (value > 0.0F && currentRow != null) {
					OrderRowDal.getDal().calculateTotal(currentRow, value);
					OrderDal.getDal().calculateTotal(currentRow.getOrder());
					refreshTotalView(currentRow);
				}
			}

			@Override
			public boolean isValidValue(float value) {
				// Do not let user to delete row
				return value > 0;
			}
		});
	}

	private static class ViewHolder {
		private static TextView mCodeView;
		private static TextView mTitleView;
		private static TextView mDescriptionView;
		private static TextView mCategoryView;
		private static TextView mPriceView;
		private static TextView mDiscountView;
		private static TextView mTotalView;
		private static NumberPicker mQuantityPicker;
	}
}
