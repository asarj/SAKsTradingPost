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
    public List<RevenueItem> getSalesReport(String month, String year) { //tested

        /*
         * The students code to fetch data from the database will be written here
         * Query to get sales report for a particular month and year
         */
        //We need to generate two strings that represent the day before and after the month
        String day1 = "";
        String day2 = "";
        if ("january".equals(month.toLowerCase()) || "jan".equals(month.toLowerCase()) || "01".equals(month.toLowerCase())) {
            int yIntt = Integer.parseInt(year);
            day1 = (yIntt - 1) + "-12-31";
            day2 = year + "-02-01";
        } else if ("february".equals(month.toLowerCase()) || "feb".equals(month.toLowerCase()) || "02".equals(month.toLowerCase())) {
            day1 = year + "-01-31";
            day2 = year + "-03-01";
        } else if ("march".equals(month.toLowerCase()) || "mar".equals(month.toLowerCase()) || "03".equals(month.toLowerCase())) {
            int yInt = Integer.parseInt(year);
            if (yInt % 100 == 0 && yInt % 400 == 0) {
                //Leap year
                day1 = year + "-02-29";
            } else {
                day1 = year + "-02-28";
            }
            day2 = year + "-04-01";
        } else if ("april".equals(month.toLowerCase()) || "apr".equals(month.toLowerCase()) || "04".equals(month.toLowerCase())) {
            day1 = year + "-03-31";
            day2 = year + "-05-01";
        } else if ("may".equals(month.toLowerCase()) || "05".equals(month.toLowerCase())) {
            day1 = year + "-04-30";
            day2 = year + "-06-01";
        } else if ("june".equals(month.toLowerCase()) || "jun".equals(month.toLowerCase()) || "06".equals(month.toLowerCase())) {
            day1 = year + "-05-31";
            day2 = year + "-07-01";
        } else if ("july".equals(month.toLowerCase()) || "jul".equals(month.toLowerCase()) || "07".equals(month.toLowerCase())) {
            day1 = year + "-06-30";
            day2 = year + "-08-01";
        } else if ("august".equals(month.toLowerCase()) || "aug".equals(month.toLowerCase()) || "08".equals(month.toLowerCase())) {
            day1 = year + "-07-31";
            day2 = year + "-09-01";
        } else if ("september".equals(month.toLowerCase()) || "sep".equals(month.toLowerCase()) || "09".equals(month.toLowerCase())) {
            day1 = year + "-08-31";
            day2 = year + "-10-01";
        } else if ("october".equals(month.toLowerCase()) || "oct".equals(month.toLowerCase()) || "10".equals(month.toLowerCase())) {
            day1 = year + "-09-30";
            day2 = year + "-11-01";
        } else if ("november".equals(month.toLowerCase()) || "nov".equals(month.toLowerCase()) || "11".equals(month.toLowerCase())) {
            day1 = year + "-10-31";
            day2 = year + "-03-01";
        } else if ("december".equals(month.toLowerCase()) || "dec".equals(month.toLowerCase()) || "12".equals(month.toLowerCase())) {
            day1 = year + "-01-31";
            day2 = (year + 1) + "-01-01";
        }
        day1 += " 00:00:00";
        day2 += " 00:00:00";
        List<RevenueItem> items = new ArrayList<RevenueItem>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
            Statement stmt = con.createStatement();

            String sql = "Select T.Id, O.NumShares, T.PricePerShare, T.DateTime, Tr.StockId, Tr.AccountId, Tr.BrokerId, T.Fee From snisonoff.Transaction T, snisonoff.Order O, snisonoff.Trade Tr Where O.DateTime > ? and O.DateTime < ? and O.Id = Tr.OrderId and T.Id = Tr.TransactionId;";
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
                r.setAccountId(rs.getString(6).substring(0, 3) + "-" + rs.getString(6).substring(3, 5) + "-" + rs.getString(6).substring(5)); //TODO convert to string correct way
                r.setAmount(rs.getFloat(8));
                items.add(r);
                // orders.add(o);
            }
            con.close();
            return items;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }


        //return getDummyRevenueItems();

    }



    public List<RevenueItem> getSummaryListing(String searchKeyword) { //Tested

        /*
         * The students code to fetch data from the database will be written here
         * Query to fetch details of summary listing of revenue generated by a particular stock,
         * stock type or customer must be implemented
         * Store the revenue generated by an item in the amount attribute
         */
       // return getDummyRevenueItems();

        List<RevenueItem> items = new ArrayList<RevenueItem>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql4.cs.stonybrook.edu:3306/snisonoff", "snisonoff", "111614611");
            //   Statement stmt = con.createStatement();

            String sql = "Create View Name (Id, AccountId, SSN, LastName, FirstName, NumShares) AS Select C.Id, A.Id, P.SSN, P.LastName, P.FirstName, A.NumShares From snisonoff.Account A, snisonoff.Client C, snisonoff.Person P Where A.Client = C.Id and C.Id = P.SSN";
            con.prepareStatement(sql);
            sql = "Select T.DateTime, N.AccountId, S.StockSymbol, N.NumShares, S.PricePerShare, T.Fee From snisonoff.Stock S, snisonoff.Trade Tr, snisonoff.Transaction T, Name N, snisonoff.Order O Where S.StockSymbol = O.StockName and T.Id = Tr.TransactionId and O.Id = Tr.OrderId and Tr.AccountId = N.AccountId and S.StockSymbol = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, searchKeyword);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                RevenueItem r = new RevenueItem();
                r.setDate(rs.getDate(1));
                r.setAccountId(rs.getString(2));
                r.setStockSymbol(rs.getString(3));
                r.setNumShares(rs.getInt(4));
                r.setPricePerShare(rs.getFloat(5));
                r.setAmount(rs.getDouble(6));
                items.add(r);
            }

            sql = "Select T.DateTime, N.AccountId, S.StockSymbol, N.NumShares, S.PricePerShare, T.Fee From snisonoff.Stock S, snisonoff.Trade Tr, snisonoff.Transaction T, Name N, snisonoff.Order O Where S.StockSymbol = O.StockName and T.Id = Tr.TransactionId and O.Id = Tr.OrderId and Tr.AccountId = N.AccountId and S.Type = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, searchKeyword);
            rs = pst.executeQuery();
            while (rs.next()) {
                RevenueItem r = new RevenueItem();
                r.setDate(rs.getDate(1));
                r.setAccountId(rs.getString(2));
                r.setStockSymbol(rs.getString(3));
                r.setNumShares(rs.getInt(4));
                r.setPricePerShare(rs.getFloat(5));
                r.setAmount(rs.getDouble(6));
                items.add(r);
            }

            sql = "Select T.DateTime, N.AccountId, S.StockSymbol, N.NumShares, S.PricePerShare, T.Fee From snisonoff.Stock S, snisonoff.Trade Tr, snisonoff.Transaction T, Name N, snisonoff.Order O Where S.StockSymbol = O.StockName and T.Id = Tr.TransactionId and O.Id = Tr.OrderId and Tr.AccountId = N.AccountId and N.SSN = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, searchKeyword);
            rs = pst.executeQuery();
            while (rs.next()) {
                RevenueItem r = new RevenueItem();
                r.setDate(rs.getDate(1));
                r.setAccountId(rs.getString(2));
                r.setStockSymbol(rs.getString(3));
                r.setNumShares(rs.getInt(4));
                r.setPricePerShare(rs.getFloat(5));
                r.setAmount(rs.getDouble(6));
                items.add(r);
            }

            con.close();
            return items;
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }


    }
}