package org.rw.bean;

public class Author {
	
	private String uri_name;
	private String name;
	
	public Author(String uri_name, String name) {
		this.setUri_name(uri_name);
		this.setName(name);
	}

	public String getUri_name() {
		return uri_name;
	}

	public void setUri_name(String uri_name) {
		this.uri_name = uri_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
