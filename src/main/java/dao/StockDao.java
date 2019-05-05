package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Customer;
import model.Location;
import model.Stock;
import java.util.Date;
import java.sql.Timestamp;

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
    public List<Stock> getActivelyTradedStocks() {  // TESTING: DONE

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


            String s1 = "Create View StockTraded (StockSymbol, StockName, StockType, TransactionId, PricePerShare ) " +
                    "AS " +
                    "Select S.StockSymbol, S.CompanyName, S.Type, Tr.TransactionId, S.PricePerShare " +
                    "From snisonoff.Order O, snisonoff.Trade Tr, snisonoff.Stock S " +
                    "Where O.StockName = S.StockSymbol and O.Id = Tr.OrderId; ";

            String s1_5 = "Create View TradedTimes (StockSymbol, StockName, StockType, Times, PricePerShare) " +
                    "AS " +
                    "Select StockSymbol, StockName, StockType, COUNT(StockSymbol), PricePerShare " +
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

            while(rs.next()){
                Stock s = new Stock();
                s.setSymbol(rs.getString(1));
                s.setName(rs.getString(2));
                s.setType(rs.getString(3));
                s.setNumShares(rs.getInt(4));
                s.setPrice(rs.getDouble(5));
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
    public List<Stock> getAllStocks() {     // TESTING: DONE
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
    public Stock getStockBySymbol(String stockSymbol) {     // TESTING: DONE
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
    public String setStockPrice(String stockSymbol, double stockPrice) {    // COME BACK AFTER SUBMITORDER() IS FINISHED
        /*
         * The student's code to fetch data from the database will be written here
         * Perform price update of the stock symbol
         */

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            Date date = new Date();
            long time = date.getTime();
            System.out.println("Time in Milliseconds: " + time);
            Timestamp ts = new Timestamp(time);
            System.out.println("Current Time Stamp: " + ts);
            // -- GET OLD STOCK PRICE
            double oldPricePerShare = 0.0 ;
            ResultSet rs1 = stmt.executeQuery("SELECT O.PricePerShare FROM snisonoff.Order O WHERE O.StockName = '" + stockSymbol + "'");
            System.out.println("1");
            while(rs1.next()){
                oldPricePerShare = (rs1.getDouble(1));
            }
            // -- UPDATING STOCK PRICE
            int rs = stmt.executeUpdate("Update Stock Set PricePerShare = '" + stockPrice + "' Where StockSymbol = '" + stockSymbol + "'");
            System.out.println("2");
            // -- INSERT NEW STOCK PRICE INTO STOCK PRICE HISTORY
            String s2= "insert into snisonoff.StockPriceHistory values ('"+ stockSymbol +"', '"+ stockPrice + "', '" + ts +"')";
            stmt.executeUpdate(s2);
            System.out.println("3");
            // CHECKING IF NEW STOCK PRICE WILL CAUSE NEW SELL TRANSACTIONS & TRADES
            ResultSet rs3 = stmt.executeQuery("SELECT O.* FROM snisonoff.Order O, Stock S " +
                    "WHERE ((S.PricePerShare - O.PricePerShare) < 0) AND ((O.PricePerShare - S.PricePerShare)/O.PricePerShare > O.Percentage) " +
                    "AND O.StockName = '" + stockSymbol + "' " +
                    "AND O.StockName = S.StockSymbol " +
                    "AND (O.PriceType = 'TrailingStop' OR O.PriceType = 'HiddenStop')");

            List<String> transactions = new ArrayList<String>();
            List<String> trades = new ArrayList<String>();
            List<String> updates = new ArrayList<String>();

            System.out.println("4");
            //transaction
            int transId = 0000000;
            double transFee = 1.0;
            double transStockPrice = 0.0;
            //trades
            int tradeAccountId = 5;
            int tradeEmployee = 8;
            int tradeOrderId = 9;
            String tradeOrderIdStr = 9 + "";
            String tradeStockId = "";

            //
            System.out.println("4.1");
            boolean foundOrder = false;
            while(rs3.next()) {
                foundOrder = true;
                //Create New Transaction
                transId = (int) (Math.random() * 10000) ;
                transFee = (rs3.getDouble(3)) * 0.05;
                System.out.println("4.2");
                transStockPrice = rs3.getDouble(3);
                String newTransaction = "insert into snisonoff.Transaction values (" + transId + ", " + transFee + ", '" + ts + "', " + transStockPrice + ")";
                transactions.add(newTransaction);
                //Create New Trade
                tradeAccountId = rs3.getInt(9);
                tradeEmployee = rs3.getInt(10);
                System.out.println("4.3");
                tradeOrderId = rs3.getInt(4);
                tradeOrderIdStr = rs3.getString(4);
                updates.add(tradeOrderIdStr);
                tradeStockId = rs3.getString(2);
                String newTrade = "insert into Trade values (" + tradeAccountId + ", " + tradeEmployee + ", " +
                        transId + ", " + tradeOrderId + ", '" + tradeStockId + "')";
                trades.add(newTrade);
                //Update Order Type to Sell
//                System.out.println("5 " + rs3.getFetchSize());
            }
            System.out.println("4.8");
            if(foundOrder) {
                for(int i=0;i<transactions.size(); i++){
                    stmt.executeUpdate(transactions.get(i));
                    System.out.println("6");
                }
                for(int i=0;i<trades.size(); i++){
                    stmt.executeUpdate(trades.get(i));
                    System.out.println("7");
                }
                for(int i=0;i<updates.size(); i++){
                    String updateOrderType = "UPDATE snisonoff.Order O SET O.OrderType = 'Sell' Where O.Id = " + updates.get(i) + "";
                    stmt.executeUpdate(updateOrderType);
                    System.out.println("8");
                }
            }
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
    public List<Stock> getOverallBestsellers() {    // TESTING: DONE

        /*
         * The students code to fetch data from the database will be written here
         * Get list of bestseller stocks
         */

        List<Stock> stocks = new ArrayList<Stock>();
        try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff","snisonoff","111614611");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select Tr.StockId, S.CompanyName, S.Type, COUNT(Tr.StockId), S.PricePerShare " +
                    "From snisonoff.Order O, Trade Tr, Stock S " +
                    "Where O.Id = Tr.OrderId AND S.StockSymbol = O.StockName " +
                    "Group By Tr.StockId " +
                    "Order By Count(Tr.StockId) DESC;");

            while(rs.next()){
                Stock s = new Stock();
                String stockSymbol = rs.getString(1);
                s.setSymbol(rs.getString(1));
                s.setName(rs.getString(2));
                s.setType(rs.getString(3));
                s.setPrice(rs.getDouble(5));
                s.setNumShares(rs.getInt(4));
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
    public List<Stock> getCustomerBestsellers(String customerID) {  // TESTING: DONE

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
            ResultSet rs = stmt.executeQuery("Select O.StockName, S.CompanyName, S.Type, S.PricePerShare, COUNT(O.StockName) " +
                    "From snisonoff.Order O, Trade Tr, Account A, Stock S " +
                    "Where A.Client = " + customerID+ " AND A.Id = Tr.AccountId AND O.Id = Tr. OrderId AND O.StockName = S.StockSymbol " +
                    "Group By O.StockName " +
                    "Order By COUNT(O.StockName);");

            while(rs.next()){
                Stock s = new Stock();
                s.setSymbol(rs.getString(1)); //Idk if StockId is name or symbol
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

    ///////// C U R R E N T   S T O C K   H O L D I N G S///------------------------------------------SearchStocks Type   7
    public List getStocksByCustomer(String customerId) {    // TESTING: DONE

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
            ResultSet rs = stmt.executeQuery("SELECT S.* FROM Stock S, Account A, Client C WHERE A.Stock = S.StockSymbol AND A.Client = C.Id AND C.Id = " + customerId + ""); //customerId


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
    public List<Stock> getStocksByName(String name) {   // TESTING: DONE

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
    public List<Stock> getStockSuggestions(String customerID) {     // TESTING: DONE

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
                    "WHERE P.SSN = " + customerID +" AND P.SSN = C.Id and C.Id = A.Client and Tr. AccountId = A.Id and " +
                    "Tr. TransactionId = T. Id and Tr. OrderId = O. Id and O. StockName = S. StockSymbol";
            String q2 = "Select S.StockSymbol, S.CompanyName, S.Type, S.NumShares, S.PricePerShare " +
                    "From Stock S " +
                    "Where S.Type in (Select StockType from SelectedOrders " +
                    "Where SSN = " + customerID + ") And StockSymbol Not In ( Select StockName from SelectedOrders " +
                    "Where SSN = " + customerID + ")";
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
    public List<Stock> getStockPriceHistory(String stockSymbol) { // STOCK PRICE HISTORY -- working, but needs some editing     // TESTING: DONE (I guess)

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
            ResultSet rs = stmt.executeQuery("SELECT SH.StockSymbol1, S.CompanyName, S.Type, SH.SharePrice,  S.NumShares FROM snisonoff.StockPriceHistory SH, Stock S WHERE StockSymbol1 = " + StockSymbol + " AND S.StockSymbol = " + StockSymbol); //stockSymbol


            //fix the output so it shows proper results plus timestamp. viewGetStockPriceHistory.jsp and showStock.jsp
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

    ///////// S E A R CH  S T O C K S /// TYPE Options --------------------------------------------- SearchStocks Type     11
    public List<String> getStockTypes() {   // TESTING: DONE

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
        return types;
    }
    ///////// S E A R CH  S T O C K S /// TYPE --------------------------------------------------- SearchStocks Type      12
    //Working!

    public List<Stock> getStockByType(String stockType) {       // TESTING: DONE

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