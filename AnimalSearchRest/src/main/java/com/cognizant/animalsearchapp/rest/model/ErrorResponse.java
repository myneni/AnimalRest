/**
 * 
 */
package com.cognizant.animalsearchapp.rest.model;

/**
 * @author Anisha
 *
 */
public class ErrorResponse {
	public ErrorResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ErrorResponse [" + (code != null ? "code=" + code + ", " : "")
				+ (message != null ? "message=" + message : "") + "]";
	}

	private String code;

	/**
	 * @return the code
	 */
	public final String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public final void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public final void setMessage(String message) {
		this.message = message;
	}

	private String message;

}
