package eu.cristianl.ross.entities.support;

public enum DocStatusType implements PersistableCodeEnum {
	NEW("N"), PENDING("P"), SUBMITTED("S"), READY("R"), CLOSED("C");

	private final String mCode;

	private DocStatusType(String code) {
		mCode = code;
	}

	@Override
	public String getCode() {
		return mCode;
	}

	@Override
	public String toString() {
		return mCode;
	}

	public static DocStatusType getByCode(String code) {
		for (DocStatusType value : DocStatusType.values()) {
			if (value.getCode().equals(code)) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid doc status type " + code);
	}
}
