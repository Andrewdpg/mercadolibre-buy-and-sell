package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String bName;
    private ArrayList<Product> list;
    private double totalPrice;
    private Date purchasedDate;

    public Order(String bName, ArrayList<Product> list, double totalPrice, String date) throws ParseException {
        this.bName = bName;
        this.list = list;
        this.totalPrice = totalPrice;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        purchasedDate = dateFormat.parse(date);
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public ArrayList<Product> getList() {
        return list;
    }

    public void setList(ArrayList<Product> list) {
        this.list = list;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

}
