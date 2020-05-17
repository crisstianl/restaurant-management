package eu.cristianl.ross.dal.filters;

public class ItemFilter extends BaseFilter {
	private String[] mItemId;
	private String[] mItemTitle;
	private String[] mItemDesc;
	private String[] mCategoryId;

	public ItemFilter() {
		super();
	}

	public ItemFilter(boolean restrict, boolean alternative, boolean negated) {
		super(restrict, alternative, negated);
	}

	public ItemFilter(boolean restrict, boolean alternative) {
		super(restrict, alternative);
	}

	public ItemFilter(boolean restrict) {
		super(restrict);
	}

	public String[] getCategoryId() {
		return mCategoryId;
	}

	public void setCategoryId(String[] categoryId) {
		mCategoryId = categoryId;
	}

	public void setCategoryId(String categoryId) {
		setCategoryId(new String[] { categoryId });
	}

	public String[] getItemId() {
		return mItemId;
	}

	public void setItemId(String[] itemId) {
		mItemId = itemId;
	}

	public void setItemId(String itemId) {
		setItemId(new String[] { itemId });
	}

	public String[] getItemDesc() {
		return mItemDesc;
	}

	public void setItemDesc(String itemDesc) {
		setItemDesc(new String[] { itemDesc });
	}

	public void setItemDesc(String[] itemDesc) {
		mItemDesc = itemDesc;
	}

	public String[] getItemTitle() {
		return mItemTitle;
	}

	public void setItemTitle(String[] itemTitle) {
		mItemTitle = itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		setItemTitle(new String[] { itemTitle });
	}
}
