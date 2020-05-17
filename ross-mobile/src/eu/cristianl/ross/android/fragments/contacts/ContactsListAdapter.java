package eu.cristianl.ross.android.fragments.contacts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.adapters.ListAdapter;
import eu.cristianl.ross.dal.ContactDal;
import eu.cristianl.ross.dal.ContactTagDal;
import eu.cristianl.ross.entities.Contact;
import eu.cristianl.ross.entities.ContactTag;
import eu.cristianl.ross.entities.Tag;
import eu.cristianl.ross.entities.support.PriorityType;
import eu.cristianl.ross.utils.Utils;

public class ContactsListAdapter extends ListAdapter<Contact> {
	private static final int SORT_BY_ID = 0;
	private static final int SORT_BY_NAME = 1;
	private static final int SORT_BY_PRIORITY = 2;
	private static final int SORT_BY_PARTY = 3;

	private static final int MAX_CONTACT_TAGS = 2;

	private final Context mContext;

	private String mFilterSearch = null;
	private Map<Contact, List<Tag>> mContactTags = new TreeMap<Contact, List<Tag>>(new ContactsComparator());

	ContactsListAdapter(Context context) {
		this.mContext = context;
		reset();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.fragment_contacts_adapter, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		Contact contact = getItem(position);

		fillViewFolderWithData(holder, contact);
		fillViewFolderExtrasWithData(holder, contact);

		return convertView;
	}

	@Override
	public int internalCompare(Contact arg0, Contact arg1) {
		switch (getSortingType()) {
		case SORT_BY_ID:
			return arg0.getId() - arg1.getId();
		case SORT_BY_PRIORITY:
			return arg0.getPriority().compareTo(arg1.getPriority());
		case SORT_BY_PARTY:
			return arg0.getConvFactor() - arg1.getConvFactor();
		case SORT_BY_NAME:
		default:
			return arg0.getName().compareTo(arg1.getName());
		}
	}

	@Override
	protected List<Contact> internalGetItems() {
		// query contact tags
		queryForTags();

		// query contacts
		switch (getSortingType()) {
		case SORT_BY_ID:
			return ContactDal.getDal().getContactsOrderedById(mFilterSearch, 100);
		case SORT_BY_PRIORITY:
			return ContactDal.getDal().getContactsOrderedByPriority(mFilterSearch, 100);
		case SORT_BY_PARTY:
			return ContactDal.getDal().getContactsOrderedByParty(mFilterSearch, 100);
		case SORT_BY_NAME:
		default:
			return ContactDal.getDal().getContactsOrderedByName(mFilterSearch, 100);
		}
	}

	@Override
	public void reset() {
		mContactTags.clear();
		super.reset();
	}

	public void setSearchFilter(String filter) {
		mFilterSearch = filter;
	}

	private void fillViewFolderWithData(ViewHolder holder, Contact contact) {
		holder.mIconView.setBackgroundResource(getIconId(contact.getPriority()));
		holder.mIdView.setText(String.valueOf(contact.getId()));
		holder.mNameView.setText(contact.getName());
		holder.mPriorityView.setText(contact.getPriority().getCode());
		holder.mPartyView.setText(String.valueOf(contact.getConvFactor()));
	}

	private void fillViewFolderExtrasWithData(ViewHolder holder, Contact contact) {
		holder.mTagsContainer.removeAllViews();

		List<Tag> tags = mContactTags.get(contact);
		if (!Utils.isEmptyOrNull(tags)) {
			for (int i = 0; i < MAX_CONTACT_TAGS && i < tags.size(); i++) {
				fillTag(holder.mTagsContainer, tags.get(i));
			}
		}
	}

	private void fillTag(LinearLayout parent, Tag tag) {
		TextView tagView = new TextView(parent.getContext());

		tagView.setText(tag.getDescription());
		tagView.setTextColor((int) tag.getForeground());
		tagView.setBackgroundColor((int) tag.getBackground());
		tagView.setLines(1);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(90, LayoutParams.WRAP_CONTENT);
		params.setMargins(1, 1, 5, 1);
		parent.addView(tagView, params);
	}

	private void queryForTags() {
		final List<ContactTag> results = ContactTagDal.getDal().getAll();
		if (Utils.isEmptyOrNull(results)) {
			return;
		}

		for (ContactTag contactTag : results) {
			List<Tag> tags = mContactTags.get(contactTag.getContact());

			if (tags == null) {
				tags = new ArrayList<Tag>();
				mContactTags.put(contactTag.getContact(), tags);
			}

			tags.add(contactTag.getTag());
		}
	}

	private int getIconId(PriorityType priority) {
		switch (priority) {
		case HIGH:
			return R.drawable.ic_action_person_very_important;
		case MEDIUM:
			return R.drawable.ic_action_person_important;
		case LOW:
			return R.drawable.ic_action_person_normal;
		default:
			return R.drawable.ic_action_person_normal;
		}
	}

	private static class ContactsComparator implements Comparator<Contact> {

		@Override
		public int compare(Contact arg0, Contact arg1) {
			return arg0.getId() - arg1.getId();
		}

	}

	private static class ViewHolder {
		private ImageView mIconView;
		private TextView mIdView;
		private TextView mNameView;
		private TextView mPartyView;
		private TextView mPriorityView;
		private LinearLayout mTagsContainer;

		private ViewHolder(View parent) {
			mIconView = (ImageView) parent.findViewById(R.id.fragment_contacts_adapter_contact_icon);
			mIdView = (TextView) parent.findViewById(R.id.fragment_contacts_adapter_contact_id);
			mNameView = (TextView) parent.findViewById(R.id.fragment_contacts_adapter_contact_name);
			mPriorityView = (TextView) parent.findViewById(R.id.fragment_contacts_adapter_contact_priority);
			mPartyView = (TextView) parent.findViewById(R.id.fragment_contacts_adapter_contact_party);
			mTagsContainer = (LinearLayout) parent.findViewById(R.id.fragment_contacts_adapter_tags_container);
		}
	}

}
