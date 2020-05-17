package eu.cristianl.ross.entities.support;

public enum PermissionType {
	READ(0), WRITE(1), DELETE(2);

	private final int mCode;

	private PermissionType(int code) {
		mCode = code;
	}

	public int getCode() {
		return mCode;
	}

	public static PermissionType getByCode(int code) {
		for (PermissionType item : PermissionType.values()) {
			if (code == item.getCode()) {
				return item;
			}
		}
		throw new IllegalArgumentException("Invalid permission type " + code);
	}
}
