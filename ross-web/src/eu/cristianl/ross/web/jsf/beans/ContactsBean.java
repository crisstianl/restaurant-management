package eu.cristianl.ross.web.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import eu.cristianl.ross.dal.ContactDal;
import eu.cristianl.ross.entities.Contact;
import eu.cristianl.ross.entities.support.PriorityType;
import eu.cristianl.ross.utils.Utils;
import eu.cristianl.ross.web.jsf.utils.FacesMessages;

@ManagedBean(name = ContactsBean.NAME)
@ViewScoped
public class ContactsBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "ContactsBean";

	// data
	private long mCount;
	private FormData mSelection = null;
	private List<FormData> mContacts = new ArrayList<FormData>();

	public ContactsBean() {
	}

	@PostConstruct
	public void init() {
		mCount = ContactDal.I().totalContacts();
		queryForContacts();
	}

	@PreDestroy
	public void destroy() {
		mSelection = null;
		mContacts.clear();
		mContacts = null;
	}

	public List<FormData> getContacts() {
		return mContacts;
	}

	public void setSelection(FormData selection) {
		this.mSelection = selection;
	}

	public FormData getSelection() {
		return this.mSelection;
	}

	public void saveContact(ActionEvent event) {
		if (mSelection != null) {
			ContactDal.I().update(mSelection.mContact);
			FacesMessages.info(null, "Success", "Contact saved !");
		}
	}

	public PriorityType[] getPriorities() {
		return PriorityType.values();
	}

	private void queryForContacts() {
		final List<Contact> results = ContactDal.I().getAll();
		if (!Utils.isEmptyOrNull(results)) {
			for (Contact contact : results) {
				mContacts.add(new FormData(contact));
			}
			mSelection = mContacts.get(0);
		}
	}

	public static class FormData implements Serializable {
		private static final long serialVersionUID = 1L;

		private Contact mContact;

		public FormData(Contact contact) {
			mContact = contact;
		}

		public String getId() {
			return mContact.getId().toString();
		}

		public String getName() {
			return mContact.getName();
		}

		public void setName(String newName) {
			if (newName != null && !newName.isEmpty()) {
				mContact.setName(newName);
			}
		}

		public String getPriority() {
			return mContact.getPriority().toString();
		}

		public String getPriorityId() {
			return mContact.getPriority().getCode();
		}

		public void setPriorityId(String newPriority) {
			if (newPriority != null && !newPriority.isEmpty()) {
				mContact.setPriority(PriorityType.getByCode(newPriority));
			}
		}

		public String getParty() {
			return String.valueOf(mContact.getConvFactor());
		}

		public void setParty(String newParty) {
			if (newParty != null && !newParty.isEmpty()) {
				mContact.setConvFactor(Integer.parseInt(newParty));
			}
		}
	}
}
