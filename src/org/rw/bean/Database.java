package org.rw.bean;

/**
 * Simple Database Bean object
 * @author Austin Delamar
 * @date 6/2/2016
 */
public class Database {
	
	private String name;
	private double size;
	
	public Database(String name, double size) {
		this.setName(name);
		this.setSize(size);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}
	
	
}