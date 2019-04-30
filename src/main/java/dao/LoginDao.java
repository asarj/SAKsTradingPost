package dao;

import model.Customer;
import model.Login;

import java.sql.*;

public class LoginDao {
	/*
	 * This class handles all the database operations related to login functionality
	 */
	
	
	public Login login(String username, String password, String role) {
		/*
		 * Return a Login object with role as "manager", "customerRepresentative" or "customer" if successful login
		 * Else, return null
		 * The role depends on the type of the user, which has to be handled in the database
		 * username, which is the email address of the user, is given as method parameter
		 * password, which is the password of the user, is given as method parameter
		 * Query to verify the username and password and fetch the role of the user, must be implemented
		 */
		
		/*Sample data begins*/
//		Login login = new Login();
//		CustomerDao cd = new CustomerDao();
//		String succ = cd.getCustomerID(username);
//		String dbPassword = null;
//		if(succ != null && !succ.equals("")){
//			login.setUsername(username);
//			try {
//				Class.forName("com.mysql.jdbc.Driver");
//				Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
//				Statement stmt = con.createStatement();
//				String sql = "select Password from snisonoff.Login where Email=?";
//				PreparedStatement p = con.prepareStatement(sql);
//				p.setString(1, username);
//				ResultSet rs = p.executeQuery();
//				while(rs.next()){
//					dbPassword = rs.getString(1);
//				}
//				if(dbPassword == null || !password.equals(dbPassword)){
//					login.setPassword(null);
//					return null;
//				}
//				else{
//					String r = null;
//					login.setPassword(dbPassword);
//					sql = "select Position from snisonoff.Employee where SSN=?";
//					PreparedStatement pst = con.prepareStatement(sql);
//					pst.setString(1, succ);
//					ResultSet rst = pst.executeQuery();
//					while (rst.next()) {
//						r = rst.getString(1);
//					}
//					if (r == null && role.equals("customer")) {
//						login.setRole(role);
//						System.out.println("Customer login successful!");
//						return login;
//					}
//					else{
//						System.out.println("Customer login failed - incorrect role");
//						return null;
//					}
//				}
//			}
//			catch(Exception e){
//				System.out.println(e);
//				return null;
//			}
//		}
//		else {
//			EmployeeDao ed = new EmployeeDao();
//			succ = ed.getEmployeeID(username);
//			String r = "";
//			if (succ != null && !succ.equals("")) {
//				login.setUsername(username);
//				try {
//					Class.forName("com.mysql.jdbc.Driver");
//					Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
//					Statement stmt = con.createStatement();
//					String sql = "select Password from snisonoff.Login where Email=?";
//					PreparedStatement p = con.prepareStatement(sql);
//					p.setString(1, username);
//					ResultSet rs = p.executeQuery();
//					while (rs.next()) {
//						dbPassword = rs.getString(1);
//					}
//					if (dbPassword == null || !password.equals(dbPassword)) {
//						login.setPassword(null);
//						return null;
//					} else {
//						login.setPassword(dbPassword);
//						sql = "select Position from snisonoff.Employee where SSN=?";
//						PreparedStatement pst = con.prepareStatement(sql);
//						pst.setString(1, succ);
//						ResultSet rst = pst.executeQuery();
//						while (rst.next()) {
//							r = rst.getString(1);
//						}
//						if (r.equals("Representative") && role.equals("customerRepresentative")) {
//							login.setRole(role);
//							System.out.println("Customer Rep login successful!");
//							return login;
//						}
//						else if(r.equals("Manager") && role.equals("manager")){
//							login.setRole(role);
//							System.out.println("Manager login successful!");
//							return login;
//						}
//						else {
//							return null;
//						}
//					}
//				} catch (Exception e) {
//					System.out.println(e);
//					return null;
//				}
//			} else {
//				return null;
//			}
//		}

		/*Sample data ends*/
		/*
			BASE LOGIN CODE, REMOVE THE BELOW CODE WHEN TESTING IS DONE
		*/
		Login login = new Login();
		login.setRole(role);
		return login;
	}
	
	public String addUser(Login login) {
		/*
		 * Query to insert a new record for user login must be implemented
		 * login, which is the "Login" Class object containing username and password for the new user, is given as method parameter
		 * The username and password from login can get accessed using getter methods in the "Login" model
		 * e.g. getUsername() method will return the username encapsulated in login object
		 * Return "success" on successful insertion of a new user
		 * Return "failure" for an unsuccessful database operation
		 */
		
		/*Sample data begins*/
		String userEmail = login.getUsername();
		String userPassword = login.getPassword();
		String userRole = login.getRole();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
			Statement stmt = con.createStatement();
			String sql = "select Email from snisonoff.Login where Email=?";
			PreparedStatement p = con.prepareStatement(sql);
			p.setString(1, userEmail);
			ResultSet rs = p.executeQuery();
			String s = null;
			while(rs.next()){
				s = rs.getString(1);
			}
			if(s == null){
				sql = "insert into snisonoff.Login values(?, ?)";
				p = con.prepareStatement(sql);
				p.setString(1, userEmail);
				p.setString(2, userPassword);
				p.executeUpdate();

				String r = null;
				EmployeeDao ed = new EmployeeDao();
				String succ = ed.getEmployeeID(userEmail);
				sql = "select Position from snisonoff.Employee where SSN=?";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, succ);
				ResultSet rst = pst.executeQuery();
				while (rst.next()) {
					r = rst.getString(1);
				}
				if (userRole.equals("customerRepresentative")) {
					sql = "update snisonoff.Employee set Position = ? where SSN = ?";
					p = con.prepareStatement(sql);
					p.setString(1, "Representative");
					p.setString(2, succ);
					p.executeUpdate();
					return "success";
				}
				else if(userRole.equals("manager")){
					sql = "update snisonoff.Employee set Position = ? where SSN = ?";
					p = con.prepareStatement(sql);
					p.setString(1, "Manager");
					p.setString(2, succ);
					p.executeUpdate();
					return "success";
				}
				else if(userRole.equals("customer")){
					return "success";
				}
				else{
					return "failure";
				}
			}
			else{
				return "failure";
			}
		}
		catch(Exception e){
			System.out.println(e);
			return "failure";
		}
//		return "failure";
		/*Sample data ends*/
	}

}
