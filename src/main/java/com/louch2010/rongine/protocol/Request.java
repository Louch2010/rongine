package com.louch2010.rongine.protocol;

import java.io.Serializable;
import java.util.Arrays;

public class Request implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String uri;
	private Object[] params;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", uri=" + uri + ", params="
				+ Arrays.toString(params) + "]";
	}

}
