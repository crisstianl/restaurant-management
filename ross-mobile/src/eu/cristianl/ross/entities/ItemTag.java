package eu.cristianl.ross.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.tables.ItemTable;
import eu.cristianl.ross.entities.tables.ItemTagTable;
import eu.cristianl.ross.entities.tables.TagTable;

@DatabaseTable(tableName = ItemTagTable.TABLE_NAME)
public class ItemTag {

	@DatabaseField(columnName = ItemTagTable.ITEM_ID, foreignColumnName = ItemTable.ID, foreign = true)
	private Item mItem;

	@DatabaseField(columnName = ItemTagTable.TAG_ID, foreignColumnName = TagTable.ID, foreign = true)
	private Tag mTag;

	public ItemTag() {
	}

	public Item getItem() {
		return mItem;
	}

	public void setItem(Item item) {
		mItem = item;
	}

	public Tag getTag() {
		return mTag;
	}

	public void setTag(Tag tag) {
		mTag = tag;
	}

}
