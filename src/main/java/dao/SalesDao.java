package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.RevenueItem;

public class SalesDao {

    private List<RevenueItem> getDummyRevenueItems()
    {
        List<RevenueItem> items = new ArrayList<RevenueItem>();

		/*Sample data begins*/
        for (int i = 0; i < 10; i++) {
            RevenueItem item = new RevenueItem();
            item.setDate(new Date());
            item.setNumShares(5);
            item.setAccountId("foo");
            item.setPricePerShare(50.0);
            item.setStockSymbol("AAPL");
            item.setAmount(150.0);
            items.add(item);
        }
        /*Sample data ends*/

        return items;
    }
    public List<RevenueItem> getSalesReport(String month, String year) { //TODO test

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to get sales report for a particular month and year
		 */
		//We need to generate two strings that represent the day before and after the month
        String day1 = "";
        String day2 = "";
        month = month.toLowerCase();
        if(month.equals("january") || month.equals("jan") || month.equals("01")){
            int yIntt = Integer.parseInt(year);
            day1 = (yIntt - 1) + "-12-31";
            day2 = year + "-02-01";
        }
        else if(month.equals("february") || month.equals("feb") || month.equals("02")){
            day1 = year + "-01-31";
            day2 = year + "-03-01";
        }
        else if(month.equals("march") || month.equals("mar") || month.equals("03")){
            int yInt = Integer.parseInt(year);
            if(yInt % 100 == 0 && yInt % 400 == 0) {
                //Leap year
                day1 = year + "-02-29";
            } else{ day1 = year + "-02-28"; }
            day2 = year + "-04-01";
        }
        else if(month.equals("april") || month.equals("apr") || month.equals("04")){
            day1 = year + "-03-31";
            day2 = year + "-05-01";
        }
        else if(month.equals("may") || month.equals("05")){
            day1 = year + "-04-30";
            day2 = year + "-06-01";
        }
        else if(month.equals("june") || month.equals("jun") || month.equals("06")){
            day1 = year + "-05-31";
            day2 = year + "-07-01";
        }
        else if(month.equals("july") || month.equals("jul") || month.equals("07")){
            day1 = year + "-06-30";
            day2 = year + "-08-01";
        }
        else if(month.equals("august") || month.equals("aug") || month.equals("08")){
            day1 = year + "-07-31";
            day2 = year + "-09-01";
        }
        else if(month.equals("september") || month.equals("sep") || month.equals("09")){
            day1 = year + "-08-31";
            day2 = year + "-10-01";
        }
        else if(month.equals("october") || month.equals("oct") || month.equals("10")){
                day1 = year + "-09-30";
                day2 = year + "-11-01";
        }
        else if(month.equals("november") || month.equals("nov") || month.equals("11")){
                day1 = year + "-10-31";
                day2 = year + "-03-01";
        }
        else if(month.equals("december") || month.equals("dec") || month.equals("12")){
                day1 = year + "-01-31";
                day2 = (year + 1) + "-01-01";
        }
        // Spencer's old code
//        switch(month.toLowerCase().toCharArray()){
//            case "january":
//            case "jan":
//            case "01":
//                int yIntt = Integer.parseInt(year);
//                day1 = (yIntt - 1) + "-12-31";
//                day2 = year + "-02-01";
//                break;
//            case "february":
//            case "feb":
//            case "02":
//                day1 = year + "-01-31";
//                day2 = year + "-03-01";
//                break;
//            case "march":
//            case "mar":
//            case "03":
//                int yInt = Integer.parseInt(year);
//                if(yInt % 100 == 0 && yInt % 400 == 0) {
//                    //Leap year
//                    day1 = year + "-02-29";
//                } else{ day1 = year + "-02-28"; }
//                day2 = year + "-04-01";
//                break;
//            case "april":
//            case "apr":
//            case "04":
//                day1 = year + "-03-31";
//                day2 = year + "-05-01";
//                break;
//            case "may":
//            case "05":
//                day1 = year + "-04-30";
//                day2 = year + "-06-01";
//                break;
//            case "june":
//            case "jun":
//            case "06":
//                day1 = year + "-05-31";
//                day2 = year + "-07-01";
//                break;
//            case "july":
//            case "jul":
//            case "07":
//                day1 = year + "-06-30";
//                day2 = year + "-08-01";
//                break;
//            case "august":
//            case "aug":
//            case "08":
//                day1 = year + "-07-31";
//                day2 = year + "-09-01";
//                break;
//            case "september":
//            case "sep":
//            case "09":
//                day1 = year + "-08-31";
//                day2 = year + "-10-01";
//                break;
//            case "october":
//            case "oct":
//            case "10":
//                day1 = year + "-09-30";
//                day2 = year + "-11-01";
//                break;
//            case "november":
//            case "nov":
//            case "11":
//                day1 = year + "-10-31";
//                day2 = year + "-03-01";
//                break;
//            case "december":
//            case "dec":
//            case "12":
//                day1 = year + "-01-31";
//                day2 = (year + 1) + "-01-01";
//                break;
//        }
        // End of Spencer's old code
        day1 += " 00:00:00";
        day2 += " 00:00:00";
        List<RevenueItem> items = new ArrayList<RevenueItem>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
            Statement stmt = con.createStatement();

            String sql = "Select T.Id, O.NumShares, T.PricePerShare, T.DateTime, Tr.StockId, Tr.AccountId, Tr.BrokerId From snisonoff.Transaction T, snisonoff.Order O, snisonoff.Trade Tr Where O.DateTime > ? and O.DateTime < ? and O.Id = Tr.OrderId and T.Id = Tr.TransactionId;";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, day1);
            pst.setString(2, day2);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                RevenueItem r = new RevenueItem();
                r.setNumShares(rs.getInt(2));
                r.setPricePerShare(rs.getFloat(3));
                r.setDate(rs.getDate(4));
                r.setStockSymbol(rs.getString(5));
                r.setAccountId(String.valueOf(rs.getInt(6))); //TODO convert to string correct way
                items.add(r);
               // orders.add(o);
            }
            con.close();
            return items;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        } //TODO add transaction fee for amount


        //return getDummyRevenueItems();

    }



    public List<RevenueItem> getSummaryListing(String searchKeyword) { //TODO Ask TA

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of summary listing of revenue generated by a particular stock,
		 * stock type or customer must be implemented
		 * Store the revenue generated by an item in the amount attribute
		 */
		/*
		Create View Name (Id, AccountId, SSN, LastName, FirstName)
AS
Select C. Id, A. AccountId, P. SSN, P. LastName, P. FirstName
From Account A, Client C, Person P
Where A. Id = C. Id and C. SSN = P. SSN
Select S. StockSymbol, S. Type, N. SSN, T. Fee
From Stock S, Trade Tr, Transaction T, Name N, Order O
Where S. StockSymbol = O. StockSymbol and T. TransactionId = Tr. TransactionId and O.OrderId = Tr. OrderId and Tr. AccountId = N. AccountId and S. StockSymbol = ‘GM’
Select S. StockSymbol, S. Type, N. SSN, T. Fee
From Stock S, Trade Tr, Transaction T, Name N, Order O
Where S. StockSymbol = O. StockSymbol and T. TransactionId = Tr. TransactionId and O.
OrderId = Tr. OrderId and Tr. AccountId = N. AccountId and S. Type = ‘automobile’
Select S. StockSymbol, S. Type, N. SSN, T. Fee
From Stock S, Trade Tr, Transaction T, Name N, Order O
Where S. StockSymbol = O. StockSymbol and T. TransactionId = Tr. TransactionId and O.OrderId = Tr. OrderId and Tr. AccountId = N. AccountId and N. SSN = ‘111-11-1111’
		 */

        return getDummyRevenueItems();
    }
}
