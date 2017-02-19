/**
 * 
 */
package com.cognizant.animalsearchapp.rest.model;

import java.util.Date;

/**
 * @author Anisha
 *
 */
public class AnimalAccessLog {

	private int requestId;

	/**
	 * @return the requestId
	 */
	public final int getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId
	 *            the requestId to set
	 */
	public final void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	private String name;

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	private Date accessTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnimalAccessLog [requestId=" + requestId + ", " + (name != null ? "name=" + name + ", " : "")
				+ (accessTime != null ? "accessTime=" + accessTime : "") + "]";
	}

	/**
	 * @return the accessTime
	 */
	public final Date getAccessTime() {
		return accessTime;
	}

	/**
	 * @param accessTime
	 *            the accessTime to set
	 */
	public final void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

}
