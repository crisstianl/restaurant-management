package eu.cristianl.ross.web.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.BehaviorEvent;

import eu.cristianl.ross.dal.CategoryDal;
import eu.cristianl.ross.dal.ItemDal;
import eu.cristianl.ross.entities.Category;
import eu.cristianl.ross.entities.Item;
import eu.cristianl.ross.utils.Utils;
import eu.cristianl.ross.web.jsf.utils.FacesMessages;

@ManagedBean(name = ItemsBean.NAME)
@ViewScoped
public class ItemsBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "ItemsBean";

	// data
	private long mCount = 0;
	private FormData mSelection = null;
	private List<FormData> mItems = new ArrayList<FormData>();

	// filters
	private String mCategoryId = null;
	private List<Category> mCategories = new ArrayList<Category>();

	public ItemsBean() {
	}

	@PostConstruct
	public void init() {
		queryForCategories();
		mCount = ItemDal.I().totalItems();
		queryForItems();
	}

	@PreDestroy
	public void destroy() {
		mSelection = null;
		mItems.clear();
		mItems = null;
	}

	public List<FormData> getItems() {
		return mItems;
	}

	public void setSelection(FormData selection) {
		this.mSelection = selection;
	}

	public FormData getSelection() {
		return this.mSelection;
	}

	public void saveItem(ActionEvent event) {
		if (mSelection != null) {
			ItemDal.I().update(mSelection.mItem);
			FacesMessages.info(null, "Success", "Item saved !");
		}
	}

	public void onCategorySelected(BehaviorEvent event) {
		queryForItems();
	}

	public String getCategoryId() {
		return mCategoryId;
	}

	public void setCategoryId(String newValue) {
		mCategoryId = newValue;
	}

	public List<Category> getCategories() {
		return mCategories;
	}

	private void queryForItems() {
		final List<Item> results = ItemDal.I().getItems(mCategoryId, (int) mCount);
		if (!Utils.isEmptyOrNull(results)) {
			mItems.clear();
			for (Item item : results) {
				mItems.add(new FormData(item, mCategories));
			}
			mSelection = mItems.get(0);
		}
	}

	private void queryForCategories() {
		mCategories = CategoryDal.I().getAll();
	}

	public static class FormData implements Serializable {
		private static final long serialVersionUID = 1L;

		private final Item mItem;
		private final List<Category> mCategories;

		public FormData(Item item, List<Category> categories) {
			mItem = item;
			mCategories = categories;
		}

		public String getId() {
			return mItem.getId().toString();
		}

		public String getTitle() {
			return mItem.getTitle();
		}

		public void setTitle(String newTitle) {
			if (newTitle != null && !newTitle.isEmpty()) {
				mItem.setTitle(newTitle);
			}
		}

		public String getDescription() {
			return mItem.getDescription();
		}

		public void setDescription(String newValue) {
			if (newValue != null && !newValue.isEmpty()) {
				mItem.setDescription(newValue);
			}
		}

		public String getCategory() {
			return mItem.getCategory().getDescription();
		}

		public String getCategoryId() {
			return mItem.getCategory().getId();
		}

		public void setCategoryId(String newValue) {
			if (newValue != null && !newValue.isEmpty()) {
				for (Category category : mCategories) {
					if (newValue.equals(category.getId())) {
						mItem.setCategory(category);
						break;
					}
				}
			}
		}

		public String getPrice() {
			return String.format("%1$.2f", mItem.getPrice());
		}

		public void setPrice(String newValue) {
			if (newValue != null && !newValue.isEmpty()) {
				mItem.setPrice(Utils.tryReadFloat(newValue, mItem.getPrice()));
			}
		}

		public String getDiscount() {
			return String.format("%1$.2f", mItem.getDiscount());
		}

		public void setDiscount(String newValue) {
			if (newValue != null && !newValue.isEmpty()) {
				mItem.setDiscount(Utils.tryReadFloat(newValue, mItem.getDiscount()));
			}
		}

		public String getCurrency() {
			return mItem.getCurrency().getName();
		}
	}
}
