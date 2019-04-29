package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Employee;
import model.Location;

public class EmployeeDao {
	/*
	 * This class handles all the database operations related to the employee table
	 */

    public Employee getDummyEmployee()
    {
        Employee employee = new Employee();

        Location location = new Location();
        location.setCity("Stony Brook");
        location.setState("NY");
        location.setZipCode(11790);

		/*Sample data begins*/
        employee.setEmail("shiyong@cs.sunysb.edu");
        employee.setFirstName("Shiyong");
        employee.setLastName("Lu");
        employee.setLocation(location);
        employee.setAddress("123 Success Street");
        employee.setStartDate("2006-10-17");
        employee.setTelephone("5166328959");
        employee.setEmployeeID("631-413-5555");
        employee.setHourlyRate(100);
		/*Sample data ends*/

        return employee;
    }

    public List<Employee> getDummyEmployees()
    {
       List<Employee> employees = new ArrayList<Employee>();

        for(int i = 0; i < 10; i++)
        {
            employees.add(getDummyEmployee());
        }

        return employees;
    }

	public String addEmployee(Employee employee) {

		/*
		 * All the values of the add employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the employee details and return "success" or "failure" based on result of the database insertion.
		 */
		
		/*Sample data begins*/
		if(employee == null){
			return "failure";
		}
		int zip = employee.getLocation().getZipCode();
		String city = employee.getLocation().getCity();
		String state = employee.getLocation().getState();
		String email = employee.getEmail();
		String startDate = employee.getStartDate();
		float hourlyRate = employee.getHourlyRate();
		int empId = Integer.parseInt(employee.getEmployeeID().replace("-", ""));
		String level = employee.getLevel();
		int SSN = Integer.parseInt(employee.getSsn().replace("-", ""));
		String lastName = employee.getLastName();
		String firstName = employee.getFirstName();
		String address = employee.getAddress();
		String tel = employee.getTelephone();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
			Statement stmt = con.createStatement();
			String sql = "insert into snisonoff.Location values (?, ?, ?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, zip);
			pst.setString(2, city);
			pst.setString(3, state);
			pst.executeUpdate();

			sql = "insert into snisonoff.Person values (?, ?, ?, ?, ?, ?)";
			pst = con.prepareStatement(sql);
			pst.setInt(1, SSN);
			pst.setString(2, lastName);
			pst.setString(3, firstName);
			pst.setString(4, address);
			pst.setInt(5, zip);
			pst.setString(6, tel);
			pst.executeUpdate();

			sql = "insert into snisonoff.Employee values (?, ?, ?, ?, ?)";
			pst = con.prepareStatement(sql);
			pst.setInt(1, empId);
			pst.setInt(2, SSN);
			pst.setDate(3, Date.valueOf(startDate));
			pst.setFloat(4, hourlyRate);
			pst.setString(5, level);
			pst.executeUpdate();

			con.close();
			return "success";
		}
		catch(SQLIntegrityConstraintViolationException j){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
				Statement stmt = con.createStatement();
				String sql = "insert into snisonoff.Person values (?, ?, ?, ?, ?, ?)";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setInt(1, SSN);
				pst.setString(2, lastName);
				pst.setString(3, firstName);
				pst.setString(4, address);
				pst.setInt(5, zip);
				pst.setString(6, tel);
				pst.executeUpdate();

				sql = "insert into snisonoff.Employee values (?, ?, ?, ?, ?)";
				pst = con.prepareStatement(sql);
				pst.setInt(1, empId);
				pst.setInt(2, SSN);
				pst.setDate(3, Date.valueOf(startDate));
				pst.setFloat(4, hourlyRate);
				pst.setString(5, level);
				pst.executeUpdate();

				con.close();
				return "success";
			}
			catch(Exception s){
				System.out.println(s);
				return "failure";
			}
		}
		catch(Exception e){
			System.out.println(e);
			return "failure";
		}
		/*Sample data ends*/

	}

	public String editEmployee(Employee employee) {
		/*
		 * All the values of the edit employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		/*Sample data begins*/
		if(employee == null){
			return "failure";
		}
		int zip = employee.getLocation().getZipCode();
		String city = employee.getLocation().getCity();
		String state = employee.getLocation().getState();
		String email = employee.getEmail();
		String startDate = employee.getStartDate();
		float hourlyRate = employee.getHourlyRate();
		int empId = Integer.parseInt(employee.getEmployeeID().replace("-", ""));
		String level = employee.getLevel();
		int SSN = Integer.parseInt(employee.getSsn().replace("-", ""));
		String lastName = employee.getLastName();
		String firstName = employee.getFirstName();
		String address = employee.getAddress();
		String tel = employee.getTelephone();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
			Statement stmt = con.createStatement();
			String sql = "update snisonoff.Location set City = ?, State = ? where Zipcode = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, city);
			pst.setString(2, state);
			pst.setInt(3, zip);
			pst.executeUpdate();

			sql = "update snisonoff.Person set LastName= ?, firstName = ?, Address = ?, Zipcode = ?, Telephone = ? where SSN = ?";
			pst = con.prepareStatement(sql);
			pst.setString(1, lastName);
			pst.setString(2, firstName);
			pst.setString(3, address);
			pst.setInt(4, zip);
			pst.setString(5, tel);
			pst.setInt(6, SSN);
			pst.executeUpdate();

			sql = "update snisonoff.Employee set StartDate = ?, HourlyRate = ?, Position = ? where SSN = ?";
			pst = con.prepareStatement(sql);
			pst.setDate(1, Date.valueOf(startDate));
			pst.setFloat(2, hourlyRate);
			pst.setString(3, level);
			pst.setInt(4, SSN);
			pst.executeUpdate();

			con.close();
			return "success";
		}
		catch(SQLIntegrityConstraintViolationException j){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
				Statement stmt = con.createStatement();
				String sql = "update snisonoff.Person set LastName= ?, firstName = ?, Address = ?, Zipcode = ?, Telephone = ? where SSN = ?";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, lastName);
				pst.setString(2, firstName);
				pst.setString(3, address);
				pst.setInt(4, zip);
				pst.setString(5, tel);
				pst.setInt(6, SSN);
				pst.executeUpdate();

				sql = "update snisonoff.Employee set StartDate = ?, HourlyRate = ?, Position = ? where SSN = ?";
				pst = con.prepareStatement(sql);
				pst.setDate(1, Date.valueOf(startDate));
				pst.setFloat(2, hourlyRate);
				pst.setString(3, level);
				pst.setInt(4, SSN);
				pst.executeUpdate();

				con.close();
				return "success";
			}
			catch(Exception s){
				System.out.println(s);
				return "failure";
			}
		}
		catch(Exception e){
			System.out.println(e);
			return "failure";
		}
		/*Sample data ends*/

	}

	public String deleteEmployee(String employeeID) {
		/*
		 * employeeID, which is the Employee's ID which has to be deleted, is given as method parameter
		 * The sample code returns "success" by default.
		 * You need to handle the database deletion and return "success" or "failure" based on result of the database deletion.
		 */
		
		/*Sample data begins*/
		Employee c = getEmployee(employeeID);
		if (c == null){
			return "failure";
		}
		else{
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
				Statement stmt = con.createStatement();
				stmt.execute("delete from snisonoff.Employee where Id=" + employeeID.replace("-", ""));
				stmt.execute("delete from snisonoff.Person where Id=" + employeeID.replace("-", ""));
				con.close();
				return "success";
			}
			catch(Exception e){
				System.out.println(e);
				return "failure";
			}
		}
		/*Sample data ends*/
	}

	
	public List<Employee> getEmployees() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to return details about all the employees must be implemented
		 * Each record is required to be encapsulated as a "Employee" class object and added to the "employees" List
		 */

		List<Employee> employees = new ArrayList<Employee>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select P.Id, P.LastName, P.FirstName, P.Address, L.Zipcode, L.City, L.State, C.Email, C.CreditCardNumber, C.Rating " +
					"from snisonoff.Client C, snisonoff.Location L, snisonoff.Person P " +
					"where c.Id = P.SSN and P.Zipcode = L.Zipcode");
			while(rs.next()){
				Customer c = new Customer();
				c.setSsn(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
				c.setClientId(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
				c.setId(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
				c.setLastName(rs.getString(2));
				c.setFirstName(rs.getString(3));
				c.setAddress(rs.getString(4));
				Location l = new Location();
				l.setZipCode(rs.getInt(5));
				l.setCity(rs.getString(6));
				l.setState(rs.getString(7));
				c.setLocation(l);
				c.setEmail(rs.getString(8));
				c.setCreditCard(rs.getString(9));
				c.setRating(rs.getInt(10));
				customers.add(c);
			}
			con.close();
		}
		catch(Exception x){
			System.out.println(x);
			System.out.println("Error loading the customers");
		}
		return employees;
	}

	public Employee getEmployee(String employeeID) {

		/*
		 * The students code to fetch data from the database based on "employeeID" will be written here
		 * employeeID, which is the Employee's ID who's details have to be fetched, is given as method parameter
		 * The record is required to be encapsulated as a "Employee" class object
		 */

		return getDummyEmployee();
	}
	
	public Employee getHighestRevenueEmployee() {
		
		/*
		 * The students code to fetch employee data who generated the highest revenue will be written here
		 * The record is required to be encapsulated as a "Employee" class object
		 */
		
		return getDummyEmployee();
	}

	public String getEmployeeID(String username) {
		/*
		 * The students code to fetch data from the database based on "username" will be written here
		 * username, which is the Employee's email address who's Employee ID has to be fetched, is given as method parameter
		 * The Employee ID is required to be returned as a String
		 */

		return "111-11-1111";
	}

}
