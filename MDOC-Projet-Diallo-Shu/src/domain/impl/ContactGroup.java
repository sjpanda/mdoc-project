package domain.impl;

import java.util.HashSet;
import java.util.Set;

import domain.IContact;
import domain.IContactGroup;

public class ContactGroup implements IContactGroup {
	private long groupId;
	private int version;
	private String groupName;
	private Set<IContact> contacts;

	public ContactGroup() {
		this.contacts = new HashSet<IContact>();
	}
	
	public ContactGroup(long groupId, String groupName, Set<IContact> contacts) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.contacts = contacts;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public Set<IContact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<IContact> contacts) {
		this.contacts = contacts;
	}	
}
