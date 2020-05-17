package eu.cristianl.ross.entities.support;

public enum PriorityType {
	LOW("L"), MEDIUM("M"), HIGH("H");

	private final String mCode;

	private PriorityType(String code) {
		mCode = code;
	}

	public String getCode() {
		return mCode;
	}

	public static PriorityType getByCode(String code) {
		if (code != null) {
			for (PriorityType item : PriorityType.values()) {
				if (code.equals(item.getCode())) {
					return item;
				}
			}
		}
		throw new IllegalArgumentException("Invalid priority type " + code);
	}

	public static PriorityType getByName(String name) {
		if (name != null) {
			for (PriorityType item : PriorityType.values()) {
				if (name.equalsIgnoreCase(item.name())) {
					return item;
				}
			}
		}
		return null;
	}
}
