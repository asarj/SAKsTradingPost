package model;

import java.util.Date;

public class Order {
	
	/*
	 * This class is a representation of the bid table in the database
	 * Each instance variable has a corresponding getter and setter
	 */

	private int id;
    private Date datetime;
    private int numShares;
    private double pricePerShare;
    private double percentage;
    private String priceType;
    private String orderType;
    private String stockName;
    private int Client;
    private static int nextId = 111111;

    public static int getNextId() { //TODO add more
         nextId++;
         return nextId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getNumShares() {
        return numShares;
    }

    public void setNumShares(int numShares) {
        this.numShares = numShares;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getClient() {
        return Client;
    }

    public void setClient(int client) {
        Client = client;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
