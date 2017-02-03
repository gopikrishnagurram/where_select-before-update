package com.wavelabs.service;

import org.hibernate.internal.util.xml.XmlDocument;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wavelabs.metadata.ClassAttributes;
import com.wavelabs.metadata.HbmFileMetaData;
import com.wavelabs.metadata.XmlDocumentBuilder;
import com.wavelabs.model.Employee;
import com.wavelabs.service.PersistenceService;
import com.wavelabs.tableoperations.CRUDTest;
import com.wavelabs.utility.Helper;

/**
 * Performs unit test cases on {@link PersistenceService} methods.
 * 
 * @author gopikrishnag
 * @since 2017-02-03
 */

public class PersistenceServiceTest {

	HbmFileMetaData employeeHbm = null;
	CRUDTest crud = null;

	/**
	 * <p>
	 * Initializes {@link HbmFileMetaData}, {@link CRUDTest} Class objects. This
	 * objects useful through out all unit test cases.
	 * </p>
	 * 
	 */
	@BeforeTest
	public void intillzation() {
		XmlDocumentBuilder xmb = new XmlDocumentBuilder();
		XmlDocument document = xmb.getXmlDocumentObject("src/main/resources/com/wavelabs/model/Employee.hbm.xml");
		employeeHbm = new HbmFileMetaData(document, Helper.getSessionFactory());
		crud = new CRUDTest(Helper.getSessionFactory(), Helper.getConfiguration(), Helper.getSession());
	}

	/**
	 * Provides the Car object values to the
	 * {@link #testCreateEmployee(int, String, double)} method.
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "records")
	public Object[][] dataBase() {
		Object[][] obj = { { 1, "gopi krishna", 20000 }, { 2, "raja shekhar", 17000 }, { 3, "tharun kumar", 18200 },
				{ 4, "murali krishna", 19900 } };
		return obj;
	}

	/**
	 * Checks insertion of records in table
	 * <ul>
	 * <li>Takes parameters values from {@link #dataBase()}</li>
	 * <li>Calls {@link PersistenceService#createEmployee(int, String, double)}
	 * method</li>
	 * <li>For successful records insertion test case will pass</li>
	 * </ul>
	 * 
	 * @param id
	 *            id of Employee
	 * @param name
	 *            name of Employee
	 * @param sal
	 *            sal of Employee
	 */
	@Test(dataProvider = "records", priority = 1,description = "checks CreateEmployee method in PersistenceService inserting record in table")
	public void testCreateEmployee(int id, String name, double sal) {
		PersistenceService.createEmployee(id, name, sal);
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.isRecordInserted(Employee.class, id), true);
	}

	/**
	 * Checks value of where attribute value in employee hbm file.
	 * <p>
	 * {@code where="sal > 18000"} As per given REQRUITMENT test expects sal
	 * &gt; 18000 condition.
	 * </p>
	 */
	@Test(priority = 2, description = "checks where attribute value in employee hbm file")
	public void testWhereMapping() {
		Assert.assertEquals(employeeHbm.getClassAttribute(ClassAttributes.where), "sal>18000");
	}

	/**
	 * Checks select-before-update attribute value in employee hbm file.
	 * 
	 * <pre>
	 * {@code select-before-update= "true"} test case will pass
	 * {@code select-before-update= "false"} test case will fail.
	 * if select-before-update is not configure {@link NullPointerException}
	 * </pre>
	 */
	@Test(priority = 3, description = "checks select-before-update attribute value in employee hbm")
	public void testSelectBeforeUpdate() {
		Assert.assertEquals(employeeHbm.getClassAttribute(ClassAttributes.selectbeforeupdate), "true");
	}

	/**
	 * Checks Employee salary is updated or not for given employee id
	 * <ul>
	 * <li>{@code PersistanceService#updateEmployeeSalary(int, double)} update
	 * record in table</li>
	 * <li>Test case pass if Update of Employee record is successful</li>
	 * <li>Test case fail if update of Employee record is not successful.</li>
	 * </ul>
	 */
	@Test(priority = 4, description = "Checks updateEmployee method in PersistenceService updated or not given id")
	public void testForUpdateEmployeeSalary() {
		PersistenceService.updateEmployeeSalary(1, 18200);
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.getValue(Employee.class, 1, "sal"), 18200.0);
	}

	/**
	 * Closes SessionFactory of Helper class.
	 */
	@AfterTest(description = "closes session and SessionFactory associated with Helper class.")
	public void closeResources() {
		if (Helper.getSession().isOpen())
			Helper.getSession().close();
		if (!Helper.getSessionFactory().isClosed())
			Helper.getSessionFactory().close();
	}
}
