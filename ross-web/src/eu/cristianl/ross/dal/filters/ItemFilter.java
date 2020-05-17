package eu.cristianl.ross.dal.filters;

public class ItemFilter extends BaseFilter {

	private String categoryId;

	public ItemFilter(boolean and, int operator) {
		super(and, operator);
	}

	public ItemFilter(boolean and) {
		super(and);
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}
