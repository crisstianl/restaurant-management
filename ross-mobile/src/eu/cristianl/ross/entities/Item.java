package eu.cristianl.ross.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.tables.CurrencyTable;
import eu.cristianl.ross.entities.tables.CategoryTable;
import eu.cristianl.ross.entities.tables.ItemTable;
import eu.cristianl.ross.entities.tables.UnitTable;

@DatabaseTable(tableName = ItemTable.TABLE_NAME)
public class Item {

	@DatabaseField(columnName = ItemTable.ID, id = true)
	private int mId;

	@DatabaseField(columnName = ItemTable.TITLE, canBeNull = false, width = 50)
	private String mTitle;

	@DatabaseField(columnName = ItemTable.DESCRIPTION, columnDefinition = "LONGBLOB")
	private String mDescription;

	@DatabaseField(columnName = ItemTable.CATEGORY_ID, foreignColumnName = CategoryTable.ID, foreign = true)
	private Category mCategory;

	@DatabaseField(columnName = ItemTable.PRICE, dataType = DataType.FLOAT, canBeNull = false)
	private float mPrice;

	@DatabaseField(columnName = ItemTable.DISCOUNT, dataType = DataType.FLOAT)
	private float mDiscount;

	@DatabaseField(columnName = ItemTable.UNIT_ID, foreignColumnName = UnitTable.ID, foreign = true)
	private Unit mUnit;

	@DatabaseField(columnName = ItemTable.CURRENCY_ID, foreignColumnName = CurrencyTable.ID, foreign = true)
	private Currency mCurrency;

	public Item() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			return mId == ((Item) obj).getId();
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%1$d-%2$s", mId, mTitle);
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public float getPrice() {
		return mPrice;
	}

	public void setPrice(float price) {
		mPrice = price;
	}

	public float getDiscount() {
		return mDiscount;
	}

	public void setDiscount(float discount) {
		mDiscount = discount;
	}

	public Category getCategory() {
		return mCategory;
	}

	public void setCategory(Category category) {
		mCategory = category;
	}

	public Unit getUnit() {
		return mUnit;
	}

	public void setUnit(Unit unit) {
		mUnit = unit;
	}

	public Currency getCurrency() {
		return mCurrency;
	}

	public void setCurrency(Currency currency) {
		mCurrency = currency;
	}

}
