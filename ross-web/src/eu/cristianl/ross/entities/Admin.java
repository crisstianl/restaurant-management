package eu.cristianl.ross.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import eu.cristianl.ross.entities.support.PermissionType;
import eu.cristianl.ross.entities.tables.AdminTable;

@Entity
@Table(name = AdminTable.TABLE_NAME)
public class Admin extends Employee {
	private static final long serialVersionUID = 1L;

	@Column(name = AdminTable.PERMISSIONS)
	@Enumerated(EnumType.ORDINAL)
	private PermissionType mPermissions;

	public Admin() {
	}

	public PermissionType getPermissions() {
		return mPermissions;
	}

	public void setPermissions(PermissionType permissions) {
		mPermissions = permissions;
	}
}
