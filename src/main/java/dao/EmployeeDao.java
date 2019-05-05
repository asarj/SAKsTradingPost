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

			sql = "insert into snisonoff.Person values (?, ?, ?, ?, ?, ?, ?)";
			pst = con.prepareStatement(sql);
			pst.setInt(1, SSN);
			pst.setString(2, lastName);
			pst.setString(3, firstName);
			pst.setString(4, address);
			pst.setInt(5, zip);
			pst.setString(6, tel);
			pst.setString(7, email);
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
				String sql = "insert into snisonoff.Person values (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setInt(1, SSN);
				pst.setString(2, lastName);
				pst.setString(3, firstName);
				pst.setString(4, address);
				pst.setInt(5, zip);
				pst.setString(6, tel);
				pst.setString(7, email);
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
			ResultSet rs = stmt.executeQuery("select E.SSN, P.LastName, P.FirstName, P.Address, L.Zipcode, L.City, L.State, P.Email, E.StartDate, E.HourlyRate, P.Telephone " +
					"from snisonoff.Employee E, snisonoff.Location L, snisonoff.Person P " +
					"where E.SSN = P.SSN and P.Zipcode = L.Zipcode");
			while(rs.next()){
				Employee c = new Employee();
				c.setSsn(rs.getString(1));
				c.setEmployeeID(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
				c.setId(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
				c.setSsn(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
				c.setLastName(rs.getString(2));
				c.setFirstName(rs.getString(3));
				c.setAddress(rs.getString(4));
				Location l = new Location();
				l.setZipCode(rs.getInt(5));
				l.setCity(rs.getString(6));
				l.setState(rs.getString(7));
				c.setLocation(l);
				c.setEmail(rs.getString(8));
				c.setStartDate(rs.getString(9));
				c.setHourlyRate(rs.getInt(10));
				c.setTelephone(rs.getString(11));
				employees.add(c);
			}
			con.close();
		}
		catch(Exception x){
			System.out.println(x);
			System.out.println("Error loading the employees");
		}
		return employees;
	}

	public Employee getEmployee(String employeeID) {

		/*
		 * The students code to fetch data from the database based on "employeeID" will be written here
		 * employeeID, which is the Employee's ID who's details have to be fetched, is given as method parameter
		 * The record is required to be encapsulated as a "Employee" class object
		 */

		Employee c = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from snisonoff.Employee where SSN=" +  employeeID.replace("-", ""));
			while (rs.next()) {
				System.out.println(rs.getString(1) + "  " + rs.getInt(2) + "  " + rs.getString(3) + "  " + rs.getString(4));
				// Column 1 = email, column 2 = rating, column 3 = credit card num, column 4 = id
				c = new Employee();
				c.setEmployeeID(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
				c.setSsn(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
				c.setId(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
				c.setStartDate(rs.getString(3));
				c.setHourlyRate(rs.getFloat(4));
				c.setLevel(rs.getString(5));
			}
			ResultSet rt = stmt.executeQuery("select * from snisonoff.Person where SSN=" + employeeID.replace("-", ""));
			String zip = "";
			while (rt.next()) {
				c.setId(rt.getString(1));
				c.setSsn(rt.getString(1));
				c.setLastName(rt.getString(2));
				c.setFirstName(rt.getString(3));
				c.setAddress(rt.getString(4));
				zip = rt.getString(5);
				c.setTelephone(rt.getString(6));
			}
//            rt.close();
			ResultSet ru = stmt.executeQuery("select * from snisonoff.Location where Zipcode=" + zip);
			while (ru.next()) {
				Location l = new Location();
				l.setZipCode(ru.getInt(1));
				l.setCity(ru.getString(2));
				l.setState(ru.getString(3));
				c.setLocation(l);
			}
//            ru.close();
//			rs.close();
			con.close();
		}
		catch(Exception x){
			System.out.println(x);
			System.out.println("Employee not found");
		}
		if(c == null){
			return null;
		}
		return c;
	}

	/* SOMETHING IS WRONG HERE, COME BACK WHEN STOCKS, ORDERS, AND TRANSACTIONS ARE WORKING*/
	public Employee getHighestRevenueEmployee() {
		
		/*
		 * The students code to fetch employee data who generated the highest revenue will be written here
		 * The record is required to be encapsulated as a "Employee" class object
		 */

		Employee c = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
			try {
				stmt.executeUpdate("DROP VIEW EmployeeName");
				stmt.executeUpdate("DROP VIEW CROrder");
				stmt.executeUpdate("DROP VIEW CRRevenue");
				stmt.executeUpdate("DROP VIEW HighestRevenue");
			} catch(SQLSyntaxErrorException s){
				try{
					stmt.executeUpdate("DROP VIEW CROrder");
					stmt.executeUpdate("DROP VIEW CRRevenue");
					stmt.executeUpdate("DROP VIEW HighestRevenue");
				}
				catch(SQLSyntaxErrorException t){
					try{
						stmt.executeUpdate("DROP VIEW CRRevenue");
						stmt.executeUpdate("DROP VIEW HighestRevenue");
					}
					catch(SQLSyntaxErrorException u){
						try{
							stmt.executeUpdate("DROP VIEW HighestRevenue");
						}
						catch(SQLSyntaxErrorException v){

						}
					}
				}
			} finally {
				String sql5 = "Create View EmployeeName (Id, SSN, LastName, FirstName) AS " +
						"Select E.Id, P.SSN, P.LastName, P.FirstName " +
						"From Employee E, Person P " +
						"Where E.SSN = P.SSN";
				String sql = "Create View CROrder (StockSymbol, StockType, LastName, FirstName, EmployeeId, Fee) AS " +
						"Select S.StockSymbol, S.Type, N.LastName, N.FirstName, N.Id, T.Fee " +
						"From Trade Tr, Stock S, Transaction T, EmployeeName N " +
						"Where Tr.StockId = S.StockSymbol and T.Id = Tr.TransactionId and N.Id = Tr.BrokerID";
				String sql2 = "Create View CRRevenue (LastName, FirstName, Revenue) AS " +
						"Select LastName, FirstName, SUM(Fee) " +
						"From CROrder " +
						"GROUP BY LastName, FirstName";
				String sql3 = "Create View HighestRevenue (MaxRevenue) AS " +
						"Select MAX(Revenue) " +
						"From CRRevenue";
				String sql4 = "Select LastName, FirstName " +
						"From CRRevenue "
//                        + "Where Revenue >= HighestRevenue"
						;
//            PreparedStatement pst = con.prepareStatement(sql);
//            PreparedStatement pst2 = con.prepareStatement(sql2);
//            PreparedStatement pst3 = con.prepareStatement(sql3);
//            PreparedStatement pst4 = con.prepareStatement(sql4);
//            stmt.addBatch(sql);
//            stmt.addBatch(sql2);
//            stmt.addBatch(sql3);
//            stmt.addBatch(sql4);
//            int[] res = stmt.executeBatch();
//            System.out.println(Arrays.toString(res));
//            con.commit();
				stmt.executeUpdate(sql5);
				stmt.executeUpdate(sql);
				stmt.executeUpdate(sql2);
				stmt.executeUpdate(sql3);
				ResultSet rs = stmt.executeQuery(sql4);
				rs.next();
				System.out.println(rs.toString());
				c = new Employee();
				c.setLastName(rs.getString(1));
				System.out.println(c.getLastName());
				c.setFirstName(rs.getString(2));
				System.out.println(c.getFirstName());
				stmt.close();

				sql = "select E.SSN, P.FirstName, P.LastName, P.Address, L.City, L.State, L.Zipcode, P.Telephone, P.Email, E.StartDate, E.HourlyRate from Employee E, Person P, Location L where E.SSN = P.SSN and P.Zipcode = L.Zipcode and P.FirstName = ? and P.LastName = ?";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, c.getFirstName());
				pst.setString(2, c.getLastName());
				rs = pst.executeQuery();
				while(rs.next()){
					c.setEmployeeID(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
					c.setSsn(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
					c.setId(rs.getString(1).substring(0, 3) + "-" + rs.getString(1).substring(3, 5) + "-" + rs.getString(1).substring(5));
					c.setFirstName(rs.getString(2));
					c.setLastName(rs.getString(3));
					c.setAddress(rs.getString(4));
					Location l = new Location();
					l.setCity(rs.getString(5));
					l.setState(rs.getString(6));
					l.setZipCode(rs.getInt(7));
					c.setLocation(l);
					c.setTelephone(rs.getString(8));
					c.setEmail(rs.getString(9));
					c.setStartDate(rs.getString(10));
					c.setHourlyRate(rs.getFloat(11));
					return c;
				}
				con.close();
				return c;
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return c;
	}

	public String getEmployeeID(String username) {
		/*
		 * The students code to fetch data from the database based on "username" will be written here
		 * username, which is the Employee's email address who's Employee ID has to be fetched, is given as method parameter
		 * The Employee ID is required to be returned as a String
		 */

		String id = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
			Statement stmt = con.createStatement();
			String sql = "select P.SSN from snisonoff.Employee E, snisonoff.Person P where E.SSN = P.SSN and P.Email=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				id = rs.getString(1);
			}
			con.close();
			return id;
		}
		catch(Exception e){
			System.out.println(e);
			return null;
		}
	}

}
