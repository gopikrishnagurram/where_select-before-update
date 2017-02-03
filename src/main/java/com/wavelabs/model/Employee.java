package com.wavelabs.model;

/**
 * <h1>Entity class to represent Employee table</h1>
 * <p>
 * id, name, sal are Properties of Entity. Provides setters and getters to all
 * methods.
 * <p>
 * 
 * @author gopikrishnag
 * @since 2017-02-02
 */
public class Employee {

	private int id;
	private String name;
	private double sal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

}
