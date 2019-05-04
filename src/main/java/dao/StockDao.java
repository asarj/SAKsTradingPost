package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Customer;
import model.Location;
import model.Stock;

public class StockDao {

    public Stock getDummyStock() {
        Stock stock = new Stock();
        stock.setName("Ap");
        stock.setSymbol("AAPL");
        stock.setPrice(150.0);
        stock.setNumShares(1200);
        stock.setType("Technology");

        return stock;
    }

    public List<Stock> getDummyStocks() {
        List<Stock> stocks = new ArrayList<Stock>();

        /*Sample data begins*/
        for (int i = 0; i < 10; i++) {
            stocks.add(getDummyStock());
        }
        /*Sample data ends*/

        return stocks;
    }

    public Stock getMYDummyStock() {
        Stock stock = new Stock();
        stock.setName("BOOP");
        stock.setSymbol("what");
        stock.setPrice(150.0);
        stock.setNumShares(1200);
        stock.setType("amazing");

        return stock;
    }

    public List<Stock> getMYDummyStocks() {
        List<Stock> stocks = new ArrayList<Stock>();

        /*Sample data begins*/
        for (int i = 0; i < 10; i++) {
            stocks.add(getMYDummyStock());
        }
        /*Sample data ends*/

        return stocks;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  1
    // done, but needs testing: needs fixing
    public List<Stock> getActivelyTradedStocks() {

        /*
         * The students code to fetch data from the database will be written here
         * Query to fetch details of all the stocks has to be implemented
         * Return list of actively traded stocks
         */

//
        List<Stock> stocks = new ArrayList<Stock>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
            Statement stmt = con.createStatement();

            try{
                stmt.executeUpdate("DROP VIEW StockTraded;");
            }catch(SQLException e){
                System.out.println("cannot drop view");
            }

            try{
                stmt.executeUpdate("DROP VIEW TradedTimes;");
            }catch(SQLException e){
                System.out.println("cannot drop view");
            }


            String s1 = "Create View StockTraded (StockSymbol, StockName, StockType, TransactionId) " +
                    "AS " +
                    "Select S.StockSymbol, S.CompanyName, S.Type, Tr.TransactionId " +
                    "From snisonoff.Order O, snisonoff.Trade Tr, snisonoff.Stock S " +
                    "Where O.StockName = S.StockSymbol and O.Id = Tr.OrderId; ";

            String s1_5 = "Create View TradedTimes (StockSymbol, StockName, StockType, Times) " +
                    "AS " +
                    "Select StockSymbol, StockName, StockType, COUNT(StockSymbol) " +
                    "From StockTraded " +
                    "Group By StockSymbol; ";

            String s2 = "SELECT * " +
                    "From TradedTimes " +
                    "Where Times >= " +
                    "(Select Max(Times) " +
                    "From TradedTimes);";

            stmt.executeUpdate(s1);
            stmt.executeUpdate(s1_5);
            ResultSet rs = stmt.executeQuery(s2);
//            stmt.executeUpdate(q3);

            while(rs.next()){
                Stock s = new Stock();
                s.setSymbol(rs.getString(1));
                s.setName(rs.getString(2));
                s.setType(rs.getString(3));
                s.setNumShares(rs.getInt(4));
                stocks.add(s);
            }
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
        return stocks;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  2
    // Working!
    public List<Stock> getAllStocks() {
        /*
         * The students code to fetch data from the database will be written here
         * Return list of stocks
         */
        List<Stock> stocks = new ArrayList<Stock>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT *" +
                    " FROM snisonoff.Stock;");
            while(rs.next()){
                Stock s = new Stock();
                s.setSymbol(rs.getString(1));
                s.setName(rs.getString(2));
                s.setType(rs.getString(3));
                s.setPrice(rs.getDouble(4));
                s.setNumShares(rs.getInt(5));
                stocks.add(s);
            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
        }
        return stocks;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  3
    // done, but needs testing (not sure, check after stock history)
    public Stock getStockBySymbol(String stockSymbol)
    {
        /*
         * The students code to fetch data from the database will be written here
         * Return stock matching symbol
         */
        Stock s = new Stock();
        try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT S.* FROM Stock S WHERE S.Type = '" + stockSymbol + "' ");

            //you need to remove duplicates --> half working
            while(rs.next()){

                s.setSymbol(rs.getString(1));
                s.setName(rs.getString(2));
                s.setType(rs.getString(3));
                s.setPrice(rs.getDouble(4));
                s.setNumShares(rs.getInt(5));
            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
        }

        return s;

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  4
    // Working, but taking in only int
    public String setStockPrice(String stockSymbol, double stockPrice) {
        /*
         * The student's code to fetch data from the database will be written here
         * Perform price update of the stock symbol
         */

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate("Update Stock Set PricePerShare = '" + stockPrice + "' Where StockSymbol = '" + stockSymbol + "'");
            con.close();
            return "success";
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
            return "failure";
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  5
    //Working, but needs visual editing
    public List<Stock> getOverallBestsellers() {

        /*
         * The students code to fetch data from the database will be written here
         * Get list of bestseller stocks
         */

        List<Stock> stocks = new ArrayList<Stock>();
        try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select Tr.StockId, COUNT(Tr.StockId) " +
                    "From snisonoff.Order O, Trade Tr " +
                    "Where O.Id = Tr.OrderId " +
                    "Group By Tr.StockId " +
                    "Order By Count(Tr.StockId);");

            while(rs.next()){
                Stock s = new Stock();
                s.setSymbol(rs.getString(1)); //Idk if StockId is name or symbol
//                s.setName(rs.getString(2));
//                s.setType(rs.getString(3));
//                s.setPrice(rs.getDouble(4));
                s.setNumShares(rs.getInt(2));
                stocks.add(s);
            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
        }
        return stocks;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   6
    //Working; fix visualization + use customerID
    public List<Stock> getCustomerBestsellers(String customerID) { //Needs completion

        /*
         * The students code to fetch data from the database will be written here.
         * Get list of customer bestseller stocks
         */
        /*
            Select O.StockSymbol, COUNT(O. StockSymbol)
            From snisonoff.Order O, Trade Tr, Account A
            Where A.Client = customerID AND A.Id = Tr.AccountId AND O. OrderId = Tr. OrderId
            Group By O.StockSymbol
            Order By Count (O.StockSymbol)
         */

        List<Stock> stocks = new ArrayList<Stock>();
        try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select O.StockName, COUNT(O. StockName) " +
                    "From snisonoff.Order O, Trade Tr, Account A " +
                    "Where A.Client = '222222222' AND A.Id = Tr.AccountId AND O.Id = Tr. OrderId " +
                    "Group By O.StockName " +
                    "Order By COUNT(O.StockName);");


            while(rs.next()){
                Stock s = new Stock();
                s.setSymbol(rs.getString(1)); //Idk if StockId is name or symbol
//                s.setName(rs.getString(2));
//                s.setType(rs.getString(3));
//                s.setPrice(rs.getDouble(4));
                s.setNumShares(rs.getInt(2));
                stocks.add(s);
            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
        }
        return stocks;
    }

    ///////// C U R R E N T   S T O C K   H O L D I N G S///------------------------------------------SearchStocks Type   7
    //Working! test with actual string customerId (has dummy value for now)
    public List getStocksByCustomer(String customerId) {

        /*
         * The students code to fetch data from the database will be written here
         * Get stockHoldings of customer with customerId
         */

        /*
        SELECT S.*
        FROM Stock S, Account A, Client C
        WHERE A.Stock = S.StockSymbol AND A.Client = C.Id AND C.Id = '444444444'
         */
        List<Stock> stocks = new ArrayList<Stock>();
            try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT S.* FROM Stock S, Account A, Client C WHERE A.Stock = S.StockSymbol AND A.Client = C.Id AND C.Id = '444444444' " ); //customerId


            while(rs.next()){

                Stock s = new Stock();
                s.setSymbol(rs.getString(1));
                s.setName(rs.getString(2));
                s.setType(rs.getString(3));
                s.setPrice(rs.getDouble(4));
                s.setNumShares(rs.getInt(5));
                stocks.add(s);

            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
        }
         return stocks;
    }
    ///////// S E A R CH  S T O C K S /// NAME --------------------------------------------------- SearchStocks Name       8
    //Working!
    public List<Stock> getStocksByName(String name) { //Search by Stock Name : Working!

        /*
         * The students code to fetch data from the database will be written here
         * Return list of stocks matching "name"
         */

        List<Stock> stocks = new ArrayList<Stock>();
        try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT S.* FROM Stock S WHERE S.CompanyName = '" + name + "' ");

            //you need to remove duplicates --> half working
            while(rs.next()){
                Stock s = new Stock();
                s.setSymbol(rs.getString(1));
                s.setName(rs.getString(2));
                s.setType(rs.getString(3));
                s.setPrice(rs.getDouble(4));
                s.setNumShares(rs.getInt(5));
                stocks.add(s);
            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
        }

        return stocks;

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  9
    // Working, but giving many many results (and the company symbols are inaccurate)
    public List<Stock> getStockSuggestions(String customerID) {

        /*
         * The students code to fetch data from the database will be written here
         * Return stock suggestions for given "customerId"
         */

        List<Stock> stocks = new ArrayList<Stock>();

        try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            try{
                stmt.executeUpdate("DROP VIEW SelectedOrders;");
            }catch(SQLException e){
                System.out.println("cannot drop view");
            }
            //create view
            String q1 = "Create View SelectedOrders(SSN, LastName, FirstName, Id, AccountId, StockName, " +
                    "DateTime, PricePerShare, Fee, OrderType, CompanyName, StockType) AS " +
                    "Select P. SSN, P. LastName, P.FirstName, C. Id, A.Id, O.StockName, " +
                    "T. DateTime, T. PricePerShare, T. Fee, O.OrderType, S.CompanyName, S.Type " +
                    "From Person P, Client C, Account A, snisonoff.Order O, Transaction T, Stock S, Trade Tr " +
                    "Where A.Client = '"+ "222222222"+ "' and Tr. AccountId = A.Client and " +
                    "T.Id = Tr. TransactionId and Tr. OrderId = O. Id; ";
            String q2 = "Select * " +
                    "From Stock S, SelectedOrders SO " +
                    "Where Type In (Select StockType from SelectedOrders " +
                    "Where SO.AccountId = '222222222') And S.StockSymbol Not In ( Select StockName from SelectedOrders " +
                    "Where AccountId = '" + "222222222" + "')";
//            String q3 = "Drop View SelectedOrders";

            stmt.executeUpdate(q1);
            ResultSet rs = stmt.executeQuery(q2);
//            stmt.executeUpdate(q3);

            while(rs.next()){
                Stock s = new Stock();
                s.setSymbol(rs.getString(1));
                s.setType(rs.getString(3));
                s.setName(rs.getString(2));
                s.setPrice(rs.getDouble(4));
                s.setNumShares(rs.getInt(5));
                stocks.add(s);
            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
        }
        return stocks;
    }
    ///////// S T O C K  P R I C E  H I S T O R Y  /// ---------------------------------------------Stock Price History   10
    //Working! but needs some editing on visuals
    public List<Stock> getStockPriceHistory(String stockSymbol) { // STOCK PRICE HISTORY -- working, but needs some editing

        /*
         * The students code to fetch data from the database
         * Return list of stock objects, showing price history
         * Search for stock by stock symbol but then get the price history
         * SELECT * FROM snisonoff.StockPriceHistory WHERE StockSymbol1 = 'GM';
         */
        List<Stock> stocks = new ArrayList<Stock>();
        System.out.println("1!!");
        try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            String StockSymbol = "'" + stockSymbol + "'";
            System.out.println(StockSymbol);
            ResultSet rs = stmt.executeQuery("SELECT * FROM snisonoff.StockPriceHistory WHERE StockSymbol1 = " + StockSymbol); //stockSymbol


            //fix the output so it shows proper results plus timestamp. viewGetStockPriceHistory.jsp and showStock.jsp
            while(rs.next()){
                System.out.println("3!!");
                Stock s = new Stock();
                s.setSymbol(rs.getString(1));
                s.setName(null);
                s.setType(null);
                s.setPrice(rs.getDouble(2));
                s.setNumShares(3);
                stocks.add(s);
            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
        }
        return stocks;

    }

    ///////// S E A R CH  S T O C K S /// TYPE Options --------------------------------------------- SearchStocks Type     11
    //Dealing with duplicates only half working
    public List<String> getStockTypes() { //Search Options: Search by Type --> Remove dulpicate method half working

        /*
         * The students code to fetch data from the database will be written here.
         * Populate types with stock types
         */

        List<String> types = new ArrayList<String>();
        try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT S.Type FROM Stock S");

            //you need to remove duplicates --> half working
            int i = 0;
            System.out.println("size: "+ types.size());
            while(rs.next()){
                String rsNext = rs.getString(1);
                if(types.size() >0 ) {
                    System.out.println("size >0");
                    for(int j=0; j<types.size(); j++){
                        String typesNext = types.get(j).toString();
                        System.out.println(typesNext + ", " + rsNext);
                        if (typesNext.equals(rsNext)){
                            System.out.println("repetition found");
                            i = -1; //if i is -1 it means there's a repetition
//                            types.add(rsNext);
//                            System.out.println(typesNext);
//                            System.out.println(rsNext);
                        }
                    }
                    if(i == -1){
                        i = 0;
                    }else{
                        types.add(rsNext);
                    }

                } else{
                    System.out.println("size 0");
                    types.add(rsNext);
                }
            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
        }
//        types.add("technology");
//        types.add("finance");
        return types;
    }
    ///////// S E A R CH  S T O C K S /// TYPE --------------------------------------------------- SearchStocks Type      12
    //Working!

    public List<Stock> getStockByType(String stockType) {

        /*
         * The students code to fetch data from the database will be written here
         * Return list of stocks of type "stockType"
         */

        List<Stock> stocks = new ArrayList<Stock>();
        try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT S.* FROM Stock S WHERE S.Type = '" + stockType + "' ");

            //you need to remove duplicates --> half working
            while(rs.next()){
                Stock s = new Stock();
                s.setSymbol(rs.getString(1));
                s.setName(rs.getString(2));
                s.setType(rs.getString(3));
                s.setPrice(rs.getDouble(4));
                s.setNumShares(rs.getInt(5));
                stocks.add(s);
            }
            con.close();
        }
        catch(Exception x){
            System.out.println(x);
            System.out.println("Error loading the customers");
        }

        return stocks;

    }
}
