package com.liferay.test.portlet.web.model;

public class Entry {
	
	private String name;
	
	private String message;

	public Entry(String name, String message) {
		this.name = name;
		this.message = message;
	}
	
	public Entry() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
