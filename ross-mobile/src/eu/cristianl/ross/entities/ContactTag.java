package eu.cristianl.ross.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.tables.ContactTable;
import eu.cristianl.ross.entities.tables.ContactTagTable;
import eu.cristianl.ross.entities.tables.TagTable;

@DatabaseTable(tableName = ContactTagTable.TABLE_NAME)
public class ContactTag {

	@DatabaseField(columnName = ContactTagTable.CONTACT_ID, foreignColumnName = ContactTable.ID, foreign = true)
	private Contact mContact;

	@DatabaseField(columnName = ContactTagTable.TAG_ID, foreignColumnName = TagTable.ID, foreign = true)
	private Tag mTag;

	public ContactTag() {
	}

	public Contact getContact() {
		return mContact;
	}

	public void setContact(Contact contact) {
		mContact = contact;
	}

	public Tag getTag() {
		return mTag;
	}

	public void setTag(Tag tag) {
		mTag = tag;
	}

}
