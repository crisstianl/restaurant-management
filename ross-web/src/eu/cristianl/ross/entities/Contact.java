package eu.cristianl.ross.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import eu.cristianl.ross.entities.support.PriorityType;
import eu.cristianl.ross.entities.tables.ContactTable;
import eu.cristianl.ross.entities.views.ContactTagView;

@Entity
@Table(name = ContactTable.TABLE_NAME)
public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = ContactTable.ID)
	private Integer mId;

	@Column(name = ContactTable.NAME, nullable = false, length = 50)
	private String mName;

	@Column(name = ContactTable.PRIORITY)
	@Enumerated(EnumType.STRING)
	private PriorityType mPriority = PriorityType.MEDIUM;

	@Column(name = ContactTable.CONV_FACTOR)
	private int mConvFactor = 1;

	@JoinTable(name = ContactTagView.VIEW_NAME, joinColumns = @JoinColumn(name = ContactTagView.CONTACT_ID), inverseJoinColumns = @JoinColumn(name = ContactTagView.TAG_ID))
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Tag> mTags = new ArrayList<Tag>();

	public Contact() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Contact) {
			Contact another = (Contact) obj;
			return mId.equals(another.getId());
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%1$d-%2$s", mId, mName);
	}

	public Integer getId() {
		return mId;
	}

	public void setId(Integer id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public PriorityType getPriority() {
		return mPriority;
	}

	public void setPriority(PriorityType priority) {
		mPriority = priority;
	}

	public int getConvFactor() {
		return mConvFactor;
	}

	public void setConvFactor(int convFactor) {
		mConvFactor = convFactor;
	}

	public List<Tag> getTags() {
		return mTags;
	}

	public void setTags(List<Tag> tags) {
		mTags = tags;
	}
}
