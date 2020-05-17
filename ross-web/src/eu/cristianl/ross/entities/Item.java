package eu.cristianl.ross.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import eu.cristianl.ross.entities.tables.ItemTable;
import eu.cristianl.ross.entities.views.ItemTagView;

@Entity
@Table(name = ItemTable.TABLE_NAME)
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = ItemTable.ID)
	private Integer mId;

	@Column(name = ItemTable.TITLE, nullable = false, length = 50)
	private String mTitle;

	@Column(name = ItemTable.DESCRIPTION, nullable = false, columnDefinition = "varchar(255)")
	private String mDescription;

	@OneToOne(cascade = { CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinColumn(name = ItemTable.CATEGORY_ID, nullable = false)
	private Category mCategory;

	@Column(name = ItemTable.PRICE, nullable = false)
	private float mPrice;

	@Column(name = ItemTable.DISCOUNT)
	private float mDiscount;

	@OneToOne
	@JoinColumn(name = ItemTable.UNIT_ID)
	private Unit mUnit;

	@OneToOne
	@JoinColumn(name = ItemTable.CURRENCY_ID, nullable = false)
	private BaseCurrency mCurrency;

	@JoinTable(name = ItemTagView.VIEW_NAME, joinColumns = @JoinColumn(name = ItemTagView.ITEM_ID), inverseJoinColumns = @JoinColumn(name = ItemTagView.TAG_ID))
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Tag> mTags;

	public Item() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			Item another = (Item) obj;
			return mId.equals(another.getId());
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%1$d-%2$s", mId, mTitle);
	}

	public Integer getId() {
		return mId;
	}

	public void setId(Integer id) {
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

	public Category getCategory() {
		return mCategory;
	}

	public void setCategory(Category category) {
		mCategory = category;
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

	public Unit getUnit() {
		return mUnit;
	}

	public void setUnit(Unit unit) {
		mUnit = unit;
	}

	public List<Tag> getTags() {
		return mTags;
	}

	public void setTags(List<Tag> tags) {
		mTags = tags;
	}

	public BaseCurrency getCurrency() {
		return mCurrency;
	}

	public void setCurrency(BaseCurrency currency) {
		mCurrency = currency;
	}
}
