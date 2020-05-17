package eu.cristianl.ross.exceptions;

public class AppException extends Exception {
	private static final long serialVersionUID = 1L;

	public AppException() {
		super();
	}

	public AppException(Throwable throwable, String detailMessage) {
		super(detailMessage, throwable);
	}

	public AppException(String detailMessage) {
		super(detailMessage);
	}

	public AppException(String message, Object... args) {
		super(String.format(message, args));
	}

	public AppException(Throwable throwable) {
		super(throwable);
	}

}
