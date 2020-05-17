package eu.cristianl.ross.entities.support;

public enum GenderType {
	MALE("M"), FEMALE("F");

	private final String mCode;

	private GenderType(String code) {
		mCode = code;
	}

	public String getCode() {
		return mCode;
	}

	public static GenderType getByCode(String code) {
		if (code != null) {
			for (GenderType item : GenderType.values()) {
				if (code.equals(item.getCode())) {
					return item;
				}
			}
		}
		throw new IllegalArgumentException("Invalid gender type " + code);
	}
}
