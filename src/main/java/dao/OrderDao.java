package dao;

import model.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao {

    public Order getDummyTrailingStopOrder() {
        TrailingStopOrder order = new TrailingStopOrder();

        order.setId(1);
        order.setDatetime(new Date());
        order.setNumShares(5);
        order.setPercentage(12.0);
        return order;
    }

    public Order getDummyMarketOrder() {
        MarketOrder order = new MarketOrder();

        order.setId(1);
        order.setDatetime(new Date());
        order.setNumShares(5);
        order.setBuySellType("buy");
        return order;
    }

    public Order getDummyMarketOnCloseOrder() {
        MarketOnCloseOrder order = new MarketOnCloseOrder();

        order.setId(1);
        order.setDatetime(new Date());
        order.setNumShares(5);
        order.setBuySellType("buy");
        return order;
    }

    public Order getDummyHiddenStopOrder() {
        HiddenStopOrder order = new HiddenStopOrder();

        order.setId(1);
        order.setDatetime(new Date());
        order.setNumShares(5);
        order.setPricePerShare(145.0);
        return order;
    }

    public List<Order> getDummyOrders() {
        List<Order> orders = new ArrayList<Order>();

        for (int i = 0; i < 3; i++) {
            orders.add(getDummyTrailingStopOrder());
        }

        for (int i = 0; i < 3; i++) {
            orders.add(getDummyMarketOrder());
        }

        for (int i = 0; i < 3; i++) {
            orders.add(getDummyMarketOnCloseOrder());
        }

        for (int i = 0; i < 3; i++) {
            orders.add(getDummyHiddenStopOrder());
        }

        return orders;
    }

    public String submitOrder(Order order, Customer customer, Employee employee, Stock stock) {     // TESTING: DONE

        /*
         * Student code to place stock order
         * Employee can be null, when the order is placed directly by Customer
         * */

        /*Sample data begins*/
        String orderType = null;
        try{ orderType = ((MarketOrder) order).getOrderType(); } catch(Exception e){ }
        try{ orderType = ((MarketOnCloseOrder) order).getOrderType(); } catch(Exception e){ }
        try{ orderType = ((TrailingStopOrder) order).getOrderType(); } catch(Exception e){ }
        try{ orderType = ((HiddenStopOrder) order).getOrderType(); } catch(Exception e){ }

        String transId = (int)(Math.random() * 10000) + "";
        String orderId = (int)(Math.random() * 10000) + "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String date = /** dateFormat.format(order.getDatetime()) + **/ "2019-05-06 00:00:00";
        order.setId(Integer.parseInt(orderId));

//DUMMY CODE
        if(customer == null){
            Location location = new Location();
            location.setZipCode(11790);
            location.setCity("Stony Brook");
            location.setState("NY");
            customer = new Customer();
            customer.setId("111-11-1111");
            customer.setAddress("123 Success Street");
            customer.setLastName("Lu");
            customer.setFirstName("Shiyong");
            customer.setEmail("shiyong@cs.sunysb.edu");
            customer.setLocation(location);
            customer.setTelephone("5166328959");
            customer.setCreditCard("1234567812345678");
            customer.setRating(1);
        }

        // ---

        if(employee == null){
            employee = new Employee();
            employee.setEmployeeID("000000000");
            employee.setId("000000000");
            employee.setSsn("000000000");
        }
        if(orderType.equals("Market") || orderType.equals("MarketOnClose")){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");

                String sql = "select PricePerShare from snisonoff.Stock where StockSymbol = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, stock.getSymbol());
                ResultSet rs = pst.executeQuery();
                String s = "";
                while(rs.next()){
                    s = rs.getString(1);
                }
                sql = "insert into snisonoff.Order values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setInt(1, order.getNumShares());
                pst.setString(2, stock.getSymbol());
                pst.setDouble(3, Double.parseDouble(s));
                pst.setInt(4, Integer.parseInt(orderId));
                pst.setString(5, date);
                pst.setDouble(6, 0.0);
                pst.setString(7, orderType);
                if(orderType.equals("Market"))
                    pst.setString(8, ((MarketOrder) order).getBuySellType());
                else
                    pst.setString(8, ((MarketOnCloseOrder) order).getBuySellType());
                pst.setString(9, customer.getClientId().replace("-", ""));
                pst.setString(10, employee.getEmployeeID().replace("-", ""));
                pst.executeUpdate();

                sql = "insert into snisonoff.Transaction values (?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, transId);
                pst.setDouble(2, (Double.parseDouble(s) * 0.05));
                pst.setString(3, date);
                pst.setDouble(4, Double.parseDouble(s));
                pst.executeUpdate();

                sql = "insert into snisonoff.Trade values (?, ?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, customer.getClientId().replace("-", ""));
                pst.setString(2, employee.getEmployeeID().replace("-", ""));
                pst.setString(3, transId);
                pst.setInt(4, Integer.parseInt(orderId));
                pst.setString(5, stock.getSymbol());
                pst.executeUpdate();

                con.close();
                return "success";
            }
            catch(Exception e){
                System.out.println(e);
                return "failure";
            }
        }
        else
            if(orderType.equals("TrailingStop") || orderType.equals("HiddenStop")){
            // Trailing Stop order
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");

                String sql = "select PricePerShare from snisonoff.Stock where StockSymbol = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, stock.getSymbol());
                ResultSet rs = pst.executeQuery();
                Double s = 0.0;
                while(rs.next()){
                    s = rs.getDouble(1);
                }

                sql = "insert into snisonoff.Order values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setInt(1, order.getNumShares());
                pst.setString(2, stock.getSymbol());
                pst.setDouble(3, s);
                pst.setInt(4, Integer.parseInt(orderId));
                pst.setString(5, date);
                pst.setDouble(6, order.getPercentage());
                pst.setString(7, orderType);
                pst.setString(8, null);
                pst.setString(9, customer.getClientId().replace("-", ""));
                pst.setString(10, employee.getEmployeeID().replace("-", ""));
                pst.executeUpdate();

//                if(s == order.getPricePerShare() + (order.getPricePerShare() * order.getPercentage())){
//                    sql = "insert into snisonoff.Transaction values (?, ?, ?, ?)";
//                    pst = con.prepareStatement(sql);
//                    pst.setString(1, transId);
//                    pst.setDouble(2, (s * 0.05));
//                    pst.setString(3, date);
//                    pst.setDouble(4, s);
//                    pst.executeUpdate();
//
//                    sql = "insert into snisonoff.Trade values (?, ?, ?, ?, ?)";
//                    pst = con.prepareStatement(sql);
//                    pst.setString(1, customer.getClientId().replace("-", ""));
//                    pst.setString(2, employee.getEmployeeID().replace("-", ""));
//                    pst.setString(3, transId);
//                    pst.setInt(4, order.getId());
//                    pst.setString(5, stock.getSymbol());
//                    pst.executeUpdate();
//                }
//                else{
//                    // Let the setStock trigger in the stock?/Order? table handle submission of the Order
//                }
                con.close();
                return "success";
            }
            catch(Exception e){
                System.out.println(e);
                return "failure";
            }
        }
        else{
            return "failure";
        }

        /*Sample data ends*/
    }

    public List<Order> getOrderByStockSymbol(String stockSymbol) {      // TESTING: ALMOST DONE DETAILS COLUMN DOES NOT DISPLAY PROPERLY
        /*
         * Student code to get orders by stock symbol
         */
        List<Order> orders = new ArrayList<Order>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
            Statement stmt = con.createStatement();

            String sql = "select O.Id, O.NumShares, O.PricePerShare, O.DateTime, O.PriceType from snisonoff.Order O where O.StockName = ? order by O.Id ASC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, stockSymbol);
            ResultSet rs = pst.executeQuery();
            int curr = 0;
            while(rs.next()){
                int ind = rs.getInt(1);
                if(ind > curr){
                    curr = ind;
                }
                else{
                    continue;
                }
                String type = rs.getString(5);
                if(type.equals("Market")){
                    try{
                        MarketOrder o = new MarketOrder();
                        o.setId(rs.getInt(1));
                        o.setNumShares(rs.getInt(2));
                        o.setPricePerShare(rs.getFloat(3));
                        o.setDatetime(rs.getDate(4));
                        o.setPriceType(rs.getString(5));

                        orders.add(o);
                    }
                    catch(Exception e){

                    }
                }
                else if(type.equals("MarketOnClose")){
                    try{
                        MarketOnCloseOrder o = new MarketOnCloseOrder();
                        o.setId(rs.getInt(1));
                        o.setNumShares(rs.getInt(2));
                        o.setPricePerShare(rs.getFloat(3));
                        o.setDatetime(rs.getDate(4));
                        o.setPriceType(rs.getString(5));

                        orders.add(o);
                    }
                    catch(Exception e){

                    }
                }
                else if(type.equals("TrailingStop")){
                    try{
                        TrailingStopOrder o = new TrailingStopOrder();
                        o.setId(rs.getInt(1));
                        o.setNumShares(rs.getInt(2));
                        o.setPricePerShare(rs.getFloat(3));
                        o.setDatetime(rs.getDate(4));
                        o.setPriceType(rs.getString(5));

                        orders.add(o);
                    }
                    catch(Exception e){

                    }
                }
                else{
                    try{
                        HiddenStopOrder o = new HiddenStopOrder();
                        o.setId(rs.getInt(1));
                        o.setNumShares(rs.getInt(2));
                        o.setPricePerShare(rs.getFloat(3));
                        o.setDatetime(rs.getDate(4));
                        o.setPriceType(rs.getString(5));

                        orders.add(o);
                    }
                    catch(Exception e){

                    }
                }
//                o.setId(rs.getInt(1));
//                o.setNumShares(rs.getInt(2));
//                o.setPricePerShare(rs.getFloat(3));
//                o.setDatetime(rs.getDate(4));
//                o.setPriceType(rs.getString(5));
//
//                orders.add(o);
            }
            con.close();
            return orders;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }

    }

    public List<Order> getOrderByCustomerName(String customerName) {    // TESTING: ALMOST DONE DETAILS COLUMN DOES NOT DISPLAY PROPERLY
        /*
         * Student code to get orders by customer name
         */
        List<Order> orders = new ArrayList<Order>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
            //   Statement stmt = con.createStatement();

            String sql = "select C.Id, P.SSN, O.Id, O.NumShares, O.PricePerShare, O.DateTime, O.PriceType \n" +
                    "from snisonoff.Order O, snisonoff.Person P, snisonoff.Client C, snisonoff.Account A, snisonoff.Trade T \n" +
                    "where O.Id = T.OrderId and T.AccountId= A.Id and C.Id = P.SSN and P.LastName = ? and P.FirstName = ? order by O.Id ASC";
            PreparedStatement pst = con.prepareStatement(sql);
            // Assumes customerName has a first and last name
            pst.setString(1, customerName.substring(customerName.indexOf(" ") + 1));
            pst.setString(2, customerName.substring(0, customerName.indexOf(" ")));
            ResultSet rs = pst.executeQuery();
            int curr = 0;
            while (rs.next()) {
                int ind = rs.getInt(3);
                if (ind > curr){
                    curr = ind;
                }
                else{
                    continue;
                }
                String type = rs.getString(7);
                if(type.equals("Market")){
                    try{
                        MarketOrder o = new MarketOrder();
                        o.setClient(rs.getInt(1));
                        o.setId(rs.getInt(3));
                        o.setNumShares(rs.getInt(4));
                        o.setPricePerShare(rs.getDouble(5));
                        o.setDatetime(rs.getDate(6));
                        o.setOrderType(rs.getString(7));
                        orders.add(o);
                    }
                    catch(Exception e){

                    }
                }
                else if(type.equals("MarketOnClose")){
                    try{
                        MarketOnCloseOrder o = new MarketOnCloseOrder();
                        o.setClient(rs.getInt(1));
                        o.setId(rs.getInt(3));
                        o.setNumShares(rs.getInt(4));
                        o.setPricePerShare(rs.getDouble(5));
                        o.setDatetime(rs.getDate(6));
                        o.setOrderType(rs.getString(7));
                        orders.add(o);
                    }
                    catch(Exception e){

                    }
                }
                else if(type.equals("TrailingStop")){
                    try{
                        TrailingStopOrder o = new TrailingStopOrder();
                        o.setClient(rs.getInt(1));
                        o.setId(rs.getInt(3));
                        o.setNumShares(rs.getInt(4));
                        o.setPricePerShare(rs.getDouble(5));
                        o.setDatetime(rs.getDate(6));
                        o.setOrderType(rs.getString(7));
                        orders.add(o);
                    }
                    catch(Exception e){

                    }
                }
                else{
                    try{
                        HiddenStopOrder o = new HiddenStopOrder();
                        o.setClient(rs.getInt(1));
                        o.setId(rs.getInt(3));
                        o.setNumShares(rs.getInt(4));
                        o.setPricePerShare(rs.getDouble(5));
                        o.setDatetime(rs.getDate(6));
                        o.setOrderType(rs.getString(7));
                        orders.add(o);
                    }
                    catch(Exception e){

                    }
                }
//                o.setClient(rs.getInt(1));
//                o.setId(rs.getInt(3));
//                o.setNumShares(rs.getInt(4));
//                o.setPricePerShare(rs.getDouble(5));
//                o.setDatetime(rs.getDate(6));
//                o.setOrderType(rs.getString(7));
//                orders.add(o);
            }
            con.close();
            return orders;
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
        //return getDummyOrders();
    }

    public List<Order> getOrderHistory(String customerId) {     // TESTING: DONE
        /*
         * The students code to fetch data from the database will be written here
         * Show orders for given customerId
         */
        List<Order> orders = new ArrayList<Order>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
            //   Statement stmt = con.createStatement();

            String sql = "SELECT DISTINCT P.SSN, P.LastName, P.FirstName, C.Id, A.Id, O.Id, O.StockName, O.DateTime, T.DateTime, O.PriceType, O.NumShares " +
                    "FROM snisonoff.Person P, snisonoff.Client C, snisonoff.Account A, snisonoff.Order O, snisonoff.Trade Tr, snisonoff.Transaction T " +
                    "WHERE P.SSN = C.Id and C.Id = A.Id and A.Id = Tr.AccountId and Tr.OrderId = O.Id and Tr.TransactionId = T.Id and C.Id = ? " +
                    "ORDER BY O.Id ASC";
            // Added and O.Id = ?
            PreparedStatement pst = con.prepareStatement(sql);
            // Assumes customerName has a first and last name
            pst.setString(1, customerId);
            ResultSet rs = pst.executeQuery();
            int curr = 0;
            while (rs.next()) {
                int ind = rs.getInt(6);
                if (ind > curr){
                    curr = ind;
                }
                else{
                    continue;
                }
                String type = rs.getString(10);
                if(type.equals("Market")){
                    MarketOrder o = new MarketOrder();
                    o.setClient(rs.getInt(4));
                    o.setId(rs.getInt(6));
                    o.setStockName(rs.getString(7));
                    o.setDatetime(rs.getDate(8));
                    o.setPriceType(rs.getString(10));
                    o.setNumShares(rs.getInt(11));
                    orders.add(o);
                }
                else if(type.equals("MarketOnClose")){
                    MarketOnCloseOrder o = new MarketOnCloseOrder();
                    o.setClient(rs.getInt(4));
                    o.setId(rs.getInt(6));
                    o.setStockName(rs.getString(7));
                    o.setDatetime(rs.getDate(8));
                    o.setPriceType(rs.getString(10));
                    o.setNumShares(rs.getInt(11));
                    orders.add(o);
                }
                else if(type.equals("TrailingStop")){
                    TrailingStopOrder o = new TrailingStopOrder();
                    o.setClient(rs.getInt(4));
                    o.setId(rs.getInt(6));
                    o.setStockName(rs.getString(7));
                    o.setDatetime(rs.getDate(8));
                    o.setPriceType(rs.getString(10));
                    o.setNumShares(rs.getInt(11));
                    orders.add(o);
                }
                else if (type.equals("HiddenStop")){
                    HiddenStopOrder o = new HiddenStopOrder();
                    o.setClient(rs.getInt(4));
                    o.setId(rs.getInt(6));
                    o.setStockName(rs.getString(7));
                    o.setDatetime(rs.getDate(8));
                    o.setPriceType(rs.getString(10));
                    o.setNumShares(rs.getInt(11));
                    orders.add(o);
                }
                else{

                }
            }
            con.close();
            return orders;
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }

        //return getDummyOrders();

    }


    public List<OrderPriceEntry> getOrderPriceHistory(String orderId) {     // TESTING: DONE

        /*
         * The students code to fetch data from the database will be written here
         * Query to view price history of hidden stop order or trailing stop order
         * Use setPrice to show hidden-stop price and trailing-stop price
         */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
            List<OrderPriceEntry> orderPriceHistory = new ArrayList<OrderPriceEntry>();

            String sql = "Select C.Id, C.StockName, C.PricePerShare, (C.PricePerShare - C.PricePerShare * C.Percentage) as TrailingStop, C.DateTime From snisonoff.Order C Where C.PriceType = ? and C.Id = ? ORDER BY C.Id ASC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "TrailingStop");
            pst.setString(2, orderId);
            ResultSet rs = pst.executeQuery();
            int curr = 0;
            while (rs.next()) {
                int ind = rs.getInt(1);
                if(ind > curr){
                    curr = ind;
                }
                else{
                    continue;
                }
                OrderPriceEntry o = new OrderPriceEntry();
                o.setOrderId(rs.getString(1));
                o.setStockSymbol(rs.getString(2));
                o.setPricePerShare(rs.getFloat(3));
                o.setPrice(rs.getFloat(4));
                o.setDate(rs.getDate(5));
                orderPriceHistory.add(o);
            }
            sql = "Select C.Id, C.StockName, C.PricePerShare, (C.PricePerShare - C.PricePerShare * C.Percentage) as TrailingStop, C.DateTime From snisonoff.Order C Where C.PriceType = ? and C.Id = ? ORDER BY C.Id ASC";
            pst = con.prepareStatement(sql);
            pst.setString(1, "HiddenStop");
            pst.setString(2, orderId);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                OrderPriceEntry o = new OrderPriceEntry();
                o.setOrderId(rst.getString(1));
                o.setStockSymbol(rst.getString(2));
                o.setPricePerShare(rst.getFloat(3));
                o.setPrice(rst.getFloat(4));
                o.setDate(rst.getDate(5));
                orderPriceHistory.add(o);
            }
            return orderPriceHistory;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

            /*
            for (int i = 0; i < 10; i++) {
                OrderPriceEntry entry = new OrderPriceEntry();
                entry.setOrderId(orderId);
                String priceType = rs.getString(1);

                //entry.setDate(new Date());
                //entry.setStockSymbol("aapl");
                //entry.setPricePerShare(150.0);
                //entry.setPrice(100.0);
                orderPriceHistory.add(entry);
            }
            return orderPriceHistory;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }*/



}