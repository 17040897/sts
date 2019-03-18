package com.chapter3;

public class MyConn implements Conn {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "MyConn [name=" + name + "]";
	}
	
	
}
