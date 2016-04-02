package uk.tomgalvin.nxblue;


/**
 * Exception thrown due to an error in PC-robot communications.
 * 
 * @author Tom Galvin
 */
public class CommException extends RuntimeException {
	private static final long serialVersionUID = 1178825829943458306L;

	public CommException() {
		super();
	}

	public CommException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommException(String message) {
		super(message);
	}

	public CommException(Throwable cause) {
		super(cause);
	}
	
}
