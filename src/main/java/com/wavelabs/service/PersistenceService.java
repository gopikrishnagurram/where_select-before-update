package com.wavelabs.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wavelabs.model.Employee;
import com.wavelabs.utility.Helper;

/**
 * <h1>Performs CRUD operations on {@link Employee} class.</h1>
 * <p>
 * provides methods to Creation of Employee and Update of Employee.
 * </p>
 * <p>
 * Uses Session given by {@link Helper} class.
 * </p>
 * 
 * @author gopikrishnag
 * @since  2017-02-02
 */
public class PersistenceService {
	/**
	 * <h1>inserts Employee object in table</h1>
	 * <p>
	 * Creates Employee object, sets the values id, name, sal and inserts record
	 * into table
	 * </p>
	 * 
	 * @param id
	 *             of Employee
	 * @param name
	 *             of Employee
	 * @param sal
	 *             of Employee
	 */
	public static void createEmployee(int id, String name, double sal) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Employee e1 = new Employee();
		e1.setId(id);
		e1.setName(name);
		e1.setSal(sal);
		session.save(e1);
		tx.commit();
		session.close();
	}

	/**
	 * <h1>Update given Employee sal</h1>
	 * <ul>
	 * <li>Loads Record from table using id</li>
	 * <li>sets sal to new value</li>
	 * <li>Performs update operation</li>
	 * </ul>
	 * 
	 * @param id
	 *            id of employe
	 * @param sal
	 *            sal of Employe
	 */
	public static void updateEmployeeSalary(int id, double sal) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Employee e1 = (Employee) session.get(Employee.class, id);
		e1.setSal(sal);
		session.flush();
		tx.commit();
		session.close();
	}
}
