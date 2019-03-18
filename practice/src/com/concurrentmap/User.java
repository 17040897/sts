package com.concurrentmap;

public class User {
	private int age;
	private String name;
	
	private double salary;
	
	private String address;

	public User() {

	}

	public User(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	

	public User(int age, String name, double salary, String address) {
		super();
		this.age = age;
		this.name = name;
		this.salary = salary;
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "User [age=" + age + ", name=" + name + "]";
	}
	
	

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return 17;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof User && ((User) obj).getName().equals(
				this.getName()));
	}

}
