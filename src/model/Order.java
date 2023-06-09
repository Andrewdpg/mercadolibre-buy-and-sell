package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import util.Compare;

public class Order implements Compare<Order> {

    public static final String PATH = "orders.json";

    private String bName;
    private String id;
    private ArrayList<Product> list;
    private Double totalPrice;
    private Date purchasedDate;
    private Integer totalProducts;

    public Order(String bName, String id, ArrayList<Product> list, String date) throws ParseException {
        this.bName = bName;
        this.id = id;
        this.list = list;
        this.totalPrice = calcTotal(list);
        this.totalProducts = totalProducts(list);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        purchasedDate = dateFormat.parse(date);
    }

    public int compare(Order other, String atribute) {
        switch (atribute) {
            case "name":
                return this.bName.toLowerCase().compareTo(other.getbName().toLowerCase());

            case "total price":
                return this.totalPrice.compareTo(other.getTotalPrice());

            case "date":
                return this.purchasedDate.compareTo(other.getPurchasedDate());

            case "quantity":
                return this.totalProducts.compareTo(other.totalProducts);

            case "id":
                return this.id.compareTo(other.getId());
            default:
                break;
        }

        return this.bName.compareTo(other.getbName());
    }

    public int compareAttr(Object target, String atributte) {
        switch (atributte) {
            case "name":
                return this.bName.toLowerCase().compareTo(((String) target).toLowerCase());

            case "total price":
                return this.totalPrice.compareTo(Double.parseDouble(target.toString()));

            case "date":
                return this.purchasedDate.compareTo((Date) target);
            case "quantity":
                return this.totalProducts.compareTo((int) target);
            case "id":
                return this.id.compareTo((String) target);
            default:
                break;
        }

        return 0;
    }

    public double calcTotal(ArrayList<Product> list) {
        double result = 0.0;
        for (Product p : list) {
            result += p.getPrice() * p.getQuantity();
        }
        return result;
    }

    public int totalProducts(ArrayList<Product> list) {
        int result = 0;
        for (Product p : list) {
            result += p.getQuantity();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Comprador: " + bName + " - Número de productos: " + totalProducts + " - Precio total: " + totalPrice + " - Fecha de compra: "
                + purchasedDate.toString();
    }
}
