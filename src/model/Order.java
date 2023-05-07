package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import util.Compare;

public class Order implements Compare<Order>{

    public static final String PATH = "orders.json";

    private String bName;
    private ArrayList<Product> list;
    private Double totalPrice;
    private Date purchasedDate;

    public Order(String bName, ArrayList<Product> list, String date) throws ParseException {
        this.bName = bName;
        this.list = list;
        this.totalPrice = calcTotal(list);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        purchasedDate = dateFormat.parse(date);
    }

    public int compare(Order other, String atribute) {
        switch (atribute) {
            case "name":
                return this.bName.compareTo(other.getbName());

            case "total price":
                return this.totalPrice.compareTo(other.getTotalPrice());

            case "date":
                return this.purchasedDate.compareTo(other.getPurchasedDate());
            default:
                break;
        }

        return this.bName.compareTo(other.getbName());
    }

    public int compareAttr(Object target, String atributte) {
        switch (atributte) {
            case "name":
                return this.bName.compareTo((String) target);

            case "total price":
                return this.totalPrice.compareTo(Double.parseDouble(target.toString()));

            case "date":
                return this.purchasedDate.compareTo((Date) target);
            default:
                break;
        }

        return 0;
    }
    
    
    public double calcTotal(ArrayList<Product> list) {
        double result = 0.0;
        for (Product p : list) {
            result += p.getPrice();
        }
        return result;
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
