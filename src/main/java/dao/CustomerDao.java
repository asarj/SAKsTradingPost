package dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import model.Customer;
import model.Location;

public class CustomerDao {
	/*
	 * This class handles all the database operations related to the customer table
	 */
    public Customer getDummyCustomer() {
        Location location = new Location();
        location.setZipCode(11790);
        location.setCity("Stony Brook");
        location.setState("NY");

        Customer customer = new Customer();
        customer.setId("111-11-1111");
        customer.setAddress("123 Success Street");
        customer.setLastName("Lu");
        customer.setFirstName("Shiyong");
        customer.setEmail("shiyong@cs.sunysb.edu");
        customer.setLocation(location);
        customer.setTelephone("5166328959");
        customer.setCreditCard("1234567812345678");
        customer.setRating(1);

        return customer;
    }
    public List<Customer> getDummyCustomerList() {
        /*Sample data begins*/
        List<Customer> customers = new ArrayList<Customer>();

        for (int i = 0; i < 10; i++) {
            customers.add(getDummyCustomer());
        }
		/*Sample data ends*/

        return customers;
    }

    /**
	 * @return ArrayList<Customer> object
	 */
	public List<Customer> getCustomers(String searchKeyword) {
		/*
		 * This method fetches one or more customers based on the searchKeyword and returns it as an ArrayList
		 */
		

		/*
		 * The students code to fetch data from the database based on searchKeyword will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */
		
		return getDummyCustomerList();
	}


	public Customer getHighestRevenueCustomer() {
		/*
		 * This method fetches the customer who generated the highest total revenue and returns it
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */

		return getDummyCustomer();
	}

	public Customer getCustomer(String customerID) {

		/*
		 * This method fetches the customer details and returns it
		 * customerID, which is the Customer's ID who's details have to be fetched, is given as method parameter
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */
        Customer c = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from snisonoff.Client where id=" + customerID.replace("-", ""));
			while (rs.next()) {
                System.out.println(rs.getString(1) + "  " + rs.getInt(2) + "  " + rs.getString(3) + "  " + rs.getString(4));
                // Column 1 = email, column 2 = rating, column 3 = credit card num, column 4 = id
                c = new Customer();
                c.setEmail(rs.getString(1));
                c.setRating(Integer.parseInt(rs.getString(2)));
                c.setCreditCard(rs.getString(3));
                c.setClientId(rs.getString(4));
            }
            ResultSet rt = stmt.executeQuery("select * from snisonoff.Person where SSN=" + customerID.replace("-", ""));
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
            System.out.println(c.printCustomerDetails());
            con.close();
		}
		catch(Exception x){
			System.out.println(x);
            System.out.println("Customer not found");
		}
		if(c == null){
		    return null;
        }
		return c;
	}
	
	public String deleteCustomer(String customerID) {

		/*
		 * This method deletes a customer returns "success" string on success, else returns "failure"
		 * The students code to delete the data from the database will be written here
		 * customerID, which is the Customer's ID who's details have to be deleted, is given as method parameter
		 */

		/*Sample data begins*/
        Customer c = getCustomer(customerID);
        if (c == null){
            return "failure";
        }
        else{
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
                Statement stmt = con.createStatement();
                stmt.execute("delete from snisonoff.Client where Id=" + customerID.replace("-", ""));
                stmt.execute("delete from snisonoff.Person where Id=" + customerID.replace("-", ""));
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


	public String getCustomerID(String email) {
		/*
		 * This method returns the Customer's ID based on the provided email address
		 * The students code to fetch data from the database will be written here
		 * username, which is the email address of the customer, who's ID has to be returned, is given as method parameter
		 * The Customer's ID is required to be returned as a String
		 */
		String id = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select Id from snisonoff.Client where Email=" + email);
            while(rs.next()){
                id = rs.getString(1);
            }
            con.close();
            return "success";
        }
        catch(Exception e){
            System.out.println(e);
        }
        if(id.equals("")){
            return "NOT FOUND";
        }
		return id;
	}


	public String addCustomer(Customer customer) {

		/*
		 * All the values of the add customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the customer details and return "success" or "failure" based on result of the database insertion.
		 */
		
		/*Sample data begins*/
        if(customer == null){
            return "failure";
        }
        int zip = customer.getLocation().getZipCode();
        String city = customer.getLocation().getCity();
        String state = customer.getLocation().getState();
        String email = customer.getEmail();
        int rating = customer.getRating();
        String creditCard = customer.getCreditCard();
        int clientId = Integer.parseInt(customer.getClientId().replace("-", ""));
        int SSN = Integer.parseInt(customer.getSsn().replace("-", ""));
        String lastName = customer.getLastName();
        String firstName = customer.getFirstName();
        String address = customer.getAddress();
        String tel = customer.getTelephone();

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

            sql = "insert into snisonoff.Client values (?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setInt(2, rating);
            pst.setString(3, creditCard);
            pst.setInt(4, clientId);
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

                sql = "insert into snisonoff.Client values (?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, email);
                pst.setInt(2, rating);
                pst.setString(3, creditCard);
                pst.setInt(4, clientId);
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

	public String editCustomer(Customer customer) {
		/*
		 * All the values of the edit customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		/*Sample data begins*/
        if(customer == null){
            return "failure";
        }
        int zip = customer.getLocation().getZipCode();
        String city = customer.getLocation().getCity();
        String state = customer.getLocation().getState();
        String email = customer.getEmail();
        int rating = customer.getRating();
        String creditCard = customer.getCreditCard();
        int clientId = Integer.parseInt(customer.getClientId().replace("-", ""));
        int SSN = Integer.parseInt(customer.getSsn().replace("-", ""));
        String lastName = customer.getLastName();
        String firstName = customer.getFirstName();
        String address = customer.getAddress();
        String tel = customer.getTelephone();

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

            sql = "update snisonoff.Person set Email = ?, Rating = ?, CreditCardNumber = ? where Id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setInt(2, rating);
            pst.setString(3, creditCard);
            pst.setInt(4, SSN);
            con.close();
            return "success";
        }
        catch(Exception e){
            System.out.println(e);
            return "failure";
        }
		/*Sample data ends*/

	}

    public List<Customer> getCustomerMailingList() {

		/*
		 * This method fetches the all customer mailing details and returns it
		 * The students code to fetch data from the database will be written here
		 */
        List<Customer> customers = new ArrayList<Customer>();
        /* THIS IS WHERE QUERY CODE STARTS*/
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select P.SSN, P.LastName, P.FirstName, P.Address, L.Zipcode, L.City, L.State, C.Email " +
                                                    "from snisonoff.Client c, snisonoff.Location l, snisonoff.Person p " +
                                                    "where c.Id = p.SSN and p.Zipcode = l.Zipcode");
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
                customers.add(c);
            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the rest of the customer mailing list");
        }

        return customers;
    }

    public List<Customer> getAllCustomers() {
        /*
		 * This method fetches returns all customers
		 */
        List<Customer> customers = new ArrayList<Customer>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select P.SSN, P.LastName, P.FirstName, P.Address, L.Zipcode, L.City, L.State, C.Email, C.CreditCardNumber, C.Rating " +
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

        return customers;
    }
}
